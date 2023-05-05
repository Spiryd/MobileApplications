package com.example.gallery

import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 200
    private var imagePaths: MutableList<String> = mutableListOf()
    private lateinit var imagesRecyclerView: RecyclerView
    private lateinit var imagesRecyclerViewAdapter : ImagesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imagesRecyclerView = findViewById(R.id.imageGalleryRecyclerView)
        requestPermissions()

        prepareRecyclerView()
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
            PERMISSION_REQUEST_CODE
        )
    }

    private fun prepareRecyclerView() {
        imagesRecyclerViewAdapter = ImagesRecyclerViewAdapter(this, imagePaths)

        val manager = GridLayoutManager(this, 4)

        imagesRecyclerView.layoutManager = manager
        imagesRecyclerView.adapter = imagesRecyclerViewAdapter
    }


    private fun getImagePath() {
        // in this method we are adding all our image paths
        // in our arraylist which we have created.
        // on below line we are checking if the device is having an sd card or not.
        val isSDPresent = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        if (isSDPresent) {

            // if the sd card is present we are creating a new list in
            // which we are getting our images data with their ids.
            val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)

            // on below line we are creating a new
            // string to order our images by string.
            val orderBy = MediaStore.Images.Media._ID

            // this method will stores all the images
            // from the gallery in Cursor
            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                orderBy
            )

            // below line is to get total number of images
            val count = cursor!!.count

            // on below line we are running a loop to add
            // the image file path in our array list.
            for (i in 0 until count) {

                // on below line we are moving our cursor position
                cursor.moveToPosition(i)

                // on below line we are getting image file path
                val dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)

                // after that we are getting the image file path
                // and adding that path in our array list.
                imagePaths.add(cursor.getString(dataColumnIndex))
            }
            imagesRecyclerViewAdapter.notifyDataSetChanged()
            // after adding the data to our
            // array list we are closing our cursor.
            cursor.close()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissions.let { super.onRequestPermissionsResult(requestCode, it, grantResults) }
        // this method is called after permissions has been granted.
        when (requestCode) {
            PERMISSION_REQUEST_CODE ->                 // in this case we are checking if the permissions are accepted or not.
                if (grantResults.isNotEmpty()) {
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (storageAccepted) {
                        // if the permissions are accepted we are displaying a toast message
                        // and calling a method to get image path.
                        Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show()
                        getImagePath()
                    } else {
                        // if permissions are denied we are closing the app and displaying the toast message.
                        Toast.makeText(
                            this,
                            "Permissions denied, Permissions are required to use the app..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}