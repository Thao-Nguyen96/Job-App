package com.nxt.jobapp.api

import com.nxt.jobapp.model.RemoteJob
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteJobAPI {

    @GET("remote-jobs")
    fun getRemoteJob():Call<RemoteJob>

    @GET("remote-jobs")
    fun searchJob(@Query("search")query: String?): Call<RemoteJob>
}