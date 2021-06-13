package com.example.tp3.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tp3.data.DataRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {


    private val dataRepository by lazy { DataRepository.newInstance(application) }

    val posts = MutableLiveData<ViewState>()

    fun loadPost() {

        viewModelScope.launch {
            posts.value = ViewState.Loading
            try {
                posts.value = ViewState.Content(posts = postRepository.getPost())

            } catch (e: Exception) {
                posts.value = ViewState.Error(e.message.orEmpty())
            }

        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Content(val lists: List<com.example.tp3.data.model.List>) : ViewState()
        data class Error(val message: String) : ViewState()
    }
}