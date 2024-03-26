package com.newsportal.info_6134capstoneproject.ui.fragments

import TabContentViewModel
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.adapters.TabContentFragmentAdapter
import com.newsportal.info_6134capstoneproject.db.BookmarkViewModel
import com.newsportal.info_6134capstoneproject.di.Injection
import com.newsportal.info_6134capstoneproject.model.Article
class SearchFragment : Fragment() {

    private val viewModel by viewModels<TabContentViewModel> {
        Injection.provideViewModelFactory()
    }
    private val bookmarkViewModel by viewModels<BookmarkViewModel>()

    private lateinit var adapter: TabContentFragmentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutError: View
    private lateinit var textViewError: TextView
    private lateinit var layoutEmpty: View
    private lateinit var progressBar: View

    private lateinit var etSearch: EditText
    private lateinit var que: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            que = savedInstanceState.getString("que", "")
            // Restore other relevant data if needed
        }

        etSearch = view.findViewById(R.id.etSearch)
        que = "news"
        setupViewModel()
        setupUI()

        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = etSearch.text.toString().trim()
                if (query.isNotEmpty()) {
                    que = query
                    performSearch(que) // Call the performSearch function
                }
                true
            } else {
                false
            }
        }
    }

    private fun performSearch(query: String) {
        etSearch.text.clear()
        viewModel.loadSearchedArticles(query)
    }

    private fun setupUI() {
        recyclerView = (view?.findViewById(R.id.rvContentFragment) as? RecyclerView)!!
        recyclerView?.let {
            layoutError = view?.findViewById(R.id.layoutError) as View
            layoutEmpty = view?.findViewById(R.id.layoutEmpty) as View
            progressBar = view?.findViewById(R.id.progressBar) as View
            textViewError = view?.findViewById(R.id.textViewError) as TextView

            it.layoutManager = LinearLayoutManager(context)
            adapter = TabContentFragmentAdapter(emptyList(), bookmarkViewModel)
            it.adapter = adapter
        }
    }

    private fun setupViewModel() {
        viewModel.articles.observe(viewLifecycleOwner, renderArticles)
        viewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
    }

    private val renderArticles = Observer<List<Article>> { articles ->
        Log.d(ContentValues.TAG, "Sources updated: $articles")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        adapter.update(articles)
    }

    private val isViewLoadingObserver = Observer<Boolean> { isLoading ->
        Log.v(ContentValues.TAG, "isViewLoading $isLoading")
        val visibility = if (isLoading) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<String> { error ->
        error.let {
            Log.v(ContentValues.TAG, "onMessageError $error")
            layoutError.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE
            textViewError.text = "Error $it"
        }
    }

    private val emptyListObserver = Observer<Boolean> { isEmpty ->
        Log.v(ContentValues.TAG, "emptyListObserver $isEmpty")
        layoutEmpty.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadSearchedArticles(que)
    }

    override fun onDestroy() {
        super.onDestroy()
        Injection.destroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("que", que)
        // Save other relevant data if needed
    }
}