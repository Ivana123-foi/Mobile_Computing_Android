package com.example.homeworkmc

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.homeworkmc.entity.Reminder
import com.example.homeworkmc.entity.User
import com.example.homeworkmc.repository.ReminderRepository
import com.example.homeworkmc.repository.UserRepository
import kotlinx.android.synthetic.main.profil.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URI
import java.util.jar.Manifest


class ActivityAdd(private val reminderRepository: ReminderRepository = Graph.reminderRepository) : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var createButton: Button
    private lateinit var deleteButton: ImageButton


//picture
    private val  PERMISSION_COD: Int = 1000;
    private val IMAGE_CAPTURE_COD : Int = 1001
    var image_rui : Uri? = null

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var currentImagePath: String? = null

    private lateinit var btnOpenCamera: Button
    private lateinit var ivPhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        //For Update
        val reminderID : Long = intent.getLongExtra("ReminderID", -1)
        val titleUpdate : String? = intent.getStringExtra("Title" )
        val messageUpdate : String? = intent.getStringExtra("Message")
        val id : Long = intent.getLongExtra("id_user", -1)
        //

        //For add
        val id_user : Long = intent.getLongExtra("id", -1)
        val title = findViewById<EditText>(R.id.title_add)
        val message = findViewById<EditText>(R.id.message_add)


        //FORPICTURE
        btnOpenCamera = findViewById(R.id.take_picture_button)
        ivPhoto = findViewById(R.id.take_picture_view)

        btnOpenCamera.setOnClickListener {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if(checkSelfPermission(android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                {
                    //permission was not enabled
                    val permission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
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


/*
        buttonMain = findViewById(R.id.button_back)
        buttonMain.setOnClickListener(View.OnClickListener {
            val intent = Intent (this, Activityhome::class.java)
            intent.putExtra("id_us", id_user)
            startActivity(intent)
        })
*/
        
        if(titleUpdate==null) {
            saveButton = findViewById(R.id.save_button)
            saveButton.setOnClickListener(View.OnClickListener {


                GlobalScope.launch {
                    reminderRepository.addReminder(
                        Reminder(
                            title = title.text.toString(),
                            message = message.text.toString(),
                            user_id = id_user
                        )
                    )

                }

                val intent = Intent(this@ActivityAdd, Activityhome::class.java)
                intent.putExtra("id", id_user)
                startActivity(intent)
            })

        }
        else {

        createButton = findViewById(R.id.create_button)
        title.setText(titleUpdate, TextView.BufferType.EDITABLE)
        message.setText(messageUpdate, TextView.BufferType.EDITABLE)
        createButton.setOnClickListener(View.OnClickListener {


            GlobalScope.launch {
                reminderRepository.updateReminder(
                    Reminder(
                        reminderID = reminderID,
                        title = title.text.toString(),
                        message = message.text.toString(),
                        user_id = id

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

                val intent = Intent(this@ActivityAdd, Activityhome::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            })
            ///delete*/


        }///////


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

}