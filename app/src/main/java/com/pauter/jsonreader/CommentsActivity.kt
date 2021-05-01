package com.pauter.jsonreader

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.pauter.jsonreader.model.Comment
import java.net.URL

class CommentsActivity : AppCompatActivity() {

    private var commentsList: ArrayList<Comment> = ArrayList<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        val recyclerViewComments: RecyclerView = findViewById(R.id.recyclerViewComments)

        val postId = intent.getStringExtra("id")

        val threadGetComments = Thread() {
            run {
                val url = "https://jsonplaceholder.typicode.com/comments"
                val body = URL(url).readText()
                val tempCommentsList = ArrayList(Klaxon().parseArray<Comment>(body))

                for (comment: Comment in tempCommentsList) {
                    if (comment.postId.toString() == postId) {
                        commentsList.add(comment)
                    }
                }
            }
            runOnUiThread() {
                val mLayoutManager = LinearLayoutManager(applicationContext)
                recyclerViewComments.layoutManager = mLayoutManager
                recyclerViewComments.itemAnimator = DefaultItemAnimator()
                recyclerViewComments.setHasFixedSize(true)
                recyclerViewComments.adapter = CommentsRecyclerAdapter(commentsList)
            }
        }
        threadGetComments.start()

        val menuButton: Button = findViewById(R.id.returnMainButton)
        menuButton.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        val returnButton: Button = findViewById(R.id.returnButton)
        returnButton.setOnClickListener() {
            this.finish()
        }

    }
}