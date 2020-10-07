package com.example.githubuser.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.model.User
import kotlinx.android.synthetic.main.list_user_item.view.*

class UserAdapter(private var userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_user_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(user = userList[position])
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.apply {
                username.text = user.username
                url.text = user.url
            }

            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(itemView.avatar)
        }
    }

    fun submitList(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }
}
