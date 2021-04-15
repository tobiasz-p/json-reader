package com.pauter.jsonreader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pauter.jsonreader.model.User

class UsersRecyclerAdapter(
        private val listUsers: List<User>,
        private val listener: OnItemClickListener
) : RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_recycler, parent, false)

        return UserViewHolder(itemView)
        
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.textViewName.text = listUsers[position].name
        holder.textViewPoints.text = listUsers[position].email
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }


    /**
     * ViewHolder class
     */
    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view),
    View.OnClickListener{

        val textViewName: AppCompatTextView = view.findViewById(R.id.textViewName) as AppCompatTextView
        val textViewPoints: AppCompatTextView = view.findViewById(R.id.textViewPoints) as AppCompatTextView

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
