package com.pemrogramanmobile.apicompose.util

import android.content.Context
import android.content.SharedPreferences

object AppSettings {
    private const val PREFS_NAME = "app_settings_prefs"
    private const val KEY_LAST_CACHE_TIMESTAMP_MOVIES = "last_cache_timestamp_movies"
    private const val KEY_UI_THEME = "ui_theme"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getLastMoviesCacheTimestamp(context: Context): Long {
        return getPrefs(context).getLong(KEY_LAST_CACHE_TIMESTAMP_MOVIES, 0L)
    }

    fun setLastMoviesCacheTimestamp(context: Context, timestamp: Long) {
        getPrefs(context).edit().putLong(KEY_LAST_CACHE_TIMESTAMP_MOVIES, timestamp).apply()
    }

    fun getTheme(context: Context): String {
        return getPrefs(context).getString(KEY_UI_THEME, "system") ?: "system"
    }

    fun setTheme(context: Context, theme: String) {
        getPrefs(context).edit().putString(KEY_UI_THEME, theme).apply()
    }
}