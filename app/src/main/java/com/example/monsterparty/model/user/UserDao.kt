package com.example.monsterparty.model.user

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    //For the search feature
    @Query("SELECT * FROM user_table WHERE user_name LIKE :username")
    fun getThisUser(username: String): LiveData<List<User>>



}