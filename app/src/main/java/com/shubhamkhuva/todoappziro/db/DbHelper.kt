package com.shubhamkhuva.todoappziro.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import org.json.JSONObject
import org.json.JSONArray


class DbHelper(context: Context) : SQLiteOpenHelper(context,
    DB_NAME, null,
    DB_VER
) {

    fun getResults(): JSONArray {

        val db = this.readableDatabase
        val cursor = db.query(
            DB_TABLE, arrayOf(
                DB_COLUMN_ID,
                DB_COLUMN_TASK,
                DB_COLUMN_DATE,
                DB_COLUMN_TIME,
                DB_COLUMN_STATUS
            ), null, null, null, null, null)

        val resultSet = JSONArray()

        cursor.moveToFirst()
        while (cursor.isAfterLast == false) {

            val totalColumn = cursor.columnCount
            val rowObject = JSONObject()

            for (i in 0 until totalColumn) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i))
                        } else {
                            rowObject.put(cursor.getColumnName(i), "")
                        }
                    } catch (e: Exception) {

                    }

                }
            }
            resultSet.put(rowObject)
            cursor.moveToNext()
        }
        cursor.close()

        return resultSet
    }

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("CREATE TABLE " + DB_TABLE + " ("
                + DB_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + DB_COLUMN_TASK +" TEXT, "
                + DB_COLUMN_DATE +" TEXT, "
                + DB_COLUMN_TIME +" TEXT, "
                + DB_COLUMN_STATUS +" TEXT);");

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val query = String.format("DELETE TABLE IF EXISTS %s",
            DB_TABLE
        )
        db.execSQL(query)
        onCreate(db)

    }

    fun insertNewTask(task: String, date: String, time: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DB_COLUMN_TASK, task)
        values.put(DB_COLUMN_DATE, date)
        values.put(DB_COLUMN_TIME, time)
        values.put(DB_COLUMN_STATUS, "0")
        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    fun updateStatus(id: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(DB_COLUMN_STATUS, "1")
        db.update(DB_TABLE, cv, "id="+id+"", null);
    }

    fun updateAllData(id:String,task: String, date: String, time: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(DB_COLUMN_STATUS, "0")
        cv.put(DB_COLUMN_TASK, task)
        cv.put(DB_COLUMN_DATE, date)
        cv.put(DB_COLUMN_TIME, time)
        db.update(DB_TABLE, cv, "id='"+id+"'", null);
    }

    fun deleteTask(task: String) {
        val db = this.writableDatabase
        db.delete(DB_TABLE, "$DB_COLUMN_TASK = ?", arrayOf(task))
        db.close()
    }

    companion object {

        private val DB_NAME = "ToDoZIRO"
        private val DB_VER = 1
        private val DB_TABLE = "TODOList"
        private val DB_COLUMN_ID = "id"
        private val DB_COLUMN_TASK = "TaskName"
        private val DB_COLUMN_DATE = "Date"
        private val DB_COLUMN_TIME = "Time"
        private val DB_COLUMN_STATUS = "Status"
    }
}
