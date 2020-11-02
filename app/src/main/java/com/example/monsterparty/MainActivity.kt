package com.example.monsterparty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_list,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId)
    {
        R.id.menu_search ->{
            /*
                todo
                  make search fragment
                  user can click on another user
                    and view their posts
             */
            true
        }
        R.id.menu_settings ->{
            /*
                todo
                  make settings fragment
                  user can update their existing data
             */
            true
        }
        R.id.menu_log_out ->{
            /*
                todo
                  make log out fragment
                  user can log out of the application
                    should clear current shared preferences
                    being in other activities should return user
                        to the login page
             */

            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}