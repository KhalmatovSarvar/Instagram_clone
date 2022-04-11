package com.example.instagram_clone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram_clone.R
import com.example.instagram_clone.adapter.FavouriteAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.instagram_clone.model.Post

class FavouriteFragment:BaseFragment() {
    val TAG = FavouriteFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView

    lateinit var iv_photo: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)
        initViews(view)
        return view
    }


    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)



        refreshAdapter(loadPosts())

    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = FavouriteAdapter(this, items)
        recyclerView.adapter = adapter

    }

    private fun loadPosts(): ArrayList<Post> {
        val items = ArrayList<Post>()
        items.add(Post("https://images.unsplash.com/photo-1626497361649-81cc097e9bfd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8bXVzbGltfGVufDB8MnwwfHw%3D&auto=format&fit=crop&w=500&q=60"))
        items.add(Post("https://images.unsplash.com/photo-1537511446984-935f663eb1f4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8bWFufGVufDB8MHwwfHw%3D&auto=format&fit=crop&w=500&q=60"))
        items.add(Post("https://images.unsplash.com/photo-1488161628813-04466f872be2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fG1hbnxlbnwwfDF8MHx8&auto=format&fit=crop&w=500&q=60"))
        return items
    }

}
