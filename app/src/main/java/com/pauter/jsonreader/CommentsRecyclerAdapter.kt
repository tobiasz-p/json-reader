package com.pauter.jsonreader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pauter.jsonreader.model.Comment

class CommentsRecyclerAdapter(private val commentsList: List<Comment> ) : RecyclerView.Adapter<CommentsRecyclerAdapter.CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        // inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_comment_recycler, parent, false)

        return CommentsViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.textViewTitle.text = commentsList[position].name
        holder.textViewEmail.text = commentsList[position].email
        holder.textViewBody.text = commentsList[position].body
        }


    override fun getItemCount(): Int {
        return commentsList.size
    }

    /**
     * ViewHolder class
     */
    inner class CommentsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textViewTitle: AppCompatTextView = view.findViewById(R.id.textViewTitle)
        val textViewEmail: AppCompatTextView = view.findViewById(R.id.textViewEmail)
        val textViewBody: AppCompatTextView = view.findViewById(R.id.textViewBody)
    }

}
