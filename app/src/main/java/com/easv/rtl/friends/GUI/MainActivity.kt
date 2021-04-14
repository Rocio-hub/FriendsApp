package com.easv.rtl.friends.GUI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easv.rtl.friends.FriendDao_Impl
import com.easv.rtl.friends.IFriendDao
import com.easv.rtl.friends.ListAdapter
import com.easv.rtl.friends.Model.BEFriend
import com.easv.rtl.friends.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ListAdapter.OnItemClickListener {

    private val REQUEST_CODE = 1
    val TAG = "xyz"
    lateinit var friendsRepo: IFriendDao

   // var friendsList : List<BEFriend> = Friends().getAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        friendsRepo = FriendDao_Impl(this)
        var list: List<BEFriend> = friendsRepo.getAll()
        myRecyclerView.adapter = ListAdapter(list, this)
    }

    override fun onItemClick(friends: BEFriend, position: Int) {
        Toast.makeText(this, friends.name, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailActivity::class.java)

        intent.putExtra("MYFRIEND", friends)
        intent.putExtra("POSITION", position)
        intent.putExtra("ISUPDATE", true)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("ISUPDATE", false)
                startActivityForResult(intent, REQUEST_CODE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var position: Int? = data?.getIntExtra("POSITION", -1)
        var isUpdate: Boolean? = data?.getBooleanExtra("ISUPDATE", false)
        if (position != null) {
            when (resultCode) {
                RESULT_OK -> {
                    var friend: BEFriend = data?.getSerializableExtra("MYFRIEND") as BEFriend
                    if (isUpdate!=null && isUpdate)
                        updateFriend(friend, position)
                    else
                        addFriend(friend)
                }
                RESULT_CANCELED -> {
                    deleteFriend(position)
                }
            }
        }
    }

    private fun addFriend(friend: BEFriend) {
      /*  var list: MutableList<BEFriend> = friendsRepo.getAll().toMutableList()
        list.add(friend)
        myRecyclerView.adapter = ListAdapter(list, this) */
    }

    private fun updateFriend(friend: BEFriend, position: Int) {
     /*   var list: MutableList<BEFriend> = friendsRepo.getAll().toMutableList()
        list[position] = friend
        myRecyclerView.adapter = ListAdapter(list, this)*/
    }

    private fun deleteFriend(position: Int) {
       /* var list: MutableList<BEFriend> = friendsRepo.getAll().toMutableList()
        list.removeAt(position)
        myRecyclerView.adapter = ListAdapter(list, this)*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflate the menu. Add items to the action bar if it is present
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "Context item selected " + item.itemId)
        return when (item.itemId) {
            R.id.create -> {
                Toast.makeText(baseContext, "CREATE", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.update -> {
                Toast.makeText(baseContext, "UPDATE", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.print -> {
                Toast.makeText(baseContext, "PRINT", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

}