package com.example.finalproject_kotlin.Data_Base

import android.app.Activity
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.notes_app.Model.Notes_Data

class Data_Base_Holder(var activity: Activity) : SQLiteOpenHelper(activity, "Notes_App", null, 1) {

    private var dbd = writableDatabase


    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(Notes_Data.TABLE_CREAT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE ${Notes_Data.TABLE_NAME}")
        onCreate(db)
    }

    //............................... Notes Table
    fun insert_Note(title: String, note: String, date: String): Boolean {
        val cv = ContentValues()
        cv.put(Notes_Data.COL_TITLE, title)
        cv.put(Notes_Data.COL_NOTE, note)
        cv.put(Notes_Data.COL_DATE, date)
        return dbd.insert(Notes_Data.TABLE_NAME, null, cv) > 0
    }

    fun get_id(title: String): Int {
        val c = dbd.rawQuery(
            "SELECT ${Notes_Data.COL_ID} FROM ${Notes_Data.TABLE_NAME} WHERE ${Notes_Data.COL_TITLE} = '$title' ",
            null
        )
        val bol = c.moveToFirst()
        if (bol) return c.getInt(0)
        else return -1
    }

    fun edit_Data(id: Int, title: String, note: String): Boolean {
        val c = dbd.rawQuery(
            "UPDATE ${Notes_Data.TABLE_NAME} SET ${Notes_Data.COL_NOTE} = '$note' , ${Notes_Data.COL_TITLE} = '$title'" +
                    "WHERE ${Notes_Data.COL_ID} = $id", null
        )
        return c.count > 0
    }

    fun get_AllNotes(): ArrayList<Notes_Data> {
        val notes = ArrayList<Notes_Data>()
        val c = dbd.rawQuery(
            "SELECT * FROM ${Notes_Data.TABLE_NAME} ",
            null
        )
        c.moveToFirst()
        while (!c.isAfterLast) {
            val p1 =
                Notes_Data(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3)
                )
            notes.add(p1)
            c.moveToNext()
        }
        c.close()
        return notes
    }

    fun delete_notes(id: Int): Boolean {
        val c =
            dbd.rawQuery(
                "DELETE FROM ${Notes_Data.TABLE_NAME} WHERE ${Notes_Data.COL_ID} = $id",
                null
            )
        return c.count > 0
    }

}