package com.example.wickedcalendar

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.wickedcalendar.databinding.FragmentEventSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate

class EventSheet(private var event: Event?, private val date: LocalDate): BottomSheetDialogFragment(){
    private lateinit var binding: FragmentEventSheetBinding
    private lateinit var eventViewModel: EventViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = requireActivity()
        if (event != null){
            binding.eventTitle.text = "Edit Event"
            val editable = Editable.Factory.getInstance()
            binding.title.text = editable.newEditable(event!!.title)
            binding.dsc.text = editable.newEditable(event!!.description)
        }else{
            binding.eventTitle.text = "New Event"
        }

        eventViewModel = ViewModelProvider(activity)[EventViewModel::class.java]
        binding.saveButton.setOnClickListener {
            saveAction()
        }
    }

    private fun saveAction() {
        Log.i("save", "save")
        val dsc = binding.dsc.text.toString()
        val title = binding.title.text.toString()
        if(event == null){
            val newEvent = Event(title, date, dsc)
            eventViewModel.addEvent(newEvent)
        }else{
            eventViewModel.updateEvent(event!!.id, title, dsc, date)
        }
        dismiss()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEventSheetBinding.inflate(inflater, container, false)
        return binding.root
    }



}