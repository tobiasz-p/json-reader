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
    private lateinit var listUsers: MutableList<User>


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
        val clickedItem = usersList[position]
        val intentTasksPosts = Intent(this, TasksPostsActivity::class.java)
        recyclerViewUsers.adapter?.notifyItemChanged(position)
        startActivity(intentTasksPosts)
    }

    private fun initViews() {
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers) as RecyclerView
    }


    private fun initObjects() {
        listUsers = ArrayList()
        val usersRecyclerAdapter = UsersRecyclerAdapter(listUsers, this)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewUsers.layoutManager = mLayoutManager
        recyclerViewUsers.itemAnimator = DefaultItemAnimator()
        recyclerViewUsers.setHasFixedSize(true)
        recyclerViewUsers.adapter = usersRecyclerAdapter

        listUsers.clear()
        listUsers.addAll(usersList)

    }
}
