package com.wopata.register_core.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.wopata.register_core.BuildConfig
import com.wopata.register_core.models.User

/**
 * Created by stephenvinouze on 14/06/2017.
 */
object UserPreferences {

    private const val PREFERENCE_KEY = BuildConfig.APPLICATION_ID + ".user_preference"
    private const val USER_KEY = "user"

    private val gson: Gson by lazy { Gson() }

    fun read(context: Context): User {
        return gson.fromJson(getPreferences(context).getString(USER_KEY, null), User::class.java)
    }

    fun write(context: Context, user: User) {
        getPreferences(context).edit().putString(USER_KEY, gson.toJson(user)).apply()
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_KEY, 0)
    }

}