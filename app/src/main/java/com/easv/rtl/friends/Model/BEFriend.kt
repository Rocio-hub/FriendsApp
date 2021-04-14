package com.easv.rtl.friends.Model

import java.io.Serializable

data class BEFriend(
    var id: Int,
    var name: String,
    var phone: String,
  //  var isFavorite: Boolean,
    var cameraFile: ByteArray
    ): Serializable { }
