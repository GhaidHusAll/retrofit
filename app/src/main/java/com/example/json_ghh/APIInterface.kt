package com.example.json_ghh

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("eur.json")
    fun getCurrencies(): Call<currencies>
}