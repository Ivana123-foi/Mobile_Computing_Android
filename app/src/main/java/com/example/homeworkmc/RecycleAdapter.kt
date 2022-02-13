package com.example.homeworkmc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkmc.entity.Reminder
import kotlinx.android.synthetic.main.fragment_main.view.*

 class RecycleAdapter(var items: List<Reminder>,  private val listener: OnItemClickListener) : RecyclerView.Adapter<RecycleAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): RecycleAdapter.ViewHolder
    {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.fragment_main, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: RecycleAdapter.ViewHolder, position: Int)
    {
        val currentItem = items[position]

        holder.itemTitle.text=currentItem.title
        holder.itemDetails.text=currentItem.message

    }

     override fun getItemCount()= items.size


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener{

        var itemTitle: TextView = itemView.item_title
        var itemDetails: TextView = itemView.item_description
        val buttonEdit: ImageButton = itemView.item_button_edit


        init {
            buttonEdit.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {
            val title : TextView = this.itemTitle
            val position: Int = adapterPosition
            if (position!= RecyclerView.NO_POSITION){
                listener.onItemClick(title.text.toString())
            }
           /* val position: Int = adapterPosition
            if (position!= RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }*/

        }
    }

     interface OnItemClickListener {
         //fun onItemClick(position: Int)
         fun onItemClick(title : String)
     }


 }


