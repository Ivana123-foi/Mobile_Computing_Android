package com.example.homeworkmc.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkmc.ActivityAdd
import com.example.homeworkmc.Graph
import com.example.homeworkmc.R
import com.example.homeworkmc.RecycleAdapter
import com.example.homeworkmc.entity.Reminder
import com.example.homeworkmc.repository.ReminderRepository
import kotlinx.android.synthetic.main.fragment_m.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.channels.AsynchronousFileChannel.open
import java.nio.channels.FileChannel.open

class Main_Fragment(private val reminderRepository: ReminderRepository = Graph.reminderRepository, private val id_user: Long) : Fragment(), RecycleAdapter.OnItemClickListener {


    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapt: RecyclerView.Adapter<RecycleAdapter.ViewHolder>? = null
    private lateinit var v: View
    private lateinit var buttonMain: ImageButton
    private lateinit var ReminderList: List<Reminder>


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

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recycleview.apply {

            GlobalScope.launch {
                ReminderList= reminderRepository.getReminder(id_user)
                layoutManager = LinearLayoutManager(activity)
                recycleview.layoutManager = layoutManager

                adapt = RecycleAdapter(reminderRepository.getReminder(id_user), this@Main_Fragment)
                recycleview.adapter = adapt

            }
        }

        buttonMain = v.findViewById(R.id.button_Screen)

        buttonMain.setOnClickListener(View.OnClickListener {

            val intent = Intent(activity, ActivityAdd::class.java)
            intent.putExtra("id", id_user)
            startActivity(intent)

        })


    }

    override fun onItemClick(title: String) {

        //Toast.makeText(activity, "You clicked item no. ${position}.", Toast.LENGTH_SHORT).show()


            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            //var ReminderList : List<Reminder> = reminderRepository.getReminder(id_user)

            //  val ReminderList: List<Reminder> = reminderRepository.getReminder(id_user)


            //Toast.makeText(activity, "You clicked item no. ${position}.", Toast.LENGTH_SHORT).show()

            for (reminder in ReminderList) {
              /* var number : Int = reminder.reminderID.toInt()
                var posicija : Int = position + reminder.reminderID.toInt() -1*/





                if (title == reminder.title) {

                    //Toast.makeText(activity, "You clicked item no. ${title}.  ${reminder.reminderID}.  ", Toast.LENGTH_SHORT).show()

                 //  Toast.makeText(activity, "You clicked item no. ${position}.  ${reminder.reminderID}.  ", Toast.LENGTH_SHORT).show()
                    //Toast.makeText(activity, "You clicked item no. ${number}.  ${posicija}.  ", Toast.LENGTH_SHORT).show()

                  val intent = Intent(activity, ActivityAdd::class.java)
                    intent.putExtra("ReminderID", reminder.reminderID)
                    intent.putExtra("Title", reminder.title)
                    intent.putExtra("Message", reminder.message)
                    intent.putExtra("id_user", reminder.user_id)
                    startActivity(intent)
                }
            }

    }

}