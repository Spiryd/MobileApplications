package com.example.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.gallery.databinding.ActivityImageDetailBinding
import com.squareup.picasso.Picasso
import java.io.File


class ImageDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageDetailBinding
    private lateinit var image: MutableLiveData<ImageHolder>
    private var imageView: ImageView? = null
    private var scaleGestureDetector: ScaleGestureDetector? = null

    private var mScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        image = MutableLiveData(intent.getParcelableExtra("image", ImageHolder::class.java)!!)
        imageView = findViewById(R.id.detailedImage)
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        binding.infoButton.setOnClickListener {
            ImageInfo(image).show(supportFragmentManager, "ImageInfo")
        }

        binding.backButton.setOnClickListener { onBack() }

        val imgFile = File(image.value!!.path)

        if (imgFile.exists()) {
            Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background)
                .into(imageView)
        }
    }

    private fun onBack(){
        val data = Intent()
        data.putExtra("backImage", image.value)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {
        scaleGestureDetector!!.onTouchEvent(motionEvent!!)
        return true
    }

     inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {

            mScaleFactor *= scaleGestureDetector.scaleFactor
            mScaleFactor = 0.1f.coerceAtLeast(mScaleFactor.coerceAtMost(10.0f))

            imageView?.scaleX = mScaleFactor
            imageView?.scaleY = mScaleFactor
            return true
        }
    }
}