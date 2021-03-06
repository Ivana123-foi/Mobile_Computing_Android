package com.example.homeworkmc

import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.homeworkmc.databinding.ActivityAddBinding
import com.example.homeworkmc.entity.Reminder
import com.example.homeworkmc.fragment.*
import com.example.homeworkmc.repository.ReminderRepository
import kotlinx.android.synthetic.main.profil.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import androidx.work.*
import java.text.SimpleDateFormat

class ActivityAdd(private val reminderRepository: ReminderRepository = Graph.reminderRepository) : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var createButton: Button
    private lateinit var deleteButton: ImageButton
    private lateinit var locationButton: Button

    //picture
    private val  PERMISSION_COD: Int = 1000;
    private val IMAGE_CAPTURE_COD : Int = 1001
    var image_rui : Uri? = null

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var currentImagePath: String? = null

    private lateinit var btnOpenCamera: Button
    private lateinit var ivPhoto: ImageView
    //

    //notification
    private lateinit var binding: ActivityAddBinding
    ///

    //todaysDate
    //var mTime = Date()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //dateadd

        val datePicker = findViewById<DatePicker>(R.id.datePicker1)
        val today = Calendar.getInstance()
        //var msg : String = SimpleDateFormat("yyyy-M-d").format(mTime)

        var msg : String = ""

        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->  val month = month + 1
            msg = "$year-$month-$day"
        }






        ///

        //For Update
        val reminderID : Long = intent.getLongExtra("ReminderID", -1)
        val titleUpdate : String? = intent.getStringExtra("Title" )
        val messageUpdate : String? = intent.getStringExtra("Message")
        val timeReminder : String? = intent.getStringExtra("time")
        val id : Long = intent.getLongExtra("id_user", -1)
        //

        //For add
        val id_user : Long = intent.getLongExtra("id", -1)

        val title = findViewById<EditText>(R.id.title_add)
        val message = findViewById<EditText>(R.id.message_add)
        //


        //FORPICTURE
        btnOpenCamera = findViewById(R.id.take_picture_button)
        ivPhoto = findViewById(R.id.take_picture_view)

        btnOpenCamera.setOnClickListener {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if(checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                {
                    //permission was not enabled
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_COD)
                }
                else{
                        openCamera()
                }
            }
            else{
                openCamera()
            }

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(cameraIntent)

        }
    //
/////LOCATION



        locationButton = findViewById(R.id.location_button)

        locationButton.setOnClickListener(View.OnClickListener {
            val inte = Intent(this@ActivityAdd, ActivityMap::class.java)
            inte.putExtra("id_u", id_user)
            inte.putExtra("TitleL", title.text.toString())
            inte.putExtra("MessageL", message.text.toString())
            inte.putExtra("reminderID", reminderID)
            inte.putExtra("user_id_change", id)
            inte.putExtra("title_update", titleUpdate)
            inte.putExtra("message_update", messageUpdate)
            startActivity(inte)
        })


////////
        //
        // Toast.makeText(this, ". ${reminderID} ", Toast.LENGTH_SHORT).show()

        if(titleUpdate==null) {


            val titleLocation : String? = intent.getStringExtra("titleL" )
            val messageLocation : String? = intent.getStringExtra("messageL")
            val latitude : Double = intent.getDoubleExtra("latitude" , -1.0)
            val longitude : Double = intent.getDoubleExtra("longitude", -1.0)

            //Toast.makeText(this, "You clicked item no. ${latitude}. ${longitude} ", Toast.LENGTH_SHORT).show()
            var lat : Double? = null
            var lon : Double?= null

            if (titleLocation != null && messageLocation!=null && latitude!= -1.0) {
                title.setText(titleLocation, TextView.BufferType.EDITABLE)
                message.setText(messageLocation, TextView.BufferType.EDITABLE)
                //fToast.makeText(this, "You clicked item no. ${latitude}. ${longitude} ", Toast.LENGTH_SHORT).show()
                lat = latitude
                lon = longitude
            }
            else
            {
                lat = null
                lon = null


            }

            //////////
            saveButton = findViewById(R.id.save_button)
            //Toast.makeText(this, "You clicked item no. ${lat}. ${lon} ", Toast.LENGTH_SHORT).show()

            saveButton.setOnClickListener(View.OnClickListener {

               // Toast.makeText(this, "You clicked item no. ${lat}. ${lon} ", Toast.LENGTH_SHORT).show()

                createNotification(context = this)


                GlobalScope.launch {

                    reminderRepository.addReminder(
                        Reminder(
                            title = title.text.toString(),
                            message = message.text.toString(),
                            user_id = id_user,
                            location_x = lat,
                            location_y = lon,
                            creation_time = msg,
                            reminder_seen = true
                        )
                    )


                }
                showNotification(title.text.toString(), msg, context = this)

                val int = Intent(this@ActivityAdd, Activityhome::class.java)
                int.putExtra("id", id_user)
                startActivity(int)
            })

        }
        else {

            val latitude : Double = intent.getDoubleExtra("latitude" , -1.0)
            val longitude : Double = intent.getDoubleExtra("longitude", -1.0)
            locationButton.isEnabled = false

            //Toast.makeText(this, "You clicked item no. ${latitude}. ${longitude} ", Toast.LENGTH_SHORT).show()
            var lat : Double? = null
            var lon : Double?= null

            if (latitude!= -1.0) {
                title.setText(titleUpdate, TextView.BufferType.EDITABLE)
                message.setText(messageUpdate, TextView.BufferType.EDITABLE)
               // Toast.makeText(this, "You clicked item no. ${latitude}. ${longitude} ", Toast.LENGTH_SHORT).show()
                lat = latitude
                lon = longitude
            }
            else
            {
                lat = null
                lon = null
            }

        createButton = findViewById(R.id.create_button)
        title.setText(titleUpdate, TextView.BufferType.EDITABLE)
        message.setText(messageUpdate, TextView.BufferType.EDITABLE)


        //date update
            var msgUpdate : String? = timeReminder
            datePicker.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)

            )
            { view, year, month, day ->
                val month = month + 1
                msgUpdate = "$year-$month-$day"
            }
        //////

            if(latitude != -1.0){
                msgUpdate == msg
            }

        createButton.setOnClickListener(View.OnClickListener {


                GlobalScope.launch {
                    reminderRepository.updateReminder(
                        Reminder(
                            reminderID = reminderID,
                            title = title.text.toString(),
                            message = message.text.toString(),
                            user_id = id,
                            location_x = lat,
                            location_y = lon,
                            creation_time = msg,
                            reminder_seen = true

                        )
                    )

                }

            val intent = Intent(this@ActivityAdd, Activityhome::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        })


            //DELETE BUTTON
            deleteButton = findViewById(R.id.button_delete)
            deleteButton.setVisibility(View.VISIBLE)

            deleteButton.setOnClickListener(View.OnClickListener {

                GlobalScope.launch {
                    reminderRepository.deletebytitler(title.text.toString())

                }

                val inten = Intent(this@ActivityAdd, Activityhome::class.java)
                inten.putExtra("id", id)
                startActivity(inten)
            })
            ///delete*/
        }


    }


    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "New picture")
        image_rui = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_rui)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_COD )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode)
        {
            PERMISSION_COD -> if(grantResults.size > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                //permission was granted
                openCamera()
            }
            else{
                //permission was denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode==Activity.RESULT_OK)
        {
            ivPhoto.setImageURI(image_rui)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }




    private fun createNotification (context: Context)
    {
        val channelIDD= "channel2"
        val channel = NotificationChannel(
            channelIDD,
            "Test Notification",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "This is addreminder"
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
    private fun showNotification(title: String, time: String, context: Context) {

        val channelIDD= "channel2"
        val id = 125
        val packageName : String = BuildConfig.APPLICATION_ID

         var alarmSound: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
         var mp: MediaPlayer = MediaPlayer.create(context, alarmSound)
         mp.start()


        val contentView = RemoteViews(packageName,R.layout.notificationlayout)
        contentView.setTextViewText(R.id.notification_title, "Added reminder $title")
        contentView.setTextViewText(R.id.message, time )

        val build = NotificationCompat.Builder(context, channelIDD)
            .setSmallIcon(R.drawable.ic_notifications)
            .setCustomContentView(contentView)

        build.setContent(contentView)
        build.setSound(alarmSound)
        var notification=build.build()
        with(NotificationManagerCompat.from(context)) {
            notify(id, notification)
        }

    }
}