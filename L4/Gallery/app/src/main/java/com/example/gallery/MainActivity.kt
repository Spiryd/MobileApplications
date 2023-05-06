package com.example.gallery

import android.Manifest.permission.READ_MEDIA_IMAGES
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() , OnClickInterface{
    private val permissionRequestCode = 200
    private var images: MutableList<ImageHolder> = mutableListOf()
    private lateinit var imagesRecyclerView: RecyclerView
    private lateinit var imagesRecyclerViewAdapter : ImagesRecyclerViewAdapter

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                var backImage = intent.getParcelableExtra("backImage", ImageHolder::class.java)
                Log.i("imageResult", "$backImage")
                val idx = images.withIndex().first { backImage!!.uuid == it.value.uuid }.index
                images[idx] = backImage!!
                images.sortByDescending { it.rating }
                imagesRecyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imagesRecyclerView = findViewById(R.id.imageGalleryRecyclerView)
        prepareRecyclerView()
        requestPermissions()
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(applicationContext, READ_MEDIA_IMAGES)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        if (checkPermission()) {
            Toast.makeText(this, "Permissions granted..", Toast.LENGTH_SHORT).show()
            getImagePath()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(READ_MEDIA_IMAGES),
            permissionRequestCode
        )
    }

    private fun prepareRecyclerView() {

        imagesRecyclerViewAdapter = ImagesRecyclerViewAdapter(this, images, this)

        val manager = GridLayoutManager(this, 4)

        imagesRecyclerView.layoutManager = manager
        imagesRecyclerView.adapter = imagesRecyclerViewAdapter
    }


    private fun getImagePath() {
        val isSDPresent = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        if (isSDPresent) {
            val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
            val orderBy = MediaStore.Images.Media._ID
            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                orderBy
            )
            val count = cursor!!.count
            for (i in 0 until count) {

                cursor.moveToPosition(i)

                val dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)

                images.add(ImageHolder(cursor.getString(dataColumnIndex)))
            }
            images.sortBy { it.rating }
            imagesRecyclerViewAdapter.notifyDataSetChanged()
            cursor.close()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissions.let { super.onRequestPermissionsResult(requestCode, it, grantResults) }
        when (requestCode) {
            permissionRequestCode ->
                if (grantResults.isNotEmpty()) {
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (storageAccepted) {
                        Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show()
                        getImagePath()
                    } else {
                        Toast.makeText(
                            this,
                            "Permissions denied, Permissions are required to use the app..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    override fun onImageClick(position: Int) {
        val i = Intent(this, ImageDetailActivity::class.java)
        i.putExtra("image", images[position])
        startForResult.launch(i)
    }
}