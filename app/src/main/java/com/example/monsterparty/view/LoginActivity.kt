package com.example.monsterparty.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.monsterparty.R
import com.example.monsterparty.REQUEST_SETTINGS
import com.example.monsterparty.model.UserDatabase
import com.example.monsterparty.model.UserRepository
import com.example.monsterparty.viewmodel.UserViewModel
import com.example.monsterparty.viewmodel.UserViewModelProvider
import kotlinx.android.synthetic.main.login_page.*

class LoginActivity: AppCompatActivity() {
    private lateinit var loginUserViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val dao = UserDatabase.getInstance(application)?.userDao
        val repository = dao?.let { UserRepository(it) }
        val factory = repository?.let { UserViewModelProvider(it) }
        loginUserViewModel = factory?.let { ViewModelProvider(this, it).get(UserViewModel::class.java)}!!
        loginUserViewModel.message.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        lg_btn_clear_all.setOnClickListener(View.OnClickListener {
            loginUserViewModel.clearAll()
        })
    }
    fun openCreateAccount(view: View){
        val intent = Intent()
        intent.setClass(this, CreateAccountActivity::class.java)
        startActivityForResult(intent, REQUEST_SETTINGS)
    }
}