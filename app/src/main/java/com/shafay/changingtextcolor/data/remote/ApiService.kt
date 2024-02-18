package com.shafay.changingtextcolor.data.remote

import com.shafay.changingtextcolor.model.Colors
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    fun getPostById(): Call<Colors>

}