package com.example.homeworkmc.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.homeworkmc.R
import com.example.homeworkmc.MainActivity

import android.content.Intent
import android.util.Log
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkmc.ActivityRegistration


class profil_Fragment : Fragment() {


    private lateinit var button: ImageButton
    private  lateinit var v: View

   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       v=  inflater.inflate(R.layout.profil, container, false)

       return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button = v.findViewById(R.id.button_profile)
        button.setOnClickListener(View.OnClickListener {
           Toast.makeText(activity, "text fragment", Toast.LENGTH_SHORT).show()
        })

    }


    }

