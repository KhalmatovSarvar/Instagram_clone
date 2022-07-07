package com.example.instagram_clone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram_clone.R
import com.example.instagram_clone.adapter.FavouriteAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram_clone.manager.AuthManager
import com.example.instagram_clone.manager.DatabaseManager
import com.example.instagram_clone.manager.handler.DBPostHandler
import com.example.instagram_clone.manager.handler.DBPostsHandler

import com.example.instagram_clone.model.Post
import com.example.instagram_clone.utils.DialogListener
import com.example.instagram_clone.utils.Utils

class FavouriteFragment : BaseFragment() {
    val TAG = FavouriteFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView

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
        recyclerView.setLayoutManager(GridLayoutManager(activity, 1))

        loadLikedFeeds()
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = FavouriteAdapter(this, items)
        recyclerView.adapter = adapter
    }

    fun likeOrUnlikePost(post: Post) {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.likeFeedPost(uid, post)

        loadLikedFeeds()
    }

    fun loadLikedFeeds() {
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadLikedFeeds(uid, object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                dismissLoading()
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }

    fun showDeleteDialog(post: Post){
        Utils.dialogDouble(requireContext(), getString(R.string.str_delete_post), object :
            DialogListener {
            override fun onCallback(isChosen: Boolean) {
                if(isChosen){
                    deletePost(post)
                }
            }
        })
    }

    fun deletePost(post: Post) {
        DatabaseManager.deletePost(post, object : DBPostHandler {
            override fun onSuccess(post: Post?) {
                loadLikedFeeds()
            }

            override fun onError(e: java.lang.Exception) {
                TODO("Not yet implemented")
            }
        })
    }
}
