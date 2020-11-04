package com.example.monsterparty

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.monsterparty.databinding.ActivityMainBinding
import com.example.monsterparty.model.post.PostDatabase
import com.example.monsterparty.model.post.PostRepository
import com.example.monsterparty.view.*
import com.example.monsterparty.viewmodel.PostViewModel
import com.example.monsterparty.viewmodel.PostViewModelProvider
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = ".MainActivity"
const val REQUEST_SETTINGS = 925
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var postViewModel: PostViewModel

    private var getUsername : String? = null
    private var getImage : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        readSharedPreferences()
        val dao = PostDatabase.getInstance(application)?.postDao
        val repository = dao?.let { PostRepository(it) }
        val factory = repository?.let { PostViewModelProvider(it) }
        postViewModel = factory?.let { ViewModelProvider(this, it).get(PostViewModel::class.java) }!!

        main_btn_post.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.setClass(this,PostActivity::class.java)
            startActivityForResult(intent, REQUEST_SETTINGS)
        })
        initRecyclerView()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_list,menu)
        return true
    }

    private fun initRecyclerView(){
        postViewModel.posts.observe(this, Observer {
            val fragment = PostFragment.createPostFragmentDisplay(it)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, fragment)
                    .commit()
        })
    }
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        displayPostList()
//    private fun displayPostList(){
//        postViewModel.posts.observe(this, Observer {
//            binding.recyclerView.adapter = PostAdapter(it)
//        })
//    }
    //todo Search & Settings Activities
    private var menuIntent = Intent()
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
        getUsername = sharedPreferences.getString("username","N/A")
        Log.d(TAG, "readSharedPreferences: $getUsername")
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
                Glide.with(this).load(it).placeholder(R.drawable.ic_default).into(main_iv_user_picture)

            }
        }
    }
}