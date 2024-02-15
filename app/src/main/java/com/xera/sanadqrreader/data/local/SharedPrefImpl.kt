package com.xera.sanadqrreader.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.xera.sanadqrreader.data.repository.data_sources.SharedPreferenceDataSource
import javax.inject.Inject

class SharedPrefImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): SharedPreferenceDataSource {
    override fun saveUserToken(token: String) {
        sharedPreferences.edit {
            putString("token",token)
        }
    }
    override fun getUserToken(): String? {
        return sharedPreferences.getString("token",null)
    }

    override fun clearUserToken() {
        sharedPreferences.edit {
            remove("token")
        }
    }

}