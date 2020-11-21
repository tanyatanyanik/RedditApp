package io.redditapp.data

import android.content.Context
import android.content.SharedPreferences

class Preferences(val context: Context) {

    private val sp: SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)


}