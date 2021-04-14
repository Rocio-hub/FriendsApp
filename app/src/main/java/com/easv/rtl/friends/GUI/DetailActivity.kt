package com.easv.rtl.friends.GUI

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.easv.rtl.friends.Data.BEFriend
import com.easv.rtl.friends.Data.FriendDatabase
import com.easv.rtl.friends.Data.FriendRepositoryInDB
import com.easv.rtl.friends.IFriendDao
import com.easv.rtl.friends.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    val TAG = "xyz"
    var position: Int = -1
    var isUpdate: Boolean = false
    private val REQUEST_CODE = 2
    private val CAM_REQUEST_CODE = 3
    lateinit var friendsDb: IFriendDao
    //val context = this
   // val db = FriendDao_Impl(this)
    lateinit var db : FriendDatabase
    lateinit var myImageBitmap: ByteArray

    //  var camFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        FriendRepositoryInDB.initialize(this)
        insertTestData()
        setupDataObserver()

       // friendsDb = FriendDao_Impl(this)
        checkPermissions()

        isUpdate = getIntent().getBooleanExtra("ISUPDATE", false)
        if (isUpdate) {
            var friend: BEFriend = getIntent().getSerializableExtra("MYFRIEND") as BEFriend
            position = getIntent().getIntExtra("POSITION", -1)
            editText_name.setText(friend.name)
            editText_phone.setText(friend.phone)
        }
    }

    private fun insertTestData() {
        val repo = FriendRepositoryInDB.get()
        repo.insert(BEFriend(0, "Lele", "123", byteArrayOf(0x2E, 0x38)))
        repo.insert(BEFriend(0, "Yui", "456", byteArrayOf(0x2E, 0x38)))
        repo.insert(BEFriend(0, "Chloe", "789", byteArrayOf(0x2E, 0x38)))
    }

    var cache: List<BEFriend>? = null

    private fun setupDataObserver() {
        val repo = FriendRepositoryInDB.get()
        val nameObserver = Observer<List<BEFriend>> { friends ->
            cache = friends
            val asStrings = friends.map { f -> "${f.id}, ${f.name}" }
            val adapter: ArrayAdapter<String> =
                ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    asStrings.toTypedArray())
        }
        repo.getAll().observe(this, nameObserver)
    }

    fun onClickSave(view: View) {
        val repo = FriendRepositoryInDB.get()
        repo.insert(BEFriend(0, editText_name.toString(), editText_phone.toString(), byteArrayOf(0x2E, 0x38)))

        val intent = Intent(this, MainActivity::class.java)
        if (editText_name.text.toString().isNotEmpty() &&
            editText_phone.text.toString().isNotEmpty()
        ) {
            val friend =
                BEFriend(1, editText_name.text.toString(), editText_phone.text.toString(), myImageBitmap)
            friendsDb.create(friend)
            intent.putExtra("MYFRIEND", friend)
            intent.putExtra("ISUPDATE", isUpdate)
        } else {
            Toast.makeText(this, "Please, fill all information", Toast.LENGTH_SHORT).show()
        }
        if (isUpdate)
            intent.putExtra("POSITION", position)
        setResult(RESULT_OK, intent)
        finish()
    }

    fun onClickDelete(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("POSITION", position)
        intent.putExtra("ISUPDATE", isUpdate)
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    fun onClickCall(view: View) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel: $" + "123456798")
        startActivity(intent)
    }

    fun onClickEmail(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        val receivers = arrayOf("roci0055@easv365.dk")
        intent.putExtra(Intent.EXTRA_EMAIL, receivers)
        //intent.putExtra(Intent.EXTRA_SUBJECT, "Test")
        intent.putExtra(Intent.EXTRA_TEXT, "Hello, this is the signature of the email")
        startActivity(intent)
    }

    fun onClickCam(view: View) {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, CAM_REQUEST_CODE)
        } else Log.d(TAG, "Camera cannot be started")
    }


    /*  private fun getOutputMediaFile(folder: String): File? {
        val mediaStorageDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), folder)

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Failed to create directory")
                return null
            }
        }

        val timeStamp: String = SimpleDateFormat("MMddyyyy_HHmmss").format(Date())
        val postfix = "jpg"
        val prefix = "IMG"
        return File(mediaStorageDir.path + File.separator + prefix + "_" + timeStamp + "." + postfix)
    }*/

    //Checks if the app has the required permissions, and prompts the user with the ones missing.
    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        val permissions = mutableListOf<String>()
        if (!isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!isGranted(Manifest.permission.CAMERA)) permissions.add(Manifest.permission.CAMERA)
        if (permissions.size > 0)
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), REQUEST_CODE)
    }

    private fun isGranted(permission: String): Boolean =
        ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var imageButton_camera = findViewById<ImageButton>(R.id.imageButton_camera)

        when (requestCode) {
            CAM_REQUEST_CODE -> {
                if (resultCode == RESULT_OK && data != null) {
                    imageButton_camera.setImageBitmap(data.extras?.get("data") as Bitmap)
                } else
                    Toast.makeText(this, "Unrecoignized request code", Toast.LENGTH_SHORT).show()
            }
            /*if (resultCode == RESULT_OK) {
                val extras = data!!.extras
                val imageBitmap = extras!!["data"] as Bitmap
                showImageFromBitmap(imageButton_camera, imageBitmap)
            }*/
        }
    }
}


/*  private fun handleOther(resultCode: Int) {
      if(resultCode == RESULT_CANCELED)
          Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
      else Toast.makeText(this, "Picture not taken - ERROR", Toast.LENGTH_SHORT).show()
  } */


/*
* fun onClickBrowser(view: View) {
* val url = "http://www.easv.dk"
* val intent = Intent(Intent.ACTION_VIEW)
* intent.data = Uri.parse(url)
* startActivity(intent)
* */

/*
 private fun showYesNoDialog() {
 val alertDialogBuilder = AlertDialog.Builder(this)
 alertDialogBuilder.setTitle("Handling something")
 alertDialogBuilder
 .setMessage("blablabla")
  .setCancelable(true)
  .setPositiveButton("Direct") { dialog, id -> sendSMSDirectly() }
 .setNegativeButton("Start", { dialog, id -> startSMSActivity() })
 val alertDialog = alertDialogBuilder.create()
 alertDialog.show()*/