package com.example.monsterparty.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.monsterparty.R
import com.example.monsterparty.databinding.CreateAccountPageBinding
import com.example.monsterparty.model.user.User
import com.example.monsterparty.model.user.UserDatabase
import com.example.monsterparty.model.user.UserRepository
import com.example.monsterparty.viewmodel.UserViewModel
import com.example.monsterparty.viewmodel.UserViewModelProvider
import kotlinx.android.synthetic.main.create_account_page.*

private const val TAG = ".CreateAccountActivity"
class CreateAccountActivity: AppCompatActivity() {

    private val IMAGE_PICK_CODE = 117
    private lateinit var binding: CreateAccountPageBinding
    private lateinit var createUserViewModel: UserViewModel
    private lateinit var userList : List<User>
    var photoUri: Uri? = null

    var usernameCheck = false
    var emailCheck = false
    var ageCheck = false
    var spinnerCheck = false
    var passCheck = false

    var num = false
    var upperCase = false
    var lowerCase = false
    var special = false
    var uniqueName = false
    var uniqueEmail = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.create_account_page)
        val dao = UserDatabase.getInstance(application)?.userDao
        val repository = dao?.let { UserRepository(it) }
        val provider = repository?.let { UserViewModelProvider(it) }
        createUserViewModel = provider?.let { ViewModelProvider(this, it).get(UserViewModel::class.java)}!!
        binding.userViewModel = createUserViewModel
        binding.lifecycleOwner = this

        createUserViewModel.message.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        createUserViewModel.users.observe(this, Observer {
            userList = it
        })
        ca_et_username.addTextChangedListener(textWatcher)
        ca_et_email.addTextChangedListener(textWatcher)
        ca_et_age.addTextChangedListener(textWatcher)
        ca_et_desc.addTextChangedListener(textWatcher)
        ca_et_password.addTextChangedListener(textWatcher)
        ca_et_password_match.addTextChangedListener(textWatcher)

        ca_spn_lang.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                createUserViewModel.createUserLang = ca_spn_lang.getItemAtPosition(position).toString()
                spinnerCheck = when(position){
                    0 -> false
                    else -> true
                }
                Log.d(TAG, "onItemSelected: ${createUserViewModel.createUserLang}")
                buttonCheck()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        ca_btn_create.setOnClickListener {
            val userName = ca_et_username.text.toString().trim()
            val email = ca_et_email.text.toString().trim()
            val password1 = ca_et_password.text.toString().trim()
            val password2 = ca_et_password_match.text.toString().trim()
            checkPassword(password1)

            when(userList.isEmpty()){
                true -> {
                    uniqueName = true
                    uniqueEmail = true
                }
                else ->{
                    checkIfUniqueName(userName)
                    checkIfUniqueEmail(email)
                }
            }

            if (password1 != password2) {
                Toast.makeText(this, "Passwords must match", Toast.LENGTH_LONG).show()
            }else if(!email.contains("@") || !email.contains(".com")|| email.length<8){
                Toast.makeText(this, "Email is invalid", Toast.LENGTH_LONG).show()
            }else if (userName.length<8){
                Toast.makeText(this, "Username is too short", Toast.LENGTH_LONG).show()
            }else if (password1.length<8){
                Toast.makeText(this, "Password is too short", Toast.LENGTH_LONG).show()
            } else if(!num){
                Toast.makeText(this, "Password needs a number", Toast.LENGTH_LONG).show()
            }else if(!upperCase){
                Toast.makeText(this, "Password needs an uppercase letter", Toast.LENGTH_LONG).show()
            }else if(!lowerCase){
                Toast.makeText(this, "Password needs a lowercase letter", Toast.LENGTH_LONG).show()
            }else if(!special){
                Toast.makeText(this, "Password needs a special character", Toast.LENGTH_LONG).show()
            }else if(!uniqueName){
                Toast.makeText(this, "This username is already taken", Toast.LENGTH_LONG).show()
            }else if(!uniqueEmail){
                Toast.makeText(this, "This email is already taken", Toast.LENGTH_LONG).show()
            }
            else {
                createUserViewModel.saveUser()
                val intent = Intent()
                intent.setClass(this, LoginActivity::class.java)
                startActivityForResult(intent, IMAGE_PICK_CODE)
            }
        }
    }

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            usernameCheck = ca_et_username.text.toString().isNotEmpty()
            emailCheck = ca_et_email.text.toString().isNotEmpty()
            ageCheck = ca_et_age.text.toString().isNotEmpty()
            passCheck = ca_et_password.text.toString().isNotEmpty()
            buttonCheck()
        }
    }
    fun getImage(view: View){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    //icon-user-default-420x420.png
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        photoUri = data?.data
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            ca_iv_image.setImageURI((photoUri))
            createUserViewModel.createUserPicture = photoUri!!
            Log.d(TAG, "onActivityResult: ${createUserViewModel.createUserPicture}")
        }
    }
    private fun buttonCheck(){
        ca_btn_create.isEnabled = when (usernameCheck && emailCheck && ageCheck && passCheck && spinnerCheck){
            true -> true
            else -> false
        }
    }
    private fun checkPassword(pass: String){
        val specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?"
        var c: Char

        for (element in pass) {
            c = element
            when {
                Character.isDigit(c) -> num = true
                Character.isUpperCase(c) -> upperCase = true
                Character.isLowerCase(c) -> lowerCase = true
                specialChars.contains(c.toString()) -> special = true
            }
        }
    }
    private fun checkIfUniqueName(user : String){
        Log.d(TAG, "checkIfUniqueName: $userList")
        for(element in userList)
            if(user != element.userName)
                uniqueName = true
    }
    private fun checkIfUniqueEmail(email : String){
        for(element in userList)
            if(email != element.userEmail)
                uniqueEmail = true
    }
}