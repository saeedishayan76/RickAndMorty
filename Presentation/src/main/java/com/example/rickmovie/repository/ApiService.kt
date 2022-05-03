package com.example.rickmovie.repository

import com.example.rickmovie.model.RickDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("character")
    suspend fun getAllCharacters(@Query("page")page:String):RickDto

    @GET("character")
    suspend fun searchName(@Query("page") page: Int ?=1, @Query("name")name:String):RickDto


}