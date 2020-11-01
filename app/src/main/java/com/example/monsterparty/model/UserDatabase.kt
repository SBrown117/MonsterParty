package com.example.monsterparty.model

import androidx.room.Database

@Database(entities = [User::class,Post::class],version = 1,exportSchema = false)
abstract class UserDatabase {
}