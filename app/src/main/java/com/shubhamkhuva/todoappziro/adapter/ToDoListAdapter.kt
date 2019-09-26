package com.shubhamkhuva.newday.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.shubhamkhuva.todoappziro.models.ToDoModel
import com.shubhamkhuva.todoappziro.activities.HomeActivity
import android.content.Context
import android.graphics.Paint
import com.shubhamkhuva.todoappziro.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ToDoListAdapter(
    val todoList: ArrayList<ToDoModel>, val applicationContext: Context) :
    RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycle_todosingle, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(todoList[position],applicationContext)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(tolist: ToDoModel, applicationContext: Any?) {
            val todotext = itemView.findViewById(R.id.todotext) as TextView
            todotext.text =tolist.task
            val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val daytime = itemView.findViewById(R.id.daytime) as TextView
            if(currentDate.equals(tolist.date)){
                daytime.text = "Today "+ "At " + tolist.time
            }else {
                daytime.text = "Date: " + tolist.date + " Time: " + tolist.time
            }
            val donetext = itemView.findViewById(R.id.donetext) as TextView
            val donetext_true = itemView.findViewById(R.id.donetext_true) as TextView
            val edittodo = itemView.findViewById(R.id.edittodo) as ImageView
            if(tolist.status=="0"){
                donetext.visibility = View.VISIBLE
            }else{
                edittodo.visibility = View.INVISIBLE
                todotext.paintFlags = todotext.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG
                donetext_true.visibility = View.VISIBLE
            }

            val deletetodo = itemView.findViewById(R.id.deletetodo) as ImageView
            deletetodo.setOnClickListener {
                (applicationContext as HomeActivity).deleteToDo(tolist.task)
            }

            donetext.setOnClickListener {
                todotext.paintFlags = todotext.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG
                donetext.visibility = View.GONE
                edittodo.visibility = View.INVISIBLE
                donetext_true.visibility = View.VISIBLE
                (applicationContext as HomeActivity).updateStatusToDo(tolist.id)
            }
            edittodo.setOnClickListener {
                (applicationContext as HomeActivity).updateDataToDo(tolist.id,tolist.task,tolist.date,tolist.time)
            }
        }
    }
}