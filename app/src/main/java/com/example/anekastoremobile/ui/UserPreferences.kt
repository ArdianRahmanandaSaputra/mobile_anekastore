package com.example.anekastoremobile.ui

import android.content.Context

class UserPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveCredentials(email: String, password: String, token: String){
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putString("token", token)
        editor.apply()
    }

    fun getEmail(): String?{
        return sharedPreferences.getString("email", null)
    }

    fun getPass(): String?{
        return sharedPreferences.getString("password", null)
    }

    fun getToken(): String?{
        return sharedPreferences.getString("token", null)
    }

    fun clearCredential(){
        editor.remove("email")
        editor.remove("password")
        editor.remove("token")
        editor.apply()
    }
}