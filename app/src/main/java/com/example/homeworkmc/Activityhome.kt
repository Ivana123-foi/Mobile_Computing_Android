package com.example.homeworkmc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.homeworkmc.R

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.homeworkmc.fragment.profil_Fragment
import com.example.homeworkmc.fragment.Main_Fragment

class Activityhome : AppCompatActivity() {

    private val homeFragment = Main_Fragment()
    private val profilFragment = profil_Fragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_main)
        replaceFragment(homeFragment)

        val navigation : BottomNavigationView = findViewById((R.id.design_navigation_view))

        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(homeFragment)
                R.id.profile -> replaceFragment(profilFragment)
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