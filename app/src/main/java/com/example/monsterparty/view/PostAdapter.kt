package com.example.monsterparty.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.monsterparty.MainActivity
import com.example.monsterparty.R
import com.example.monsterparty.databinding.PostLayoutCardBinding
import com.example.monsterparty.model.user.Post
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*

class PostAdapter(private val postList: List<Post>) : RecyclerView.Adapter<PostViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: PostLayoutCardBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.post_layout_card, parent,false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount() = postList.size

}

class PostViewHolder(private val binding: PostLayoutCardBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(post: Post){
        binding.ptIvPostName.text = post.postOwnerName
        val storageRef = FirebaseStorage.getInstance().getReference("MonsterPartyPics/${post.postOwnerPicture}")
        storageRef.downloadUrl.addOnSuccessListener {
            Glide.with(MainActivity().recycler_view).load(it).into(binding.ptIvPostProfile)
        }
        binding.ptTvPostText.text = post.postText

    }
}