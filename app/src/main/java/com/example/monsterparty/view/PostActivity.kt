package com.example.monsterparty.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.monsterparty.R
import com.example.monsterparty.databinding.CreatePostBinding
import com.example.monsterparty.model.post.PostDatabase
import com.example.monsterparty.model.post.PostRepository
import com.example.monsterparty.viewmodel.PostViewModel
import com.example.monsterparty.viewmodel.PostViewModelProvider

class PostActivity: AppCompatActivity() {

    private val IMAGE_PICK_CODE = 118
    private lateinit var binding: CreatePostBinding
    private lateinit var createPostViewModel : PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_post)

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
    }
}