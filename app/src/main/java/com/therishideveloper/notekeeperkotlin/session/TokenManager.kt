package com.therishideveloper.notekeeperkotlin.session

import android.content.Context
import com.therishideveloper.notekeeperkotlin.utils.Constants.TOKEN_MANAGER
import com.therishideveloper.notekeeperkotlin.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Shuva Ranjan Rishi on 17,March,2023
 * BABL, Bangladesh,
 */

class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private var prefs = context.getSharedPreferences(TOKEN_MANAGER, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}