package com.ait.restaurantpicker.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RestaurantAPI {
    @Headers("user-key: 9a0b643697162e0f72d6b7e6ee9639bb")
    @GET("api/v2.1/search")
    fun getRestaurants(
        @Query("entity_id") cityID: Int,
        @Query("entity_type") areaType: String,
        @Query("q") catName: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("sort") sortBy: String,
        @Query("count") numResults: Int): Call<String>
}