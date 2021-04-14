package com.easv.rtl.friends

import com.easv.rtl.friends.Model.BEFriend

interface IFriendDao {

    fun create(f: BEFriend)

    fun getAll(): List<BEFriend>

    fun update(f: BEFriend)

    fun delete(f: BEFriend)
}