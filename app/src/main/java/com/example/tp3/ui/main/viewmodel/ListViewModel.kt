package com.example.tp3.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tp3.data.ListRepository
import kotlinx.coroutines.launch

class ListViewModel(application: Application): AndroidViewModel(application) {
    private val listRepository by lazy { ListRepository.newInstance(application) }

    val lists = MutableLiveData<ListViewModel.ViewState>()

    fun getList(id: Int, hash: String){
        viewModelScope.launch {
            lists.value = ListViewModel.ViewState.Loading
            try {
                lists.value = ListViewModel.ViewState.Content(lists = listOf(listRepository.getList(id, hash)))

            } catch (e: Exception) {
                lists.value = ListViewModel.ViewState.Error(e.message.orEmpty())
            }
        }
    }

    fun getListsUser(hash: String){
        viewModelScope.launch {
            lists.value = ListViewModel.ViewState.Loading
            try {
                lists.value = ListViewModel.ViewState.Content(lists = listRepository.getListsUser(hash))

            } catch (e: Exception) {
                lists.value = ListViewModel.ViewState.Error(e.message.orEmpty())
            }
        }
    }

    fun mkListUser(idUser: Int, label: String, hash: String){
        viewModelScope.launch {
            lists.value = ListViewModel.ViewState.Loading
            try {
                listRepository.mkListUser(idUser, label, hash)
                lists.value = ListViewModel.ViewState.Content(lists = listRepository.getListsUser(hash))

            } catch (e: Exception) {
                lists.value = ListViewModel.ViewState.Error(e.message.orEmpty())
            }
        }
    }

    fun rmListUser(id_user: Int, id_list: Int, hash: String){

        viewModelScope.launch {
            lists.value = ListViewModel.ViewState.Loading
            try {
                listRepository.rmListUser(id_user, id_list, hash)
                lists.value = ListViewModel.ViewState.Content(lists = listRepository.getListsUser(hash))

            } catch (e: Exception) {
                lists.value = ListViewModel.ViewState.Error(e.message.orEmpty())
            }
        }

    }

    fun chgListLabel(id_user: Int, id_list: Int, label: String, hash: String){
        viewModelScope.launch {
            lists.value = ListViewModel.ViewState.Loading
            try {
                listRepository.chgListLabel(id_user, id_list, label, hash)
                lists.value = ListViewModel.ViewState.Content(lists = listRepository.getListsUser(hash))

            } catch (e: Exception) {
                lists.value = ListViewModel.ViewState.Error(e.message.orEmpty())
            }
        }
    }





    sealed class ViewState {
        object Loading : ViewState()
        data class Content(val lists: List<com.example.tp3.data.model.List>) : ViewState()
        data class Error(val message: String) : ViewState()
    }


}