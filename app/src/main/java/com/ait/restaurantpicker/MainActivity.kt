package com.ait.restaurantpicker

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    MyLocationProvider.OnNewLocationAvailable {



    private var prevLocation: Location? = null
    lateinit var latitude: String
    lateinit var longitude: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "Home"


        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.categories_array,
            R.layout.my_spinner
        )

        val spinner = spinnerSort
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = this

        btnSeeResults.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, RestaurantActivity::class.java)
            intent.putExtra("CATEGORY", etChooseCat.text.toString())
            intent.putExtra("SORT", ret)
            intent.putExtra("LAT", latitude)
            intent.putExtra("LONG", longitude)

            startActivity(intent)

        }

        requestNeededPermission()

    }


    lateinit var ret: String
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item = p0?.getItemAtPosition(p2)
        ret = item.toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }


    private fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Toast.makeText(
                    this,
                    "I need it for location", Toast.LENGTH_SHORT
                ).show()
            }

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        } else {
            startLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "ACCESS_FINE_LOCATION perm granted", Toast.LENGTH_SHORT)
                        .show()

                    startLocation()
                } else {
                    Toast.makeText(
                        this,
                        "ACCESS_FINE_LOCATION perm NOT granted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private lateinit var myLocationProvider: MyLocationProvider

    fun startLocation() {
        myLocationProvider = MyLocationProvider(
            this,
            this
        )
        myLocationProvider.startLocationMonitoring()

    }

    override fun onStop() {
        super.onStop()
        myLocationProvider.stopLocationMonitoring()
    }

    override fun onNewLocation(location: Location) {
        prevLocation = location
        latitude = location.latitude.toString()
        longitude = location.longitude.toString()

    }
}