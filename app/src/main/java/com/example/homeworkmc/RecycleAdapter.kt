package com.example.homeworkmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


open class RecycleAdapter : RecyclerView.Adapter<RecycleAdapter.ViewHolder>(){



    private var title = arrayOf("Take out garbage","Wash the floor","Clothes")

    private var details = arrayOf("Plastic","No more","Put away in the closet")


    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): RecycleAdapter.ViewHolder
    {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.fragment_main, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: RecycleAdapter.ViewHolder, position: Int)
    {
        holder.itemTitle.text=title[position]
        holder.itemDetails.text=details[position]


    }

    override fun getItemCount():Int
     {
        return title.size
     }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var itemTitle: TextView
        var itemDetails: TextView

        init {

            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetails = itemView.findViewById(R.id.item_description)

        }
    }
}