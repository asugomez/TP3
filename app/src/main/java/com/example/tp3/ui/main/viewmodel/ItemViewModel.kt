package com.example.tp3.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tp3.data.ItemRepository
import com.example.tp3.data.UserRepository
import com.example.tp3.data.model.Item
import com.example.tp3.data.model.User
import kotlinx.coroutines.launch

class ItemViewModel(application: Application): AndroidViewModel(application) {
    private val itemRepository by lazy { ItemRepository.newInstance(application) }

    val items = MutableLiveData<ItemViewModel.ViewState>()

    fun getItemsOfAList(id_list: Int, hash: String){
        viewModelScope.launch {
            items.value = ItemViewModel.ViewState.Loading
            try {
                items.value = ItemViewModel.ViewState.Content(items = itemRepository.getItemsOfAList(id_list, hash))

            } catch (e: Exception) {
                items.value = ItemViewModel.ViewState.Error(e.message.orEmpty())
            }
        }
    }

    fun getItem(id_item: Int, id_list: Int, hash: String){
        viewModelScope.launch {
            items.value = ItemViewModel.ViewState.Loading
            try {
                items.value = ItemViewModel.ViewState.Content(items = listOf(itemRepository.getItem(id_item, id_list, hash)))
            } catch (e: Exception) {
                items.value = ItemViewModel.ViewState.Error(e.message.orEmpty())
            }
        }
    }

    fun mkItem(id_list: Int, label: String, url: String? = null, hash: String){
        viewModelScope.launch {
            items.value = ItemViewModel.ViewState.Loading
            try {
                itemRepository.mkItem(id_list, label, url, hash)
                items.value = ItemViewModel.ViewState.Content(items = itemRepository.getItemsOfAList(id_list, hash))
            } catch (e: Exception) {
                items.value = ItemViewModel.ViewState.Error(e.message.orEmpty())
            }
        }
    }

    fun rmItem(id_list: Int, id_item: Int, hash: String){
        viewModelScope.launch {
            items.value = ItemViewModel.ViewState.Loading
            try {
                itemRepository.rmItem(id_list, id_item, hash)
                items.value = ItemViewModel.ViewState.Content(items = itemRepository.getItemsOfAList(id_list, hash))
            } catch (e: Exception) {
                items.value = ItemViewModel.ViewState.Error(e.message.orEmpty())
            }
        }
    }

    fun chgItemLabel(id_list: Int, id_item: Int, label: String, hash: String){
        viewModelScope.launch {
            items.value = ItemViewModel.ViewState.Loading
            try {
                itemRepository.chgItemLabel(id_list, id_item, label, hash)
                items.value = ItemViewModel.ViewState.Content(items = itemRepository.getItemsOfAList(id_list, hash))
            } catch (e: Exception) {
                items.value = ItemViewModel.ViewState.Error(e.message.orEmpty())
            }
        }
    }

    fun chgItemUrl(id_list: Int, id_item: Int, url: String, hash: String){
        viewModelScope.launch {
            items.value = ItemViewModel.ViewState.Loading
            try {
                itemRepository.chgItemUrl(id_list, id_item, url, hash)
                items.value = ItemViewModel.ViewState.Content(items = itemRepository.getItemsOfAList(id_list, hash))
            } catch (e: Exception) {
                items.value = ItemViewModel.ViewState.Error(e.message.orEmpty())
            }
        }
    }

    fun checkItem(id_list: Int, id_item: Int, check: Int, hash: String){
        viewModelScope.launch {
            items.value = ItemViewModel.ViewState.Loading
            try {
                itemRepository.checkItem(id_list, id_item, check, hash)
                items.value = ItemViewModel.ViewState.Content(items = itemRepository.getItemsOfAList(id_list, hash))
            } catch (e: Exception) {
                items.value = ItemViewModel.ViewState.Error(e.message.orEmpty())
            }
        }
    }


    sealed class ViewState {
        object Loading : ViewState()
        data class Content(val items: List<Item>) : ViewState()
        data class Error(val message: String) : ViewState()
    }

}