package com.example.monsterparty.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Insert
    suspend fun insertPost(post: Post)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE user_name LIKE :username")
    fun getThisUser(username: String): LiveData<List<String>>

    @Query("SELECT * FROM post_table WHERE post_owner_name IS :userId")
    fun getPosts(userId: Int): LiveData<List<Post>>

}