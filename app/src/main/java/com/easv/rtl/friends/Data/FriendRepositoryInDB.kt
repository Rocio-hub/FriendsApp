package com.easv.rtl.friends.Data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.concurrent.Executors

class FriendRepositoryInDB private constructor(private val context: Context) {

    private val database: FriendDatabase = Room.databaseBuilder(context.applicationContext,
                                                                FriendDatabase::class.java,
                                                                "friend-database").build()

    private val friendDao = database.friendDao()

    fun getAll(): LiveData<List<BEFriend>> = friendDao.getAll()

    fun getAllById(id: Int) = friendDao.getAllById(id)

    private val executor = Executors.newSingleThreadExecutor()

    fun insert(f: BEFriend) {
        executor.execute { friendDao.insert(f) }
    }

    fun update(f: BEFriend) {
        executor.execute { friendDao.update(f) }
    }

    fun delete(f: BEFriend) {
        executor.execute { friendDao.delete(f) }
    }

    companion object {
        private var Instance: FriendRepositoryInDB? = null

        fun initialize(context: Context) {
            if(Instance == null)
                Instance = FriendRepositoryInDB(context)
        }

        fun get(): FriendRepositoryInDB {
            if(Instance != null) return Instance!!
            throw IllegalStateException("Friend repository is not initialized")
        }
    }
}