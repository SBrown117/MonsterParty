package com.example.monsterparty.model.user

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user_table")
data class User (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "user_id") var userId : Int,
        @ColumnInfo(name = "user_name") var userName : String,
        @ColumnInfo(name = "user_email") var userEmail : String,
        @ColumnInfo(name = "user_password") var userPassword : String,
        @ColumnInfo(name = "user_age") var userAge : String,
        @ColumnInfo(name = "user_lang") var userLanguage : String,
        @ColumnInfo(name = "user_desc") var userDescription : String,
        @ColumnInfo(name = "user_pic") var userPicture : String,
)

@Parcelize
@Entity(tableName = "post_table")
data class Post(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "post_id") val postId : Int,
        @ColumnInfo(name = "post_owner_name") val postOwnerName : String,
        @ColumnInfo(name = "post_owner_pic") val postOwnerPicture : String,
        @ColumnInfo(name = "post_text") val postText : String,
        @ColumnInfo(name = "post_picture") val postPicture : String
): Parcelable

/*
todo
    User
        - hash the password
        - language actually do something
    Post
        - add date creation time
 */