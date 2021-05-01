package com.pauter.jsonreader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pauter.jsonreader.model.Post

class PostsRecyclerAdapter(
        private val postsList: List<Post>,
        private val listener: OnItemClickListener
) : RecyclerView.Adapter<PostsRecyclerAdapter.PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        // inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_post_recycler, parent, false)

        return PostsViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.textViewTitle.text = postsList[position].title
        holder.textViewBody.text = postsList[position].body
    }

    override fun getItemCount(): Int {
        return postsList.size
    }


    /**
     * ViewHolder class
     */
    inner class PostsViewHolder(view: View) : RecyclerView.ViewHolder(view),
            View.OnClickListener{

        val textViewTitle: AppCompatTextView = view.findViewById(R.id.textViewTitle)
        val textViewBody: AppCompatTextView = view.findViewById(R.id.textViewBody)

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
