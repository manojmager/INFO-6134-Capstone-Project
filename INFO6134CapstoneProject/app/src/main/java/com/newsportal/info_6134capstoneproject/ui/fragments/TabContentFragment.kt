package com.newsportal.info_6134capstoneproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.newsportal.info_6134capstoneproject.R

class TabContentFragment : Fragment() {
    private var title: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access the title from arguments
        title = arguments?.getString("title")

        // Customize the fragment content based on the title or other data
        // For example, set text to a TextView
        val textView = view.findViewById<TextView>(R.id.textView)
        textView.text = title
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