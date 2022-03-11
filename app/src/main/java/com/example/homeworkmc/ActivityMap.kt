package com.example.homeworkmc

import android.app.Activity
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import com.google.android.gms.location.LocationServices

import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat
import java.util.jar.Manifest
import com.google.android.gms.common.api.GoogleApiClient





class ActivityMap : AppCompatActivity() {

    private lateinit var mMap: GoogleMap
    private  lateinit var supportMan: SupportMapFragment
    private var boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val id_for : Long = intent.getLongExtra("id_u", -1)
        val titl : String? = intent.getStringExtra("TitleL" )
        val message : String? = intent.getStringExtra("MessageL")
        val reminderID : Long = intent.getLongExtra("ReminderID", -1)
        val id : Long = intent.getLongExtra("id_user", -1)
        val titleUpdate : String? = intent.getStringExtra("title_update" )
        val messageUpdate : String? = intent.getStringExtra("message_update")
        //Toast.makeText(this, "You clicked item no. ${id_for}  ", Toast.LENGTH_SHORT).show()

        supportMan =  supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment




        supportMan.getMapAsync { googleMap ->
            mMap = googleMap
            boolean = true

            val location = LatLng(65.0121, 25.4651)

            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(location, 10f)
            )

            val marker = MarkerOptions().title("Oulu").position(location)
            mMap.addMarker(marker)


            updateMap(mMap, id_for, titl, message, id, reminderID, titleUpdate, messageUpdate)
        }

    }


    private fun updateMap(map: GoogleMap, id: Long, title: String?, Message: String?, id_dva:Long, reminderID:Long, titleUpdate: String?, messageUpdate: String?) {


        map.setOnMapClickListener { latlong ->
            val snip = String.format(
                Locale.getDefault(),
                "Lat:%1$.2f, Lng:%2$.2f",
                latlong.latitude,
                latlong.longitude
            )

            map.addMarker(
                MarkerOptions().position(latlong).title("Reminder").snippet(snip)
            ).apply {
                finish()

                val intent = Intent(this@ActivityMap, ActivityAdd::class.java)
                intent.putExtra("latitude", latlong.latitude)
                intent.putExtra("longitude", latlong.longitude)
                intent.putExtra("id", id)
                intent.putExtra("titleL", title)
                intent.putExtra("messageL", Message)
                intent.putExtra("ReminderID", reminderID)
                intent.putExtra("id_user", id_dva)
                intent.putExtra("Title", titleUpdate)
                intent.putExtra("Message", messageUpdate)
                startActivity(intent)
            }
        }

    }
}