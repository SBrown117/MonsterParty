package com.example.monsterparty.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.monsterparty.R
import com.example.monsterparty.model.UserDatabase
import com.example.monsterparty.model.UserRepository
import com.example.monsterparty.viewmodel.UserViewModel
import com.example.monsterparty.viewmodel.UserViewModelProvider
import kotlinx.android.synthetic.main.create_account_page.*

private const val TAG = ".CreateAccountActivity"
class CreateAccountActivity: AppCompatActivity() {

    private val IMAGE_PICK_CODE = 117
    private lateinit var userViewModel: UserViewModel
    var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account_page)

        val dao = UserDatabase.getInstance(application)?.userDao
        val repository = dao?.let { UserRepository(it) }
        val factory = repository?.let { UserViewModelProvider(it) }
        userViewModel = factory?.let { ViewModelProvider(this,it).get(UserViewModel::class.java)}!!

        userViewModel.createUserLang = ca_spn_lang.onItemSelectedListener.toString()
    }

    fun getImage(view: View){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        photoUri = data?.data
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            ca_iv_image.setImageURI((photoUri))
            userViewModel.createUserPicture = photoUri!!
        }
    }
}