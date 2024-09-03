package com.shehab.memsapp.Model

import retrofit2.Response
import retrofit2.http.GET



interface ApiInterface {
    @GET("/get_memes")
suspend fun getMemsList():Response<MemsResource>


}