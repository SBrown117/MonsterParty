package com.example.monsterparty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monsterparty.model.post.PostRepository

class PostViewModelProvider(private val repository: PostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PostViewModel::class.java))
            return PostViewModel(repository) as T
        throw IllegalArgumentException("PostViewModel has dun goofed")
    }
}