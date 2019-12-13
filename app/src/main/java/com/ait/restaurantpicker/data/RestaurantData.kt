package com.ait.restaurantpicker.data

data class All_reviews(val rating: String?, val review_text: String?, val id: String?, val rating_color: String?, val review_time_friendly: String?, val rating_text: String?, val timestamp: String?, val likes: String?, val user: User?, val comments_count: String?)

data class Base(val results_found: String?, val results_start: String?, val results_shown: String?, val restaurants: List<Restaurants>?)

data class Location(val address: String?, val locality: String?, val city: String?, val latitude: String?, val longitude: String?, val zipcode: String?, val country_id: String?)

data class Photos(val id: String?, val url: String?, val thumb_url: String?, val user: User?, val res_id: String?, val caption: String?, val timestamp: String?, val friendly_time: String?, val width: String?, val height: String?, val comments_count: String?, val likes_count: String?)

data class Restaurants(val id: String?, val name: String?, val url: String?, val location: Location?, val average_cost_for_two: String?, val price_range: String?, val currency: String?, val thumb: String?, val featured_image: String?, val photos_url: String?, val menu_url: String?, val events_url: String?, val user_rating: User_rating?, val has_online_delivery: String?, val is_delivering_now: String?, val has_table_booking: String?, val deeplink: String?, val cuisines: String?, val all_reviews_count: String?, val photo_count: String?, val phone_numbers: String?, val photos: List<Photos>?, val all_reviews: List<All_reviews>?)

data class User(val name: String?, val zomato_handle: String?, val foodie_level: String?, val foodie_level_num: String?, val foodie_color: String?, val profile_url: String?, val profile_deeplink: String?, val profile_image: String?)

data class User_rating(val aggregate_rating: String?, val rating_text: String?, val rating_color: String?, val votes: String?)
