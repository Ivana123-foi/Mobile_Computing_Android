package com.example.homeworkmc.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkmc.R
import com.example.homeworkmc.RecycleAdapter
import kotlinx.android.synthetic.main.fragment_m.*

class Main_Fragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapt: RecyclerView.Adapter<RecycleAdapter.ViewHolder>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_m, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recycleview.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            recycleview.layoutManager = layoutManager
            // set the custom adapter to the RecyclerView
            adapt = RecycleAdapter()
            recycleview.adapter = adapt
        }
    }




}