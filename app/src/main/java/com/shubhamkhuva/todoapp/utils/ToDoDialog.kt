package com.shubhamkhuva.todoapp.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.shubhamkhuva.todoapp.db.DbHelper
import com.shubhamkhuva.todoapp.activities.HomeActivity
import com.shubhamkhuva.todoapp.R
import java.util.*

class ToDoDialog {
    var dbHelper: DbHelper? = null

    fun openAdd(context: Context, id: String, notes: String, date: String, time: String) {
        dbHelper = DbHelper(context)
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_addtodo)

        val addbtn = dialog.findViewById(R.id.addbtn) as Button
        val datetext = dialog.findViewById(R.id.datetext) as EditText
        val timetext = dialog.findViewById(R.id.timetext) as EditText
        val notestext = dialog.findViewById(R.id.notestext) as EditText
        val title = dialog.findViewById(R.id.title) as TextView
        val closeicon = dialog.findViewById(R.id.closeicon) as ImageView
        var update = false
        if(!id.equals("")){
            notestext.setText(notes)
            datetext.setText(date)
            timetext.setText(time)
            update = true
            addbtn.setText("Update")
            title.setText("Update your ToDo")
        }
        addbtn.setOnClickListener {
            if(update){
                dbHelper?.updateAllData(id,notestext.text.toString(), datetext.text.toString(), timetext.text.toString());
            }else {
                dbHelper?.insertNewTask(notestext.text.toString(), datetext.text.toString(), timetext.text.toString());
            }
            dialog.dismiss()
            (context as HomeActivity).readDatafromDB()
        }

        datetext.setOnClickListener {
            val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                view.minDate = System.currentTimeMillis()
                val monthOfYeartemp = monthOfYear + 1
                var nwMonth = monthOfYeartemp.toString()
                if(monthOfYear < 10) {
                    nwMonth = "0$nwMonth"
                }

                var nwdayOfMonth = dayOfMonth.toString()
                if(dayOfMonth < 10) {
                    nwdayOfMonth = "0$nwdayOfMonth"
                }

                datetext.setText("$nwdayOfMonth-$nwMonth-$year")
            }
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)

            val mDate = DatePickerDialog(context, date, mYear, mMonth, mDay)
            mDate.datePicker.minDate = System.currentTimeMillis()
            mDate.show()

        }
        timetext.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            var hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(context,
                TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                    hour = selectedHour
                    val minutes = selectedMinute
                    var timeSet = ""
                    if (hour > 12) {
                        hour -= 12
                        timeSet = "PM"
                    } else if (hour === 0) {
                        hour += 12
                        timeSet = "AM"
                    } else if (hour === 12) {
                        timeSet = "PM"
                    } else {
                        timeSet = "AM"
                    }

                    var min = ""
                    if (minutes < 10)
                        min = "0$minutes"
                    else
                        min = minutes.toString()

                    // Append in a StringBuilder
                    val aTime = StringBuilder().append(hour).append(':')
                        .append(min).append(" ").append(timeSet).toString()
                    timetext.setText(
                        aTime
                    )
                }, hour, minute, true
            )//Yes 24 hour time
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }
        closeicon.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}
