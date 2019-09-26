package com.shubhamkhuva.todoappziro.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.shubhamkhuva.newday.adapter.ToDoListAdapter
import com.shubhamkhuva.todoappziro.models.ToDoModel
import kotlinx.android.synthetic.main.activity_home.*
import com.shubhamkhuva.todoappziro.db.DbHelper
import org.json.JSONObject
import kotlin.collections.ArrayList
import android.widget.*
import com.shubhamkhuva.todoappziro.R
import com.shubhamkhuva.todoappziro.utils.GeneralUtils
import com.shubhamkhuva.todoappziro.utils.ToDoDialog

class HomeActivity : AppCompatActivity() {
    var dbHelper: DbHelper? = null
    var toDoModel = ArrayList<ToDoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        dbHelper = DbHelper(this)

        recycle_todo.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)

        readDatafromDB()
        fab.setOnClickListener { view ->
            ToDoDialog().openAdd(this,"","","","")
        }
        logout.setOnClickListener {
            GeneralUtils().saveToSharedPerfBoolean(this,"login",false)
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun readDatafromDB() {
        val taskLists = dbHelper?.getResults()
        if(taskLists!!.length()>0) {
            toDoModel.clear()
            if (taskLists != null) { 
                for (i in 0 until taskLists.length()) {
                    toDoModel.add(
                        ToDoModel(
                            (taskLists[i] as JSONObject).get("id").toString(),
                            (taskLists[i] as JSONObject).get("TaskName").toString(),
                            (taskLists[i] as JSONObject).get("Date").toString(),
                            (taskLists[i] as JSONObject).get("Time").toString(),
                            (taskLists[i] as JSONObject).get("Status").toString()
                        )
                    )
                }
            }

            val adapter = ToDoListAdapter(toDoModel, this)
            recycle_todo.adapter = adapter
            emptytodo.visibility = View.GONE
        }
        else{
            toDoModel.clear()
            val adapter = ToDoListAdapter(toDoModel, this)
            recycle_todo.adapter = adapter
            emptytodo.visibility = View.VISIBLE
        }
    }

    fun deleteToDo(notes:String){
        dbHelper?.deleteTask(notes)
        readDatafromDB()
    }
    fun updateStatusToDo(id:String){
        dbHelper?.updateStatus(id);
    }
    fun updateDataToDo(id:String,notes:String,date:String,time:String){
        ToDoDialog().openAdd(this,id,notes,date,time)
    }

}
