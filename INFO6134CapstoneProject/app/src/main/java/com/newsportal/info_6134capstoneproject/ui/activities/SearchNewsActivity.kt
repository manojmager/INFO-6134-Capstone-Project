package com.newsportal.info_6134capstoneproject.ui.activities

import TabContentViewModel
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.adapters.TabContentFragmentAdapter
import com.newsportal.info_6134capstoneproject.db.BookmarkViewModel
import com.newsportal.info_6134capstoneproject.di.Injection
import com.newsportal.info_6134capstoneproject.model.Article

class SearchNewsActivity : AppCompatActivity() {

    private lateinit var viewModel: TabContentViewModel
    private lateinit var bookmarkViewModel: BookmarkViewModel

    private lateinit var adapter: TabContentFragmentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var layoutError: View
    private lateinit var textViewError: TextView
    private lateinit var layoutEmpty: View
    private lateinit var progressBar: View

    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_news)

        query = intent.getStringExtra("QUERY") ?: "news"

        setupToolbar()
        setupViewModel()
        setupUI()
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.tbSearchNews)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupUI() {
        recyclerView = findViewById(R.id.rvContentFragment)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        layoutError = findViewById(R.id.layoutError)
        layoutEmpty = findViewById(R.id.layoutEmpty)
        progressBar = findViewById(R.id.progressBar)
        textViewError = findViewById(R.id.textViewError)
        bookmarkViewModel = ViewModelProvider(this).get(BookmarkViewModel::class.java)

        adapter = TabContentFragmentAdapter(emptyList(), bookmarkViewModel)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadSearchedArticles(query)
        }
    }

    private fun setupViewModel() {
        viewModel = Injection.provideViewModelFactory().create(TabContentViewModel::class.java)
        viewModel.articles.observe(this, renderArticles)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    private val renderArticles = Observer<List<Article>> { articles ->
        Log.d("SearchNewsActivity", "Sources updated: $articles")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        adapter.update(articles)
        swipeRefreshLayout.isRefreshing = false
    }

    private val isViewLoadingObserver = Observer<Boolean> { isLoading ->
        Log.v("SearchNewsActivity", "isViewLoading $isLoading")
        val visibility = if (isLoading) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<String> { error ->
        error?.let {
            Log.v("SearchNewsActivity", "onMessageError $error")
            layoutError.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE
            textViewError.text = "Error $it"
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private val emptyListObserver = Observer<Boolean> { isEmpty ->
        Log.v("SearchNewsActivity", "emptyListObserver $isEmpty")
        layoutEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
        layoutError.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadSearchedArticles(query)
    }

    override fun onDestroy() {
        super.onDestroy()
        Injection.destroy()
    }
}
