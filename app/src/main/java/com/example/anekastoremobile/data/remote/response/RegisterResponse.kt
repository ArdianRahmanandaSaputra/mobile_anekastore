package com.example.anekastoremobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Pivot(

	@field:SerializedName("role_id")
	val roleId: String? = null,

	@field:SerializedName("model_type")
	val modelType: String? = null,

	@field:SerializedName("model_id")
	val modelId: String? = null
)

data class Data(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("roles")
	val roles: List<RolesItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class RolesItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("pivot")
	val pivot: Pivot? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("guard_name")
	val guardName: String? = null
)
