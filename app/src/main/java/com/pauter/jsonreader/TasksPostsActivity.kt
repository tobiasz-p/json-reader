package com.pauter.jsonreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.pauter.jsonreader.model.Post
import com.pauter.jsonreader.model.Todo
import java.net.URL

class TasksPostsActivity : AppCompatActivity(), PostsRecyclerAdapter.OnItemClickListener {

    private var tasksList: ArrayList<Todo> = ArrayList<Todo>()
    private var postsList: ArrayList<Post> = ArrayList<Post>()
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var postRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_posts)
        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        postRecyclerView = findViewById(R.id.postRecyclerView)

        val userId = intent.getStringExtra("id")

        val threadGetTodos = Thread() {
            run {
                val url = "https://jsonplaceholder.typicode.com/todos"
                val body = URL(url).readText()
                val tempTodosList = ArrayList(Klaxon().parseArray<Todo>(body))

                for (task: Todo in tempTodosList) {
                    if (task.userId.toString() == userId) {
                        tasksList.add(task)
                    }
                }
            }
            runOnUiThread() {
                taskRecyclerView.adapter = TodosRecyclerAdapter(tasksList)
            }
        }
        threadGetTodos.start()

        val threadGetPosts = Thread() {
            run {
                val url = "https://jsonplaceholder.typicode.com/posts"
                val body = URL(url).readText()
                val tempPostsList = ArrayList(Klaxon().parseArray<Post>(body))

                for (post: Post in tempPostsList) {
                    if (post.userId.toString() == userId) {
                        postsList.add(post)
                    }
                }
            }
            runOnUiThread() {
                postRecyclerView.adapter = PostsRecyclerAdapter(postsList, this)
            }
        }
        threadGetPosts.start()

        val returnButton: Button = findViewById(R.id.returnButton)
        returnButton.setOnClickListener() {
            this.finish()
        }

    }

    override fun onItemClick(position: Int) {
        val clickedItemId = postsList[position].id
        val CommentsIntent = Intent(this, CommentsActivity::class.java)
        CommentsIntent.putExtra("id", clickedItemId.toString())
        postRecyclerView.adapter?.notifyItemChanged(position)
        startActivity(CommentsIntent)
    }
}