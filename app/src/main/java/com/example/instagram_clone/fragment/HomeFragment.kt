package com.example.instagram_clone.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.instagram_clone.R
import java.lang.RuntimeException

class HomeFragment:BaseFragment() {
    val TAG = HomeFragment::class.java.simpleName
    private var listener:HomeFragment.HomeListener? = null

    lateinit var iv_photo: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home,container,false)
        initViews(view)
        return view
    }

    /*
  onAttach is for communication of Fragments
   */

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is HomeFragment.HomeListener){
            context
        }else{
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
        iv_photo = view.findViewById(R.id.iv_camera)
        iv_photo.setOnClickListener {
        listener!!.scrollToUpload()
        }
    }

    /*
   this interface is created for communication with UploadFragment
     */
    interface HomeListener{
        fun scrollToUpload()
    }
}