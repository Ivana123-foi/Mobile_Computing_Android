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
import java.time.LocalDate as LocalDate1
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.widget.RemoteViews
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.homeworkmc.*


class Main_Fragment(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
    private val id_user: Long
) : Fragment(), RecycleAdapter.OnItemClickListener {


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

        //Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()

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