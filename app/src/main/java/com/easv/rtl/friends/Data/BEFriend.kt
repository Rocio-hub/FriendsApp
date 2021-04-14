package com.easv.rtl.friends.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class BEFriend(
    @PrimaryKey(autoGenerate = true) var id: Int,
                                     var name: String,
                                     var phone: String,
                            //  var isFavorite: Boolean,
                                     var cameraFile: ByteArray
) : Serializable {}
