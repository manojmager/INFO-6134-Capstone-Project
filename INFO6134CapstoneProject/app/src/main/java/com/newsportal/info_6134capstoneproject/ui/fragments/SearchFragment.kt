package com.newsportal.info_6134capstoneproject.ui.fragments

import TabContentViewModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.adapters.SearchHistoryAdapter
import com.newsportal.info_6134capstoneproject.di.Injection
import com.newsportal.info_6134capstoneproject.ui.activities.SearchNewsActivity


class SearchFragment : Fragment() {

    private val viewModel by viewModels<TabContentViewModel> {
        Injection.provideViewModelFactory()
    }

    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    private lateinit var rvSearchHistory: RecyclerView

    private lateinit var btnClear: Button

    private lateinit var searchHistoryList: MutableList<String>

    private lateinit var etSearch: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etSearch = view.findViewById(R.id.etSearch)
        rvSearchHistory = view.findViewById(R.id.rvSearchHistory)
        btnClear = view.findViewById(R.id.btnClear)


        rvSearchHistory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        searchHistoryList = loadSearchHistory().toMutableList()

        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = etSearch.text.toString().trim()
                if (query.isNotEmpty()) {

                    etSearch.text = null

                    performSearch(query)

                    //add values to searchHistoryList
                    searchHistoryList.add(query)
                    searchHistoryAdapter.notifyDataSetChanged()

                    // Save the updated search history
                    saveSearchHistory(searchHistoryList)

                } else {
                    Toast.makeText(requireContext(), "Please enter a search query", Toast.LENGTH_SHORT).show()
                }
                true
            } else {
                false
            }
        }

        btnClear.setOnClickListener {
            // Clear data from SharedPreferences
            val sharedPreferences =
                requireContext().getSharedPreferences("search_history", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            searchHistoryList.clear()
            searchHistoryAdapter.notifyDataSetChanged()
        }

        setupHistoryAdapter()

    }

    private fun setupHistoryAdapter() {

        searchHistoryAdapter = SearchHistoryAdapter(searchHistoryList)

        rvSearchHistory.adapter = searchHistoryAdapter
    }

    private fun saveSearchHistory(searchHistoryList: List<String>) {
        val sharedPreferences =
            requireContext().getSharedPreferences("search_history", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("history", HashSet(searchHistoryList))
        editor.apply()
    }


    private fun performSearch(query: String) {
        val intent = Intent(requireContext(), SearchNewsActivity::class.java)
        intent.putExtra("QUERY", query)
        startActivity(intent)
    }

    private fun loadSearchHistory(): List<String> {
        val sharedPreferences =
            requireContext().getSharedPreferences("search_history", Context.MODE_PRIVATE)
        val historySet = sharedPreferences.getStringSet("history", HashSet()) ?: HashSet()
        return ArrayList(historySet)
    }

}
