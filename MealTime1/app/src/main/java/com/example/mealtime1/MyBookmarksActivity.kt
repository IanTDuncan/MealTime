package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity

class MyBookmarksActivity: ComponentActivity() {
    private lateinit var buttonBackToMainMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bookmars_activity)

        val bookmarks = listOf(
            Bookmark("Google", "https://www.google.com"),
            Bookmark("GitHub", "https://www.github.com"),
            // Add more bookmarks as needed
        )

        val bookmarkListView: ListView = findViewById(R.id.bookmarkListView)
        val adapter = BookmarkAdapter(this, android.R.layout.simple_list_item_2, bookmarks)
        bookmarkListView.adapter = adapter
    }
}


