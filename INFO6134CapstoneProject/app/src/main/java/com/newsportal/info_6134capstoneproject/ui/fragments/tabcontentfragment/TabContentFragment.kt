package com.newsportal.info_6134capstoneproject.ui.fragments.tabcontentfragment

import TabContentViewModel
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.adapters.TabContentFragmentAdapter
import com.newsportal.info_6134capstoneproject.di.Injection
import com.newsportal.info_6134capstoneproject.model.Article
import com.newsportal.info_6134capstoneproject.model.Source

class TabContentFragment : Fragment() {

    private val viewModel by viewModels<TabContentViewModel> {
        Injection.provideViewModelFactory()
    }

    private lateinit var adapter: TabContentFragmentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutError: View
    private lateinit var textViewError: TextView
    private lateinit var layoutEmpty: View
    private lateinit var progressBar: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupUI()
    }

    private fun setupUI() {
        recyclerView = view?.findViewById(R.id.rvContentFragment) as RecyclerView
        layoutError = view?.findViewById(R.id.layoutError) as View
        layoutEmpty = view?.findViewById(R.id.layoutEmpty) as View
        progressBar = view?.findViewById(R.id.progressBar) as View
        textViewError = view?.findViewById(R.id.textViewError) as TextView

        adapter = TabContentFragmentAdapter(viewModel.articles.value ?: emptyList())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel.articles.observe(viewLifecycleOwner, renderArticles)
        viewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
    }

    private val renderArticles = Observer<List<Article>> { articles ->
        Log.d(TAG, "Sources updated: $articles")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        adapter.update(articles)
    }


    private val isViewLoadingObserver = Observer<Boolean> { isLoading ->
        Log.v(TAG, "isViewLoading $isLoading")
        val visibility = if (isLoading) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<String> { error ->
        error?.let {
            Log.v(TAG, "onMessageError $error")
            layoutError.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE
            textViewError.text = "Error $it"
        }
    }

    private val emptyListObserver = Observer<Boolean> { isEmpty ->
        Log.v(TAG, "emptyListObserver $isEmpty")
        layoutEmpty.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

    companion object {
        fun newInstance(title: String): TabContentFragment {
            val fragment = TabContentFragment()
            val args = Bundle().apply {
                putString("title", title)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadArticles()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injection.destroy()
    }
}