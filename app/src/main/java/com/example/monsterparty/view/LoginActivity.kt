package com.example.monsterparty.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.monsterparty.R
import com.example.monsterparty.REQUEST_SETTINGS

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
    }
    fun openCreateAccount(view: View){
        val intent = Intent()
        intent.setClass(this, CreateAccountActivity::class.java)
        startActivityForResult(intent, REQUEST_SETTINGS)
    }
}