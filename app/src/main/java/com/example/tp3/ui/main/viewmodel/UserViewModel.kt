package com.example.tp3.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tp3.data.UserRepository
import com.example.tp3.data.model.User
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {


    private val userRepository by lazy { UserRepository.newInstance(application) }

    val user = MutableLiveData<ViewState>()

    ////////////// USER //////////////

    fun connexion(pseudo: String, pass: String){
        viewModelScope.launch {
            user.value = ViewState.Loading
            try{
                val hash: String = userRepository.connexion(pseudo, pass)
                val id: Int = 1 // todo: get the id with the hash
                val user_connected = User(id,pseudo, pass, hash)
                user.value = UserViewModel.ViewState.Content(user_connected)
            } catch (e: Exception){
                user.value = UserViewModel.ViewState.Error(e.message.orEmpty())
            }
        }

    }

    fun mkUser(pseudo: String, pass: String, hash: String){
        viewModelScope.launch {
            user.value = UserViewModel.ViewState.Loading
            try{
                user.value = UserViewModel.ViewState.Content(user = userRepository.mkUser(pseudo, pass, hash))
            } catch (e: Exception){
                user.value = UserViewModel.ViewState.Error(e.message.orEmpty())
            }
        }
    }



    sealed class ViewState {
        object Loading : ViewState()
        data class Content(val user: User) : ViewState()
        data class Error(val message: String) : ViewState()
    }
}