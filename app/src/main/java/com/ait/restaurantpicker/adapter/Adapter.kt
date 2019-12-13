package com.ait.restaurantpicker.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import com.ait.restaurantpicker.FButton
import com.ait.restaurantpicker.R
import com.ait.restaurantpicker.RestaurantActivity
import com.ait.restaurantpicker.Restaurants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.restaurant_item.view.*


class Adapter(private val restaurants: ArrayList<Restaurants>, private val context: Context) :
    PagerAdapter() {
    override fun getCount(): Int {
        return restaurants.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == o
    }


    private lateinit var imgView: ImageView
    private lateinit var title: TextView
    private lateinit var descr: TextView
    private lateinit var fab: View
    private lateinit var btnNavigate: FButton

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val rootView =
            LayoutInflater.from(context).inflate(R.layout.restaurant_item, container, false)


        imgView = rootView.restaurantImg
        title = rootView.restaurantDescr
        descr = rootView.tvDescr
        fab = rootView.fab
        btnNavigate = rootView.btnNavigate


        Glide.with(context as RestaurantActivity)
            .load(
                restaurants[position].img
            )
            .into(imgView)



        title.text = restaurants[position].title
        descr.text = restaurants[position].desc


        fab.setOnClickListener {
            Toast.makeText(context, "You clicked a button", Toast.LENGTH_LONG).show()
        }

        btnNavigate.setOnClickListener {
            val gmmIntentUri: Uri =
                Uri.parse("google.navigation:q=${restaurants[position].latitude},${restaurants[position].longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)
        }


        container.addView(rootView, 0)
        return rootView
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        o: Any
    ) {
        container.removeView(o as View)
    }

}