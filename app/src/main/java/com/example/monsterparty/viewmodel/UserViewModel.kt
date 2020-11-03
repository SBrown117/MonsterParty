package com.example.monsterparty.viewmodel

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monsterparty.Event
import com.example.monsterparty.model.User
import com.example.monsterparty.model.UserRepository
import com.example.monsterparty.view.CreateAccountActivity
import com.example.monsterparty.view.LoginActivity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = ".UserViewModel"
class UserViewModel(private val repository: UserRepository): ViewModel(),Observable{
    val users = repository.users
    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = statusMessage


    // Bindable create account data
    @Bindable
    var createUserName = MutableLiveData<String>()

    @Bindable
    var createUserEmail = MutableLiveData<String>()

    @Bindable
    var createUserAge = MutableLiveData<String>()


    lateinit var createUserLang : String

    @Bindable
    var createUserPassword = MutableLiveData<String>()


    lateinit var createUserPicture : Uri
//    lateinit var savedUserPicture : String

    @Bindable
    var createUserDesc = MutableLiveData<String>()

    //Bindable update account data
//    @Bindable
//    var updateUserName = MutableLiveData<String>()
//
//    @Bindable
//    var updateUserEmail = MutableLiveData<String>()
//
//    @Bindable
//    var updateUserAge = MutableLiveData<Int>()
//
//    //todo ...bind these
//    @Bindable
//    val updateUserLang = MutableLiveData<String>()
//
//    @Bindable
//    val updateUserPassword = MutableLiveData<String>()
//
//    @Bindable
//    val updateUserPicture = MutableLiveData<String>()
//
//    @Bindable
//    val updateUserDesc = MutableLiveData<String>()

    fun saveUser(){
        val name : String = createUserName.value!!
        val email : String = createUserEmail.value!!
        val age : String = createUserAge.value!!
        val lang : String = createUserLang
        val pass : String= createUserPassword.value!!
        val pic = firebasePicStorage()
        val desc : String = createUserDesc.value!!
        Log.d(TAG, "saveUser: $name,$email,$age,$lang,$pass,$desc")
        Log.d(TAG, "saveUser: stopping point")
        insert(User(0,name,email,pass,age,lang,desc,pic))
        createUserName.value = null
        createUserEmail.value = null
        createUserPassword.value = null
        createUserAge.value = null
        createUserDesc.value = null

    }
//    fun updateUser(user: User){
//        //todo picture update
//        updateUserName.value = user.userName
//        updateUserEmail.value = user.userEmail
//        updateUserAge.value = user.userAge
//        updateUserDesc.value = user.userDescription
//    }

    private fun insert(user: User) = viewModelScope.launch {
        Log.d(TAG, "$user")
        repository.insertUser(user)
        statusMessage.value = Event("${user.userName} has been added. Welcome to Monster Party!")
    }
//    private fun update(user: User) = viewModelScope.launch {
//          Log.d(TAG, "${user.toString()}")
//        repository.update(user)
//        statusMessage.value = Event("${user.userName}, your information is up to date.")
//    }
//    private fun delete(user: User) = viewModelScope.launch {
//        repository.delete(user)
//        statusMessage.value = Event("Bye bye ${user.userName}. We'll miss you ;(")
//    }

    private fun firebasePicStorage():String{
        Log.d(TAG, "firebasePicStorage: Starting")
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/MonsterPartyPics/$filename")
            Log.d(TAG, "reference: $ref")
            ref.putFile(createUserPicture)
            Log.d(TAG, "reference putFile: $createUserPicture")

            ref.downloadUrl.addOnSuccessListener {
                Log.d(TAG, "firebasePicStorage: $it")
            }
        return createUserPicture.toString()
    }
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
        createUserPicture = builder.build()
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("The database has been cleansed")
    }
}
