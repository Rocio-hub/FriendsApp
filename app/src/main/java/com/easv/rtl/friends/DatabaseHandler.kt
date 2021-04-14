package com.easv.rtl.friends

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASENAME = "MY DATABASE"
val TABLENAME = "Friends"
val COL_ID = "id"
val COL_NAME = "name"
val COL_PHONE = "phone"
val COL_CAMFILE = "camfile"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
       /* val createTable =
            "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME + " VARCHAR(256)," + COL_PHONE + " INTEGER" + COL_CAMFILE + "BLOB)"
        db?.execSQL(createTable)*/
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {/*
       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
       // onCreate(db)*/
    }
/*
    fun insertData(friend: BEFriend) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, friend.name)
        cv.put(COL_PHONE, friend.phone)
     //   cv.put(COL_CAMFILE, friend.cameraFile)

        val result = db.insert(TABLENAME, null, cv)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData(): MutableList<BEFriend> {
        val list: MutableList<BEFriend> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val friend = BEFriend(
                    result.getString(result.getColumnIndex(COL_ID)).toInt(),
                    result.getString(result.getColumnIndex(COL_NAME)),
                    result.getString(result.getColumnIndex(COL_PHONE)),
                    result.getString(result.getColumnIndex(COL_CAMFILE)).toByteArray()
                )
                /* friend.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                 friend.name = result.getString(result.getColumnIndex(COL_NAME))
                 friend.phone = result.getString(result.getColumnIndex(COL_PHONE)).toInt()*/
                list.add(friend)
            } while (result.moveToNext())
        }
        return list
    } */
}
