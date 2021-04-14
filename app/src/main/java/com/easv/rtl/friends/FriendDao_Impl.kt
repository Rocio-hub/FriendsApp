package com.easv.rtl.friends

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.easv.rtl.friends.Data.BEFriend

class FriendDao_Impl : IFriendDao {
    var TAG: String = "xyz"
    var friendDb: SQLiteDatabase

    constructor(context: Context) {
        val openHelper = MyOpenHelper(context)
        friendDb = openHelper.writableDatabase
    }

    override fun create(f: BEFriend) {
        val friend1 = ContentValues()
        friend1.put("name", "Rocio")
        friend1.put("phone", "123")
        val result1 = friendDb.insert("friend", null, friend1)
        Log.d(TAG, "create from DAO returned ${result1}")

        val friend2 = ContentValues()
        friend1.put("name", "Lelota")
        friend1.put("phone", "123")
        val result2 = friendDb.insert("friend", null, friend2)
        Log.d(TAG, "create from DAO returned ${result2}")

   /*     val cv = ContentValues()
        cv.put("name", f.name)
        cv.put("phone", f.phone)
        val result = friendDb.insert("friend", null, cv)
        if (result > 0.toLong()) {
            f.id = result.toInt()
            Log.d(TAG, "create from DAO returned ${result}")
        } else Log.d(TAG, "create from DAO failed") */
    }

    override fun getAll(): List<BEFriend> {
        val query = "SELECT * FROM Friend ORDER BY id"
        val cursor = friendDb.rawQuery(query, null)
        val result = getByCursor(cursor)
        Log.d(TAG, "getAll from DAO returned ${result.size} friends")
        return result
    }

    override fun update(f: BEFriend) {
        TODO("Not yet implemented")
    }

    override fun delete(f: BEFriend) {
        TODO("Not yet implemented")
    }

    private fun getByCursor(cursor: Cursor): List<BEFriend> {
        val result = ArrayList<BEFriend>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex("id")).toInt()
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val phone = cursor.getString(cursor.getColumnIndex("phone"))
                val camFile = cursor.getString(cursor.getColumnIndex("camFile")).toByteArray()
                result.add(BEFriend(id, name, phone, camFile))
            } while (cursor.moveToNext())
        }
        return result
    }

    inner class MyOpenHelper(context: Context) :
        SQLiteOpenHelper(context, "FriendDB", null, 1) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL("CREATE TABLE Friend(id INTEGER PRIMARY KEY, name TEXT, phone INTEGER, camFile VARCHAR(256))")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            onCreate(db)
        }
    }

}