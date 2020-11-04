package com.example.monsterparty.viewmodel

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monsterparty.Event
import com.example.monsterparty.model.post.PostRepository
import com.example.monsterparty.model.user.Post
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository): ViewModel(), Observable{
    val posts = repository.posts
    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = statusMessage

    @Bindable
    val postText = MutableLiveData<String>()


    fun savePost(userName : String, userPic : String, postPic : String){
        insert(Post(0,userName,userPic,postText.value!!,postPic))
        postText.value = null
    }
    private fun insert(post: Post) = viewModelScope.launch {
        repository.insertPost(post)
        statusMessage.value = Event("Post is created")
    }

    private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("All posts go bye bye.")
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}