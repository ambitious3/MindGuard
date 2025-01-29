package com.example.mindguard


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class SupportCommunityActivity : AppCompatActivity() {

    private lateinit var postEditText: EditText
    private lateinit var postButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var communityAdapter: CommunityAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_community)


        db = FirebaseFirestore.getInstance()

        postEditText = findViewById(R.id.postEditText)
        postButton = findViewById(R.id.postButton)
        recyclerView = findViewById(R.id.communityRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        communityAdapter = CommunityAdapter()
        recyclerView.adapter = communityAdapter

        postButton.setOnClickListener {
            val postContent = postEditText.text.toString()
            if (postContent.isNotEmpty()) {
                addPostToCommunity(postContent)
            } else {
                Toast.makeText(this, "Wpisz coś, zanim opublikujesz!", Toast.LENGTH_SHORT).show()
            }
        }

        loadCommunityPosts()
    }

    private fun addPostToCommunity(postContent: String) {
        val post = hashMapOf(
            "content" to postContent,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("community_posts")
            .add(post)
            .addOnSuccessListener {
                Toast.makeText(this, "Post opublikowany!", Toast.LENGTH_SHORT).show()
                postEditText.text.clear()
                loadCommunityPosts()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Błąd publikacji postu: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadCommunityPosts() {
        db.collection("community_posts")
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { documents ->
                val posts = mutableListOf<String>()
                for (document in documents) {
                    val content = document.getString("content")
                    if (content != null) {
                        posts.add(content)
                    }
                }
                communityAdapter.setPosts(posts)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Błąd ładowania postów: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
