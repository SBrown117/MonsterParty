package com.example.monsterparty.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.monsterparty.MainActivity
import com.example.monsterparty.R
import com.example.monsterparty.databinding.CreatePostBinding
import com.example.monsterparty.model.post.PostDatabase
import com.example.monsterparty.model.post.PostRepository
import com.example.monsterparty.viewmodel.PostViewModel
import com.example.monsterparty.viewmodel.PostViewModelProvider
import kotlinx.android.synthetic.main.create_account_page.*
import kotlinx.android.synthetic.main.create_post.*

class PostActivity: AppCompatActivity() {

    private val IMAGE_PICK_CODE = 118
    private lateinit var binding: CreatePostBinding
    private lateinit var createPostViewModel : PostViewModel
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.create_post)

        val dao = PostDatabase.getInstance(application)?.postDao
        val repository = dao?.let{ PostRepository(it)}
        val provider = repository?.let{PostViewModelProvider(it)}
        createPostViewModel = provider?.let { ViewModelProvider(this,it).get(PostViewModel::class.java)}!!
        binding.postViewModel = createPostViewModel
        binding.lifecycleOwner = this

        createPostViewModel.message.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let{
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            }
        })
        cp_et_post_text_box.addTextChangedListener(textWatcher)

        cp_btn_submit_post.setOnClickListener(View.OnClickListener {
            if(cp_iv_image.drawable == null){
                createPostViewModel.defaultPic()
            }
            val sharedPreferences = getSharedPreferences(
                    "monster_party_preferences",
                    Context.MODE_PRIVATE)
            val un = sharedPreferences.getString("username","N/A").toString()
            val im = sharedPreferences.getString("image","N/A").toString()
            createPostViewModel.savePost(un,im)
            return2Main()
        })
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { buttonCheck() }
    }
    fun getPostImage(view: View){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        photoUri = data?.data
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            cp_iv_image.setImageURI((photoUri))
            createPostViewModel.postPicture = photoUri!!
        }
        buttonCheck()
    }
    private fun buttonCheck(){
        cp_btn_submit_post.isEnabled = (cp_et_post_text_box.text.toString().isNotEmpty() || cp_iv_image.drawable != null)
    }
    private fun return2Main(){
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivityForResult(intent,89)
    }
}