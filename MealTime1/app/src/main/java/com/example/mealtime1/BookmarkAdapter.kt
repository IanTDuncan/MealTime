package com.example.mealtime1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class BookmarkAdapter(context: Context, resource: Int, bookmarks: List<Bookmark>) :
    ArrayAdapter<Bookmark>(context, resource, bookmarks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)

        val titleTextView: TextView = view.findViewById(android.R.id.text1)
        val urlTextView: TextView = view.findViewById(android.R.id.text2)

        val bookmark: Bookmark = getItem(position)!!

        titleTextView.text = bookmark.title
        urlTextView.text = bookmark.url

        return view
    }
}