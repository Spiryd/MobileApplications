package com.example.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.gallery.databinding.FragmentImageInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImageInfo(private var image: MutableLiveData<ImageHolder>) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentImageInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.description.setText(image.value!!.description)
        binding.ratingbar.rating = image.value!!.rating
        binding.saveButton.setOnClickListener {
            onSaveClicked()
        }
    }

    private fun onSaveClicked(){
        val tmpImage = image.value
        tmpImage!!.rating = binding.ratingbar.rating
        tmpImage.description = binding.description.text.toString()
        image.postValue(tmpImage)
        Log.i("save", "save")
        dismiss()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentImageInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
}