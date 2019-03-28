package com.example.imageonline.Adapters

import com.example.imageonline.POJO.Example
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("customsearch/v1")
    fun getExamples(@Query("key") key : String,
                    @Query("cx") cx : String,
                    @Query("searchType") searchType : String,
                    @Query("q") q : String) : Call<Example>
}