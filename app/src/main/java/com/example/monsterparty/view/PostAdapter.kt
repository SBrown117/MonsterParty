package com.example.monsterparty.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.monsterparty.R
import com.example.monsterparty.databinding.PostLayoutCardBinding
import com.example.monsterparty.model.user.Post
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

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
            Picasso.get().load(it).error(R.drawable.ic_default).into(binding.ptIvPostProfile)
        }
        binding.ptTvPostText.text = post.postText
        val storageRef2 = FirebaseStorage.getInstance().getReference("MonsterPartyPics/${post.postPicture}")
        storageRef2.downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(binding.ptIvPostPicture)
        }


    }
}