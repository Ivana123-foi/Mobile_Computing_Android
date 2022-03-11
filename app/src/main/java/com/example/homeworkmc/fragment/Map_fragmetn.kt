package com.example.homeworkmc.fragment


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color.argb
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.homeworkmc.Graph
import com.example.homeworkmc.R
import com.example.homeworkmc.entity.Reminder
import com.example.homeworkmc.repository.ReminderRepository
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class Map_fragmetn(private val reminderRepository: ReminderRepository = Graph.reminderRepository,  private val id_user: Long) : Fragment() {

    private lateinit var remderLista : List<Reminder>

    private  lateinit var v: View
    private lateinit var mMap: GoogleMap
    private  lateinit var supportMan: SupportMapFragment
    private var boolean = false
    var mark : Marker?=null
    var mMarker: Marker? = null
    var mCircle: Circle? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v =  inflater.inflate(R.layout.map_fragment, container, false)

        GlobalScope.launch {
            remderLista=reminderRepository.getReminder(id_user)
        }



        supportMan=childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        supportMan.getMapAsync { googleMap ->
            mMap = googleMap
            boolean = true

            val location = LatLng(65.0121, 25.4651)

            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(location, 10f)
            )

            val marker = MarkerOptions().title("Oulu").position(location)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
            mMap.addMarker(marker)


            //updateMap(mMap)

            mMap.setOnMapClickListener { latlong ->
                mMap.clear()
                val snip = String.format(
                    Locale.getDefault(),
                    "Lat:%1$.2f, Lng:%2$.2f",
                    latlong.latitude,
                    latlong.longitude
                )
                for (s in remderLista)
                {
                    if(s.location_x!=null && s.location_y!=null)
                    {
                        if(radius(latlong.latitude, s.location_x, latlong.longitude, s.location_y)<=2) {

                            mMap.addMarker(
                                MarkerOptions().position(LatLng(s.location_x, s.location_y))
                                    .title(s.title)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))

                            )
                        }

                    }

                    //CUSTOM MARKER



                    mMarker?.remove()
                    mCircle?.remove()

                    mMarker = mMap.addMarker(
                        MarkerOptions().position(latlong).title("Your DEMO location").snippet(snip).icon(
                            bitmapFromVector(Graph.contextApp, R.drawable.ic_task)
                        )
                    )

                    mCircle = mMap.addCircle(
                        CircleOptions()
                            .center(latlong)
                            .radius(2000.0)
                            .strokeColor(argb(255, 128, 0, 128))
                            .fillColor(argb(50, 128, 0, 128))
                            .strokeWidth(5.0F)
                    )


                    //

                }

            }

        }

        return v
    }


    fun radius(lat1: Double, lat2: Double, lon1: Double, lon2: Double): Double {

        var lat1 = lat1
        var lat2 = lat2
        var lon1 = lon1
        var lon2 = lon2
        lon1 = Math.toRadians(lon1)
        lon2 = Math.toRadians(lon2)
        lat1 = Math.toRadians(lat1)
        lat2 = Math.toRadians(lat2)

        val dlon = lon2 - lon1
        val dlat = lat2 - lat1
        val a = (Math.pow(Math.sin(dlat / 2), 2.0)
                + (Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2.0)))
        val c = 2 * Math.asin(Math.sqrt(a))

        // Radius of earth in kilometers. Use 3956
        // for miles
        val r = 6371.0
        // calculate the result
        return c * r
    }

    private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)


        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

