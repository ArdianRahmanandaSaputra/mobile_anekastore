package com.example.anekastoremobile.data.remote.body

import com.google.gson.annotations.SerializedName

data class LoginBody(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
