package com.example.notes_app.Model

import android.os.Parcel
import android.os.Parcelable

class Notes_Data(var id: Int, var title: String, var note: String, var date: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(note)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Notes_Data> {
        const val TABLE_NAME = "Notes"
        const val COL_ID = "Id"
        const val COL_NOTE = "note"
        const val COL_TITLE = "title"
        const val COL_DATE = "date"
        const val TABLE_CREAT =
            "create table ${TABLE_NAME} ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    "$COL_TITLE  TEXT NOT NULL , $COL_NOTE  TEXT NOT NULL , $COL_DATE  TEXT NOT NULL) "

        override fun createFromParcel(parcel: Parcel): Notes_Data {
            return Notes_Data(parcel)
        }

        override fun newArray(size: Int): Array<Notes_Data?> {
            return arrayOfNulls(size)
        }
    }

}