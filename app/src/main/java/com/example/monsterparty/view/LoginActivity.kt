package com.example.monsterparty.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.monsterparty.MainActivity
import com.example.monsterparty.R
import com.example.monsterparty.REQUEST_SETTINGS
import com.example.monsterparty.model.user.User
import com.example.monsterparty.model.user.UserDatabase
import com.example.monsterparty.model.user.UserRepository
import com.example.monsterparty.viewmodel.UserViewModel
import com.example.monsterparty.viewmodel.UserViewModelProvider
import kotlinx.android.synthetic.main.login_page.*

class LoginActivity: AppCompatActivity() {
    private lateinit var userList: List<User>
    private lateinit var loginUserViewModel: UserViewModel
    private val REQUEST_CODE = 7755

    var usernameCheck = false
    var passCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        //ViewModel setup
        val dao = UserDatabase.getInstance(application)?.userDao
        val repository = dao?.let { UserRepository(it) }
        val factory = repository?.let { UserViewModelProvider(it) }
        loginUserViewModel = factory?.let { ViewModelProvider(this, it).get(UserViewModel::class.java)}!!

        //Getting List of Users
        loginUserViewModel.users.observe(this, Observer {
            userList = it
        })

        //Event message observer
        loginUserViewModel.message.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        //OnClick listeners
        lg_btn_login.setOnClickListener(View.OnClickListener {
            val un = lg_et_username.text.toString()
            val ps = lg_et_password.text.toString()
            if (un.length<8){
                Toast.makeText(this, "Username is too short", Toast.LENGTH_LONG).show()
            }else if (ps.length<8){
                Toast.makeText(this, "Password is too short", Toast.LENGTH_LONG).show()
            }else
                verifyUser(un, ps)
        })

        lg_btn_clear_all.setOnClickListener(View.OnClickListener {
            loginUserViewModel.clearAll()
        })
        lg_et_username.addTextChangedListener(textWatcher)
        lg_et_password.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object: TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            usernameCheck = lg_et_username.text.toString().isNotEmpty()
            passCheck = lg_et_password.text.toString().isNotEmpty()
            buttonCheck()
        }

    }
    fun openCreateAccount(view: View){
        val intent = Intent()
        intent.setClass(this, CreateAccountActivity::class.java)
        startActivityForResult(intent, REQUEST_SETTINGS)
    }
    private fun verifyUser(un : String, ps : String){
        var userExists = false
        for (user in userList) {
            if (un == user.userName && ps == user.userPassword) {
                val sharedPreferences = getSharedPreferences(
                        "monster_party_preferences",
                        Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("username",user.userName)
                editor.putString("image", user.userPicture)
                editor.commit()
                userExists = true
            }
        }
        when (userExists){
            true ->{
                val intent = Intent()
                intent.setClass(this, MainActivity::class.java)
                startActivityForResult(intent,REQUEST_CODE)
                Toast.makeText(this,"$un has arrived at the party!",Toast.LENGTH_SHORT).show()
            }
            else ->{
                Toast.makeText(this,"User not found",Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun buttonCheck(){
        lg_btn_login.isEnabled = when (usernameCheck && passCheck){
            true -> true
            else -> false
        }
    }
}