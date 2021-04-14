package com.easv.rtl.friends.Data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FriendDao {

    @Query("SELECT * FROM BEFriend ORDER BY id")
    fun getAll(): LiveData<List<BEFriend>>

    @Query("SELECT * FROM BEFriend WHERE id = (:id)")
    fun getAllById(id: Int): LiveData<List<BEFriend>>

    @Insert
    fun insert(f: BEFriend)

    @Update
    fun update(f: BEFriend)

    @Delete
    fun delete(f: BEFriend)
}