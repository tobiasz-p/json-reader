package com.pauter.jsonreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.pauter.jsonreader.model.Post
import com.pauter.jsonreader.model.Todo
import com.pauter.jsonreader.model.User
import java.net.URL

class MainActivity : AppCompatActivity(), UsersRecyclerAdapter.OnItemClickListener {
    private lateinit var usersList: ArrayList<User>
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var todosCounter: IntArray
    private lateinit var uncompletedCounter: IntArray
    private lateinit var postsCounter: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val threadGetUsers = Thread() {
            println("Hello world")
            run {
                val url = "https://jsonplaceholder.typicode.com/users"
                val body = URL(url).readText()
                usersList = ArrayList(Klaxon().parseArray<User>(body))

                todosCounter = IntArray(usersList.size) { 0 }
                uncompletedCounter = IntArray(usersList.size) { 0 }
                postsCounter = IntArray(usersList.size) { 0 }


                val urlTodos = "https://jsonplaceholder.typicode.com/todos"
                val bodyTodos = URL(urlTodos).readText()
                val todosList = ArrayList(Klaxon().parseArray<Todo>(bodyTodos))


                todosCounter = IntArray(usersList.size) { 0 }
                uncompletedCounter = IntArray(usersList.size) { 0 }

                for (task: Todo in todosList) {
                    if (!task.completed) {
                        uncompletedCounter[task.userId - 1]++
                    }
                    todosCounter[task.userId - 1]++
                }

                val urlPosts = "https://jsonplaceholder.typicode.com/posts"
                val bodyPosts = URL(urlPosts).readText()
                val postsList = ArrayList(Klaxon().parseArray<Post>(bodyPosts))


                for (post: Post in postsList) {
                    postsCounter[post.userId - 1]++
                }
            }
            runOnUiThread() {
                initViews()
                initObjects()
            }
        }
        threadGetUsers.start()
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Wybrano użytkownika nr $position.", Toast.LENGTH_SHORT).show()
        val clickedItemId = usersList[position].id
        val TasksPostsIntent = Intent(this, TasksPostsActivity::class.java)
        TasksPostsIntent.putExtra("id", clickedItemId.toString())
        recyclerViewUsers.adapter?.notifyItemChanged(position)
        startActivity(TasksPostsIntent)
    }

    private fun initViews() {
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers) as RecyclerView
    }


    private fun initObjects() {
        val usersRecyclerAdapter = UsersRecyclerAdapter(usersList, this, todosCounter, uncompletedCounter, postsCounter)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewUsers.layoutManager = mLayoutManager
        recyclerViewUsers.itemAnimator = DefaultItemAnimator()
        recyclerViewUsers.setHasFixedSize(true)
        recyclerViewUsers.adapter = usersRecyclerAdapter
    }
}
