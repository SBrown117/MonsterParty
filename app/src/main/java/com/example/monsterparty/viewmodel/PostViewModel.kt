package com.example.monsterparty.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monsterparty.Event
import com.example.monsterparty.model.post.PostRepository
import com.example.monsterparty.model.user.Post
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = ".PostViewModel"

class PostViewModel(private val repository: PostRepository): ViewModel(), Observable{
    val posts = repository.posts
    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = statusMessage

    @Bindable
    val postText = MutableLiveData<String>()

    lateinit var postPicture: Uri


    fun savePost(userName : String, userPic : String){
        val pic = firebasePicStorage()
        if(postText.value == null)
            postText.value = ""
        insert(Post(0,userName,userPic,postText.value!!,pic))
        postText.value = null
    }
    private fun insert(post: Post) = viewModelScope.launch {
        repository.insertPost(post)
        statusMessage.value = Event("Post is created")
    }
    private fun firebasePicStorage():String{
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/MonsterPartyPics/$filename")
        ref.putFile(postPicture)

        ref.downloadUrl.addOnSuccessListener {
            Log.d(TAG, "firebasePicStorage: $it")
        }
        return filename
    }
    //todo Fix default pic
    fun defaultPic(){
        //https://i.kym-cdn.com/photos/images/facebook/001/398/452/8c1.png
        val builder = Uri.Builder()
        builder.scheme("https")
                .authority("i.kym-cdn.com")
                .appendPath("photos")
                .appendPath("images")
                .appendPath("facebook")
                .appendPath("001")
                .appendPath("398")
                .appendPath("452")
                .appendPath("8c1.png")
        postPicture = builder.build()
    }
    private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("All posts go bye bye.")
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}