package com.pauter.jsonreader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pauter.jsonreader.model.Todo

class TodosRecyclerAdapter(
        private val postsList: List<Todo>
) : RecyclerView.Adapter<TodosRecyclerAdapter.TodosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        // inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_task_recycler, parent, false)

        return TodosViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        holder.textViewTitle.text = postsList[position].title
        if (postsList[position].completed) {
            holder.textViewCompleted.text = "zrobione"
        } else {
            holder.textViewCompleted.text = "do zrobienia"
        }

    }

    override fun getItemCount(): Int {
        return postsList.size
    }


    /**
     * ViewHolder class
     */
    inner class TodosViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textViewTitle: AppCompatTextView = view.findViewById(R.id.textViewTitle)
        val textViewCompleted: AppCompatTextView = view.findViewById(R.id.textViewCompleted)
    }

}
