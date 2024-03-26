package com.newsportal.info_6134capstoneproject.ui.fragments.bookmarkfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.adapters.BookmarkAdapter
import com.newsportal.info_6134capstoneproject.adapters.TabContentFragmentAdapter
import com.newsportal.info_6134capstoneproject.db.BookmarkViewModel
import com.newsportal.info_6134capstoneproject.ui.fragments.tabcontentfragment.ViewModelFactory

class BookmarkFragment : Fragment() {

    private lateinit var bookmarkViewModel: BookmarkViewModel

    private lateinit var adapter: BookmarkAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        recyclerView = view?.findViewById(R.id.rvContentFragment) as RecyclerView
        bookmarkViewModel = ViewModelProvider(this).get(BookmarkViewModel::class.java)
        adapter = BookmarkAdapter(bookmarkViewModel)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        bookmarkViewModel.readAllData.observe(viewLifecycleOwner, Observer{ dbArticle ->
            adapter.update(dbArticle)
        })
    }

}