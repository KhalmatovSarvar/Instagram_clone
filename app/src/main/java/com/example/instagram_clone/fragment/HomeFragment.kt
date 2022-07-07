package com.example.instagram_clone.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram_clone.R
import com.example.instagram_clone.adapter.HomeAdapter
import com.example.instagram_clone.manager.AuthManager
import com.example.instagram_clone.manager.DatabaseManager
import com.example.instagram_clone.manager.handler.DBPostHandler
import com.example.instagram_clone.manager.handler.DBPostsHandler
import com.example.instagram_clone.model.Post
import com.example.instagram_clone.utils.DialogListener
import com.example.instagram_clone.utils.Utils
import java.lang.RuntimeException

class HomeFragment : BaseFragment() {
    val TAG = HomeFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView
    private var listener: HomeFragment.HomeListener? = null

    lateinit var iv_photo: ImageView
    var feeds = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser && feeds.size>0){
            loadMyFeeds()
        }
    }

    /*
  onAttach is for communication of Fragments
   */

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is HomeListener) {
            context
        } else {
            throw RuntimeException("$context must implement UploadListener")
        }
    }

    /*
  onAttach is for communication of Fragments
   */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)


        val iv_camera = view.findViewById<ImageView>(R.id.iv_camera)
        iv_camera.setOnClickListener {
            listener!!.scrollToUpload()
        }

        loadMyFeeds()
    }

    private fun loadMyFeeds() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFeeds(uid, object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                Log.d("@@@",posts.size.toString())
                dismissLoading()
                feeds.clear()
                feeds.addAll(posts)
                refreshAdapter(feeds)
            }

            override fun onError(e: java.lang.Exception) {
                dismissLoading()
            }
        })
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = HomeAdapter(this, items)
        recyclerView.adapter = adapter

    }


    fun likeOrUnlikePost(post: Post) {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.likeFeedPost(uid, post)
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
                loadMyFeeds()
            }

            override fun onError(e: Exception) {

            }
        })
    }

    /*
   this interface is created for communication with UploadFragment
     */
    interface HomeListener {
        fun scrollToUpload()
    }
}