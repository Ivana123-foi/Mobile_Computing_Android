package com.example.homeworkmc.fragment

import android.R.attr.*
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkmc.entity.Reminder
import com.example.homeworkmc.repository.ReminderRepository
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_m.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.homeworkmc.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.location.LocationListener
import android.location.LocationRequest
import kotlinx.android.synthetic.main.activity_add.view.*

class Main_Fragment(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
    private val id_user: Long
) : Fragment(), RecycleAdapter.OnItemClickListener, LocationListener {


    ///location
    private lateinit var locationManager: LocationManager
    private var latent : Double? =null
    private var longe : Double?=null
    private val locationPermissionCode = 2
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    private var locati: Location? = null

    ///
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapt: RecyclerView.Adapter<RecycleAdapter.ViewHolder>? = null
    private lateinit var v: View
    private lateinit var buttonMain: ImageButton
    private lateinit var ReminderList: List<Reminder>
    private lateinit var ReminderListFobutton: List<Reminder>
    private lateinit var buttonShowAll: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_m, container, false)



        return v

    }

    //todays date
    var mTime = Date()
    var text: String = SimpleDateFormat("yyyy-M-d").format(mTime)
    //
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        recycleview.apply {


            createNotification (context)
            getLocation (context)

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

            locati = getLastLocation(context)



            Toast.makeText(
                activity,
                "${locati!!.latitude}, ${locati!!.longitude}",
                Toast.LENGTH_SHORT
            ).show()



            GlobalScope.launch {
                buttonShowAll = v.findViewById(R.id.showAll)


                ReminderListFobutton = reminderRepository.getReminderByTime(text, id_user)
                ReminderList = reminderRepository.getReminder(id_user)

                if (ReminderListFobutton != null) {

                    layoutManager = LinearLayoutManager(activity)
                    recycleview.layoutManager = layoutManager

                    adapt =
                        RecycleAdapter(
                            reminderRepository.getReminderByTime(text, id_user),
                            this@Main_Fragment
                        )
                    recycleview.adapter = adapt
                }

                buttonShowAll.setOnClickListener(View.OnClickListener {
                    layoutManager = LinearLayoutManager(activity)
                    recycleview.layoutManager = layoutManager

                    adapt =
                        RecycleAdapter(
                            ReminderList,
                            this@Main_Fragment
                        )
                    recycleview.adapter = adapt
                })

                //notification
                for (reminder in ReminderListFobutton) {
                    if (reminder.creation_time == text) {
                        showNotification(reminder, context)
                    }

                }

                for(remid in ReminderList)
                {
                    if(remid.location_x!=null &&  remid.location_y!=null )
                    {
                            if(radius(locati!!.latitude, remid.location_x, locati!!.longitude, remid.location_y)<=2){

                            showNotification(remid, context)
                            }


                    }
                }
                ////////

            }

        }


        buttonMain = v.findViewById(R.id.button_Screen)

        buttonMain.setOnClickListener(View.OnClickListener {

            val intent = Intent(activity, ActivityAdd::class.java)
            intent.putExtra("id", id_user)
            startActivity(intent)

        })

    }


    //LOCATION


    //////
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


    /////


    private fun getLocation(context: Context) {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            activity?.let { ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode) }
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5f, this)
    }

    private fun getLastLocation(context: Context): Location? {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {}
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
    }
    override fun onLocationChanged(location: Location) {

        //Toast.makeText(activity, "${location.latitude} .... ${location.longitude}", Toast.LENGTH_SHORT).show()
        /*latent= location.latitude
        longe= location.longitude*/
        //locati = location

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show()

            }
        }
    }

//NOTIFICATIONS
    private fun createNotification (context: Context)
    {
        val channelIDD= "channel1"
        val channel = NotificationChannel(
            channelIDD,
            "Test Notification",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "This is test channel"
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
    private fun showNotification(reminder: Reminder, context: Context) {

        val channelIDD= "channel1"
        val packageName : String = BuildConfig.APPLICATION_ID

       /* var alarmSound: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        var mp: MediaPlayer = MediaPlayer.create(context, alarmSound)
        mp.start()*/

        val contentView = RemoteViews(packageName,R.layout.notificationlayout)
        contentView.setTextViewText(R.id.notification_title, reminder.title)
        contentView.setTextViewText(R.id.message, reminder.creation_time)


        val id = reminder.reminderID
        val build = NotificationCompat.Builder(context, channelIDD)
            .setSmallIcon(R.drawable.ic_notifications)
            .setCustomContentView(contentView)


        build.setContent(contentView)
        //build.setSound(alarmSound)

        var notification=build.build()
        with(NotificationManagerCompat.from(context)) {
            notify(id.toInt(), notification)
        }

    }
/////

    override fun onItemClick(title: String) {

        for (reminder in ReminderList) {

            if (title == reminder.title) {

                //Toast.makeText(activity, "You clicked item no. ${number}.  ${posicija}.  ", Toast.LENGTH_SHORT).show()

                val intent = Intent(activity, ActivityAdd::class.java)
                intent.putExtra("ReminderID", reminder.reminderID)
                intent.putExtra("Title", reminder.title)
                intent.putExtra("Message", reminder.message)
                intent.putExtra("id_user", reminder.user_id)
                intent.putExtra("time", reminder.creation_time)
                startActivity(intent)
            }
        }

    }

}