package com.example.instagram_clone.fragment

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram_clone.R
import com.example.instagram_clone.adapter.ProfileAdapter
import com.example.instagram_clone.manager.AuthManager
import com.example.instagram_clone.manager.DatabaseManager
import com.example.instagram_clone.manager.StorageManager
import com.example.instagram_clone.manager.handler.*
import com.example.instagram_clone.model.Post
import com.example.instagram_clone.model.User
import com.example.instagram_clone.utils.Logger
import com.google.android.material.imageview.ShapeableImageView
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import org.w3c.dom.Text

class ProfileFragment : BaseFragment() {

    val TAG = ProfileFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView
    lateinit var iv_profile: ShapeableImageView
    lateinit var tv_email: TextView
    lateinit var tv_fullname: TextView
    lateinit var tv_posts: TextView

    lateinit var tv_followers:TextView
    lateinit var tv_following:TextView


    var pickedPhoto: Uri? = null
    var allPhotos = ArrayList<Uri>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        loadUserInfo()
        loadMyPosts()
        loadMyFollowing()
        loadMyFollowers()
        recyclerView = view.findViewById(R.id.rv_profile)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        iv_profile = view.findViewById(R.id.iv_profile)
        tv_fullname = view.findViewById(R.id.tv_fullname)
        tv_email = view.findViewById(R.id.tv_email)
        tv_posts = view.findViewById(R.id.tv_posts)
        tv_followers = view.findViewById(R.id.tv_followers)
        tv_following = view.findViewById(R.id.tv_following)

        val iv_logout = view.findViewById<ImageView>(R.id.iv_log_out)
        iv_logout.setOnClickListener {
            Log.d("button", "clicked")
            AuthManager.signOut()
            callSignInActivity(requireActivity())
        }
        iv_profile.setOnClickListener {
            pickFishBunPhoto()
        }
    }


    private fun loadMyFollowing() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFollowing(uid, object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                tv_following.text = users.size.toString()
            }

            override fun onError(e: java.lang.Exception) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun loadMyFollowers() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFollowers(uid, object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                tv_followers.text = users.size.toString()
            }

            override fun onError(e: java.lang.Exception) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun loadMyPosts() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadPosts(uid, object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                tv_posts.text = posts.size.toString()
                refreshAdapter(posts)
            }

            override fun onError(e: java.lang.Exception) {
                TODO("Not yet implemented")
            }
        })
    }

    fun loadUserInfo() {
        DatabaseManager.loadUser(AuthManager.currentUser()!!.uid, object : DBUserHandler {
            override fun onSuccess(user: User?) {
                if (user != null) {
                    showUserInfo(user)
                }
            }

            override fun onError(e: java.lang.Exception) {
            }
        })
    }

    private fun showUserInfo(user: User) {
        tv_fullname.text = user.fullname
        tv_email.text = user.email
        Glide.with(this).load(user.userImg)
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(iv_profile)
    }

    /**
     * Pick photo using FishBun library
     */

    private fun pickFishBunPhoto() {
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setMinCount(1)
            .setSelectedImages(allPhotos)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    private val photoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                allPhotos =
                    it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
                pickedPhoto = allPhotos.get(0)
                uploadPickedPhoto()
                uploadUserPhoto()
            }
        }

    private fun uploadPickedPhoto() {
        if (pickedPhoto != null) {
            Logger.d(TAG, pickedPhoto!!.path.toString())
        }
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = ProfileAdapter(this, items)
        recyclerView.adapter = adapter
    }

    private fun uploadUserPhoto() {
        if (pickedPhoto == null) return
        StorageManager.uploadUserPhoto(pickedPhoto!!, object : StorageHandler {
            override fun onSuccess(imgUrl: String) {
                DatabaseManager.updateUserImage(imgUrl)
                iv_profile.setImageURI(pickedPhoto)
            }

            override fun onError(e: Exception) {
            }
        })
    }

}