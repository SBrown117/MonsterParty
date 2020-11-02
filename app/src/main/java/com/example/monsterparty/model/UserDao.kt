package com.example.monsterparty.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

//    @Insert
//    suspend fun insertPost(post: Post)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)


    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

//    @Query("SELECT * FROM Post")
//    fun getAllPosts(): LiveData<List<Post>>

    //For the search feature
    @Query("SELECT * FROM user_table WHERE user_name LIKE :username")
    fun getThisUser(username: String): LiveData<List<User>>

    //For loading a user's posts
    //Should load the current user's post at login
//    @Query("SELECT * FROM user_table WHERE user_id IS :userId")
//    fun getPosts(userId: Int): LiveData<List<UserWithPosts>>

}