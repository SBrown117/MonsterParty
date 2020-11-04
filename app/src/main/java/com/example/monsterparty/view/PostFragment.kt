package com.example.monsterparty.view

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monsterparty.R
import com.example.monsterparty.model.user.Post
import kotlinx.android.synthetic.main.post_display.view.*

class PostFragment: Fragment() {

    companion object{
        val KEY_POST_FRAGMENT = "KEY_POST_FRAGMENT"
        fun createPostFragmentDisplay(postList: List<Post>): PostFragment{
            val postFragment = PostFragment()
            val bundle = Bundle()

            bundle.putParcelableArrayList(KEY_POST_FRAGMENT, postList as java.util.ArrayList<out Parcelable>)
            postFragment.arguments = bundle
            return postFragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.post_display,container,false)
        arguments?.getParcelableArrayList<Post>(KEY_POST_FRAGMENT)
                ?.let{
                    inflateRecyclerView(it,view)
                }
        return view
    }
    private fun inflateRecyclerView(postList: List<Post>, view: View){
        val linearLayoutManager = LinearLayoutManager(activity)
        view.recycler_view.layoutManager = linearLayoutManager
        view.recycler_view.adapter = PostAdapter(postList)
    }
}