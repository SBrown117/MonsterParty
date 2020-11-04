package com.example.monsterparty.model.post

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.monsterparty.model.user.Post

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class PostDatabase : RoomDatabase(){
    abstract val postDao : PostDao

    companion object{
        @Volatile
        private var INSTANCE : PostDatabase? = null
        fun getInstance(context: Context): PostDatabase? {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostDatabase::class.java,
                        "post_table"
                    ).build()
                }
                return instance
            }
        }
    }
}