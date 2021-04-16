package com.pauter.jsonreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.pauter.jsonreader.model.User
import java.net.URL

class MainActivity : AppCompatActivity(), UsersRecyclerAdapter.OnItemClickListener {
    private lateinit var usersList: ArrayList<User>
    private lateinit var recyclerViewUsers: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val threadGetUsers = Thread() {
            println("Hello world")
            run {
                Thread.sleep(1000)
                val url = "https://jsonplaceholder.typicode.com/users"
                val body = URL(url).readText()
                usersList = ArrayList(Klaxon().parseArray<User>(body))
            }
            runOnUiThread() {
                initViews()
                initObjects()
            }
        }
        threadGetUsers.start()
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "User $position clicked", Toast.LENGTH_SHORT).show()
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
        val usersRecyclerAdapter = UsersRecyclerAdapter(usersList, this)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewUsers.layoutManager = mLayoutManager
        recyclerViewUsers.itemAnimator = DefaultItemAnimator()
        recyclerViewUsers.setHasFixedSize(true)
        recyclerViewUsers.adapter = usersRecyclerAdapter
    }
}
