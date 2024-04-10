package com.newsportal.info_6134capstoneproject.ui.fragments.bookmarkfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsportal.info_6134capstoneproject.adapters.BookmarkAdapter
import com.newsportal.info_6134capstoneproject.databinding.FragmentBookmarkBinding
import com.newsportal.info_6134capstoneproject.db.BookmarkViewModel

class BookmarkFragment : Fragment() {

    private lateinit var bookmarkViewModel: BookmarkViewModel
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { BookmarkAdapter(bookmarkViewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        val context = context ?: return
        val recyclerView = binding.rvContentFragment
        bookmarkViewModel = ViewModelProvider(this).get(BookmarkViewModel::class.java)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        bookmarkViewModel.readAllData.observe(viewLifecycleOwner, Observer { dbArticle ->
            adapter.update(dbArticle)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}