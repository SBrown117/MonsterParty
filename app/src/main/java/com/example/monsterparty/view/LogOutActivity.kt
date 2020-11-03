package com.example.monsterparty.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monsterparty.MainActivity
import com.example.monsterparty.R
import kotlinx.android.synthetic.main.log_out.*

class LogOutActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_out)
        val sharedPreferences = getSharedPreferences(
            "monster_party_preferences",
            Context.MODE_PRIVATE)
        val un = sharedPreferences.getString("username","N/A")
        lo_tv_peace.text = getString(R.string.log_out_as_username) + " $un"
        lo_btn_logout.setOnClickListener(View.OnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("username","N/A")
            editor.putString("image", "N/A")
            editor.commit()
            Toast.makeText(this,"We'll miss you ;(",Toast.LENGTH_SHORT).show()
            return2Main()
        })
        lo_btn_cancel.setOnClickListener(View.OnClickListener {
            return2Main()
        })
    }
    private fun return2Main(){
        val intent = Intent()
        intent.setClass(this,MainActivity::class.java)
        startActivityForResult(intent,89)
    }
}