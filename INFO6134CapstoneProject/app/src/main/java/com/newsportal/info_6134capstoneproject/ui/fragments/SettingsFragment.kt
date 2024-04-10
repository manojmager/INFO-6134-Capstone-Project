package com.newsportal.info_6134capstoneproject.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.ui.activities.CategoryActivity

class SettingsFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val activity = activity as AppCompatActivity?
        activity?.supportActionBar?.hide()

        val notificationSettingsTextView = view.findViewById<TextView>(R.id.notificationSettings)
        val preferredCategoryTextView = view.findViewById<TextView>(R.id.preferredCategory)

        val lightMode = view.findViewById<RadioButton>(R.id.lightMode)
        val darkMode = view.findViewById<RadioButton>(R.id.darkMode)

        val lightModeLayout = view.findViewById<LinearLayout>(R.id.lightModeLayout)
        val darkModeLayout = view.findViewById<LinearLayout>(R.id.darkModeLayout)

        sharedPreferences = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)

        val isDarkMode = sharedPreferences.getBoolean("is_dark_mode", false)
        setDarkMode(isDarkMode)
        lightMode.isChecked = !isDarkMode
        darkMode.isChecked = isDarkMode

        lightModeLayout.setOnClickListener {
            lightMode.isChecked = true
            darkMode.isChecked = false
            updateDarkModeSetting(false)
        }

        darkModeLayout.setOnClickListener {
            darkMode.isChecked = true
            lightMode.isChecked = false
            updateDarkModeSetting(true)
        }

        notificationSettingsTextView.setOnClickListener {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
            startActivity(intent)
        }

        preferredCategoryTextView.setOnClickListener {
            // Start CategoryActivity
            val intent = Intent(requireContext(), CategoryActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val activity = activity as AppCompatActivity?
        activity?.supportActionBar?.show()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("SettingsFragment", "Configuration changed: $newConfig")

        val isDarkMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        setDarkMode(isDarkMode)
    }

    private fun setDarkMode(isDarkMode: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_dark_mode", isDarkMode)
        editor.apply()
        AppCompatDelegate.setDefaultNightMode(if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun updateDarkModeSetting(isChecked: Boolean) {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isDarkModeChanged = (isChecked && currentNightMode != Configuration.UI_MODE_NIGHT_YES) ||
                (!isChecked && currentNightMode == Configuration.UI_MODE_NIGHT_YES)
        if (isDarkModeChanged) {
            setDarkMode(isChecked)
            requireActivity().recreate()
        }
    }

}
