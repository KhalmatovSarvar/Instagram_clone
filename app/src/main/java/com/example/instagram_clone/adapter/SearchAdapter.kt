package com.example.instagram_clone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram_clone.R
import com.example.instagram_clone.fragments.SearchFragment
import com.example.instagram_clone.model.User
import com.google.android.material.imageview.ShapeableImageView

class SearchAdapter(var fragment: SearchFragment,var items:ArrayList<User>):BaseAdapter() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_user_search,parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user:User = items[position]
        if (holder is UserViewHolder){
            holder.tv_fullname.text = user.fullname
            holder.tv_email.text = user.email

            Glide.with(fragment).load(user.userImg)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(holder.iv_profile)

            var tv_follow = holder.tv_follow
            tv_follow.setOnClickListener {
                if (!user.isFollowed){
                    tv_follow.text = fragment.getString(R.string.str_following)
                }else{
                    tv_follow.text = fragment.getString(R.string.str_follow)
                }
                fragment.followOrUnfollow(user)
            }

            if (!user.isFollowed){
                tv_follow.text = fragment.getString(R.string.str_follow)
            }else{
                tv_follow.text = fragment.getString(R.string.str_following)
            }
        }


    }

    class UserViewHolder(var view:View):RecyclerView.ViewHolder(view){
        var iv_profile: ShapeableImageView
        var tv_fullname:TextView
        var tv_email:TextView
        var tv_follow:TextView

        init {
            iv_profile = view.findViewById(R.id.iv_profile)
            tv_fullname = view.findViewById(R.id.tv_fullname)
            tv_email = view.findViewById(R.id.tv_email)
            tv_follow = view.findViewById(R.id.tv_follow)
        }
    }
}