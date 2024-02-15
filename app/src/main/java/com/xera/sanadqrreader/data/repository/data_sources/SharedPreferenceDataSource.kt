package com.xera.sanadqrreader.data.repository.data_sources

interface SharedPreferenceDataSource {

    fun saveUserToken(token: String)
    fun getUserToken(): String?
    fun clearUserToken()

}