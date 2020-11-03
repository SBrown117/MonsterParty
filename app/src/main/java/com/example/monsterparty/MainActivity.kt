package com.example.monsterparty

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.monsterparty.view.LogOutActivity
import com.example.monsterparty.view.LoginActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = ".MainActivity"
const val REQUEST_SETTINGS = 925
class MainActivity : AppCompatActivity() {

    private var getUsername : String? = null
    private var getImage : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readSharedPreferences()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_list,menu)
        return true
    }

    //todo Search & Settings Activities
    var menuIntent = Intent()
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId)
    {
        R.id.menu_search ->{
//            menuIntent.setClass(this,SearchActivity::class.java)
//            startActivityForResult(menuIntent, REQUEST_SETTINGS)
            true
        }
        R.id.menu_settings ->{
//            menuIntent.setClass(this,UpdateActivity::class.java)
//            startActivityForResult(menuIntent, REQUEST_SETTINGS)
            true
        }
        R.id.menu_log_out ->{
            menuIntent.setClass(this,LogOutActivity::class.java)
            startActivityForResult(menuIntent, REQUEST_SETTINGS)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    private fun readSharedPreferences() {
        val sharedPreferences = getSharedPreferences(
            "monster_party_preferences",
            Context.MODE_PRIVATE)
        getUsername = sharedPreferences.getString("username","N/A").toString()
        getImage = sharedPreferences.getString("image","N/A")
        if(getUsername == "N/A" || getImage == "N/A" || getUsername == null || getImage == null){
            val intent = Intent()
            intent.setClass(this, LoginActivity::class.java)
            startActivityForResult(intent, REQUEST_SETTINGS)
        }
        else{
            main_tv_user_name.text = getUsername
            val storageRef = FirebaseStorage.getInstance().getReference("MonsterPartyPics/$getImage")
            Log.d(TAG, "storageRef: $storageRef")
            storageRef.downloadUrl.addOnSuccessListener {
                Glide.with(this).load(it).into(main_iv_user_picture)

            }
        }
    }
}