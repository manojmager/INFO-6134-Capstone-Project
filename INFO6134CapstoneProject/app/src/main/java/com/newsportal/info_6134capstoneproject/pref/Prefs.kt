package com.newsportal.info_6134capstoneproject.pref

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class Prefs private constructor(private val appContext: Context) {
    private val prefs: SharedPreferences = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @SuppressLint("ApplySharedPref")
    fun clearAllData() {
        prefs.edit().clear().commit()
    }

    companion object {
        private const val PREFS_NAME = "global_preferences"
        private var sUniqueInstance: Prefs? = null

        @JvmStatic
        fun get(): Prefs {
            requireNotNull(sUniqueInstance) { "Prefs is not initialized, call initialize method first." }
            return sUniqueInstance!!
        }

        @JvmStatic
        fun initialize(appContext: Context) {
            requireNotNull(appContext) { "Provided application context is null" }
            if (sUniqueInstance == null) {
                sUniqueInstance = Prefs(appContext)
            }
        }
    }
}