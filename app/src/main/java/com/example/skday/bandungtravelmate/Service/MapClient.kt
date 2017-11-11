package com.example.skday.bandungtravelmate.Service

import com.example.skday.bandungtravelmate.POJO.DetailPlace
import com.example.skday.bandungtravelmate.POJO.ListPlace
import com.example.skday.bandungtravelmate.Utils.EndPoints
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by skday on 11/11/17.
 */
interface MapClient {
    @GET("maps/api/place/textsearch/json")
    fun requestListPlace(
            @Query("query") query: String = "tempat+wisata+di+bandung",
            @Query("key") apiKey: String = EndPoints.API_KEY
    ): Call<ListPlace>

    @GET("maps/api/place/details/json")
    fun requestDetail(
            @Query("placeid") placeId: String,
            @Query("key") apiKey: String
    ): Call<DetailPlace>
}