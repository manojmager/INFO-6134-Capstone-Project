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
import androidx.lifecycle.ViewModelProvider
import com.newsportal.info_6134capstoneproject.R

class TabContentFragment : Fragment() {
    private var title: String? = null
    private lateinit var textView: TextView

    private lateinit var viewModel: TabContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVars(view)

        viewModel = ViewModelProvider(this).get(TabContentViewModel::class.java)

        // Call the method to get news sources
        viewModel.fetchSources("business", "en", "US", "z_LylxinAVC3Q1-Qu43kgeCKlT-jWaf9uKPKLKwy91o")
        // Call the method to get latest headlines
        viewModel.fetchLatestHeadlines("1", "relevancy", "z_LylxinAVC3Q1-Qu43kgeCKlT-jWaf9uKPKLKwy91o")

        // Observe the LiveData for the response
        viewModel.sources.observe(viewLifecycleOwner) { sourceResponse ->
            // Handle the response here
            if (sourceResponse != null) {
                // Process sourceResponse.sources list
                Log.d(TAG, "Sources: ${sourceResponse.sources}")
            }
        }

    }

    private fun initVars(view: View) {
        // Access the title from arguments
        title = arguments?.getString("title")

        // Customize the fragment content based on the title or other data
        // For example, set text to a TextView
//        val textView = view.findViewById<TextView>(R.id.textView)
//        textView.text = title

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
}