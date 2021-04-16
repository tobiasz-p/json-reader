package com.pauter.jsonreader

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.pauter.jsonreader.model.Post
import com.pauter.jsonreader.model.Todo
import java.net.URL

class TasksPostsActivity : AppCompatActivity(), PostsRecyclerAdapter.OnItemClickListener {

    private lateinit var todosList: ArrayList<Todo>
    private lateinit var postsList: ArrayList<Post>
    private var userPosts: ArrayList<Post> = arrayListOf<Post>()
    private lateinit var recyclerViewTasksPosts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_posts)
        val id = intent.getStringExtra("id")

        recyclerViewTasksPosts = findViewById(R.id.recyclerViewTasksPosts) as RecyclerView
        initObjects()

        val threadTasksPosts = Thread() {
            println("Hello world")
            run {
                Thread.sleep(1000)
                var url = "https://jsonplaceholder.typicode.com/todos"
                var body = URL(url).readText()
                todosList = ArrayList(Klaxon().parseArray<Todo>(body))

                url = "https://jsonplaceholder.typicode.com/posts"
                body = URL(url).readText()
                postsList = ArrayList(Klaxon().parseArray<Post>(body))
                for (post in postsList) {
                    if (post.userId.toString() == id) {
                        userPosts.add(post)
                    }
                }
                recyclerViewTasksPosts.adapter?.notifyDataSetChanged()
                print("userPosts: ")
                print(userPosts)
            }
            runOnUiThread() {
                //initViews()
                //initObjects()
            }
        }
        threadTasksPosts.start()
    }


    override fun onItemClick(position: Int) {
        Toast.makeText(this, "User $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = postsList[position]
        val intentTasksPosts = Intent(this, TasksPostsActivity::class.java)
        recyclerViewTasksPosts.adapter?.notifyItemChanged(position)
        startActivity(intentTasksPosts)
    }

    private fun initViews() {
        recyclerViewTasksPosts = findViewById(R.id.recyclerViewTasksPosts) as RecyclerView
    }


    private fun initObjects() {
        val tasksPostsRecyclerAdapter = PostsRecyclerAdapter(userPosts, this)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewTasksPosts.layoutManager = mLayoutManager
        recyclerViewTasksPosts.itemAnimator = DefaultItemAnimator()
        recyclerViewTasksPosts.setHasFixedSize(true)
        recyclerViewTasksPosts.adapter = tasksPostsRecyclerAdapter
    }
}
