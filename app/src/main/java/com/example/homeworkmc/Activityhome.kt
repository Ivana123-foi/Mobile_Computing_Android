package com.example.homeworkmc


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.homeworkmc.entity.Reminder
import com.example.homeworkmc.entity.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.homeworkmc.fragment.profil_Fragment
import com.example.homeworkmc.fragment.Main_Fragment
import com.example.homeworkmc.fragment.Map_fragmetn
import com.example.homeworkmc.repository.ReminderRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Activityhome(private val reminderRepository: ReminderRepository = Graph.reminderRepository): AppCompatActivity(){




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_main)


        val id_user : Long = intent.getLongExtra("id", -1)
        val homeFragment = Main_Fragment(id_user = id_user)
        val mapa = Map_fragmetn(id_user = id_user)

        val profilFragment = profil_Fragment()
        val navigation : BottomNavigationView = findViewById((R.id.design_navigation_view))

        replaceFragment(homeFragment)

        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(homeFragment)
                R.id.profile -> replaceFragment(profilFragment)
                R.id.map -> replaceFragment(mapa)

            }
            true
        }




    }

    private fun replaceFragment(fragment: Fragment) {
        if(fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }



}