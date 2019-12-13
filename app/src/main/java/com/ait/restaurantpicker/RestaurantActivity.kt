package com.ait.restaurantpicker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.ait.restaurantpicker.adapter.Adapter
import com.ait.restaurantpicker.api.RestaurantAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


class RestaurantActivity : AppCompatActivity() {

    var adapter: Adapter? = null
    var restaurants = ArrayList<Restaurants>(3)
    lateinit var res: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        supportActionBar?.hide()
        val viewPager: ViewPager = findViewById(R.id.viewPager)

        val category = intent.getStringExtra("CATEGORY")
        var sortBy = intent.getStringExtra("SORT")
        val longitude = intent.getStringExtra("LONG")
        val latitude = intent.getStringExtra("LAT")


        if (sortBy == "Cost") {
            sortBy = "cost"
        } else if (sortBy == "Distance") {
            sortBy = "real_distance"
        } else {
            sortBy = "rating"
        }



        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.base_URL))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val restaurantAPI = retrofit.create(RestaurantAPI::class.java)
        val call = restaurantAPI.getRestaurants(
            276,
            "city",
            category!!,
            latitude!!,
            longitude!!,
            sortBy,
            3

        )

        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                //tvShowCat.text = t.message
            }

            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful) {

                    val rawResult = response.body()
                    val rawJson = JSONObject(rawResult).getJSONArray("restaurants")

                    var restaurantName = ""
                    for (i in 0..2) {
                        restaurantName +=
                            rawJson.getJSONObject(i).getJSONObject("restaurant").getString("name") + "\n"
                    }

                    var restaurantLat = ""
                    for (i in 0..2) {
                        restaurantLat +=
                            rawJson.getJSONObject(i).getJSONObject("restaurant").getJSONObject("location").getString("latitude") + "\n"
                    }




                    restaurants.add(
                        Restaurants(
                            rawJson.getJSONObject(0).getJSONObject("restaurant").getString("thumb"),
                            rawJson.getJSONObject(0).getJSONObject("restaurant").getString("name"),
                            rawJson.getJSONObject(0).getJSONObject("restaurant").getString("cuisines") + '\n' + rawJson.getJSONObject(
                                0
                            ).getJSONObject("restaurant").getJSONObject("user_rating").getString("aggregate_rating"),
                            rawJson.getJSONObject(0).getJSONObject("restaurant").getJSONObject("location").getString("latitude"),
                            rawJson.getJSONObject(0).getJSONObject("restaurant").getJSONObject("location").getString("longitude")
                        )
                    )
                    restaurants?.add(
                        Restaurants(
                            rawJson.getJSONObject(1).getJSONObject("restaurant").getString("thumb"),
                            rawJson.getJSONObject(1).getJSONObject("restaurant").getString("name"),
                            rawJson.getJSONObject(1).getJSONObject("restaurant").getString("cuisines") + '\n' + rawJson.getJSONObject(
                                1
                            ).getJSONObject("restaurant").getJSONObject("user_rating").getString("aggregate_rating"),
                                    rawJson.getJSONObject(1).getJSONObject("restaurant").getJSONObject("location").getString("latitude"),
                            rawJson.getJSONObject(1).getJSONObject("restaurant").getJSONObject("location").getString("longitude")
                        )
                    )
                    restaurants?.add(
                        Restaurants(
                            rawJson.getJSONObject(2).getJSONObject("restaurant").getString("thumb"),
                            rawJson.getJSONObject(2).getJSONObject("restaurant").getString("name"),
                            rawJson.getJSONObject(2).getJSONObject("restaurant").getString("cuisines") + '\n' + rawJson.getJSONObject(
                                2
                            ).getJSONObject("restaurant").getJSONObject("user_rating").getString("aggregate_rating"),
                            rawJson.getJSONObject(2).getJSONObject("restaurant").getJSONObject("location").getString("latitude"),
                            rawJson.getJSONObject(2).getJSONObject("restaurant").getJSONObject("location").getString("longitude")
                        )
                    )
                    adapter = Adapter(
                        restaurants,
                        this@RestaurantActivity
                    )
                    viewPager.adapter = adapter
                } else {
                    //tvShowCat.text = "Fail"
                }

            }
        })

    }

}