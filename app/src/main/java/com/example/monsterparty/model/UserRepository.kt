package com.example.monsterparty.model

class UserRepository (private val dao : UserDao){

    val users = dao.getAllUsers()
//    val posts = dao.getAllPosts()

    suspend fun insertUser(user: User){
        dao.insertUser(user)
    }

    suspend fun update(user: User){
        dao.updateUser(user)
    }

    suspend fun delete(user: User){
        dao.deleteUser(user)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
//    suspend fun insertPost(post: Post){
//        dao.insertPost(post)
//    }
}