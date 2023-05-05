package com.example.gallery

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import java.io.File


class ImageDetailActivity : AppCompatActivity() {
    private var imgPath: String? = null
    private var imageView: ImageView? = null
    private var scaleGestureDetector: ScaleGestureDetector? = null

    // on below line we are defining our scale factor.
    private var mScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        // on below line getting data which we have passed from our adapter class.
        imgPath = intent.getStringExtra("imgPath")

        // initializing our image view.
        imageView = findViewById(R.id.detailedImage)

        // on below line we are initializing our scale gesture detector for zoom in and out for our image.
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        // on below line we are getting our image file from its path.
        val imgFile = File(imgPath)

        // if the file exists then we are loading that image in our image view.
        if (imgFile.exists()) {
            Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background)
                .into(imageView)
        }
    }

    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {
        // inside on touch event method we are calling on
        // touch event method and passing our motion event to it.
        scaleGestureDetector!!.onTouchEvent(motionEvent!!)
        return true
    }

     inner class ScaleListener : SimpleOnScaleGestureListener() {
        // on below line we are creating a class for our scale
        // listener and  extending it with gesture listener.
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            // inside on scale method we are setting scale
            // for our image in our image view.
            mScaleFactor *= scaleGestureDetector.scaleFactor
            mScaleFactor = 0.1f.coerceAtLeast(mScaleFactor.coerceAtMost(10.0f))

            // on below line we are setting
            // scale x and scale y to our image view.
            imageView?.scaleX = mScaleFactor
            imageView?.scaleY = mScaleFactor
            return true
        }
    }
}