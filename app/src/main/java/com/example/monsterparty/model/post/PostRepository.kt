package com.example.monsterparty.model.post

import com.example.monsterparty.model.user.Post

class PostRepository(private val dao : PostDao) {

    val posts = dao.getAllPosts()

    suspend fun insertPost(post: Post){
        dao.insertPost(post)
    }
    suspend fun deletePost(post: Post){
        dao.deletePost(post)
    }
    suspend fun deleteAll(){
        dao.deleteAll()
    }
}