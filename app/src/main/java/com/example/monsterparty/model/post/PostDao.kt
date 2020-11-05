package com.example.monsterparty.model.post

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.monsterparty.model.user.Post

@Dao
interface PostDao {

    @Insert
    suspend fun insertPost(post: Post)

    @Update
    suspend fun updatePost(post: Post)

    @Delete
    suspend fun deletePost(post: Post)

    @Query("SELECT * FROM post_table ORDER BY post_id DESC")
    fun getAllPosts(): LiveData<List<Post>>

    @Query("DELETE FROM post_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM post_table WHERE post_owner_name IS :ownerName")
    fun getThesePosts(ownerName: String): LiveData<List<Post>>
}