package com.example.wickedcalendar

import android.app.TimePickerDialog
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
import java.time.LocalTime

class EventSheet(private var event: Event?, private val date: LocalDate): BottomSheetDialogFragment(){
    private lateinit var binding: FragmentEventSheetBinding
    private lateinit var eventViewModel: EventViewModel
    private var startTime: LocalTime? = null
    private var endTime: LocalTime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = requireActivity()
        if (event != null){
            binding.eventTitle.text = "Edit Event"
            val editable = Editable.Factory.getInstance()
            binding.title.text = editable.newEditable(event!!.title)
            binding.dsc.text = editable.newEditable(event!!.description)
            if(event!!.timeFrame() != null){
                startTime = LocalTime.parse(event!!.timeStartString)
                endTime = LocalTime.parse(event!!.timeEndString)
            }
        }else{
            binding.eventTitle.text = "New Event"
        }
        eventViewModel = ViewModelProvider(activity)[EventViewModel::class.java]
        binding.saveButton.setOnClickListener {
            saveAction()
        }
        binding.selectTimeFrameButton.setOnClickListener {
            openTimePicker()
        }
    }

    private fun openTimePicker() {
        if (startTime == null || endTime == null){
            startTime = LocalTime.now()
            endTime = LocalTime.now()
        }
        val startListener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            startTime = LocalTime.of(selectedHour, selectedMinute)
        }
        val endListener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            endTime = LocalTime.of(selectedHour, selectedMinute)
        }
        val startDialog = TimePickerDialog(activity, startListener, startTime!!.hour, startTime!!.minute, true)
        startDialog.setTitle("Start Time")
        startDialog.show()
        val endDialog = TimePickerDialog(activity, endListener, endTime!!.hour, endTime!!.minute, true)
        endDialog.setTitle("End Time")
        endDialog.show()
    }


    private fun saveAction() {
        Log.i("save", "save")
        val dsc = binding.dsc.text.toString()
        val title = binding.title.text.toString()
        if(event == null){
            if (startTime != null){
                val newEvent = Event(title, Event.dateFormatter.format(date), dsc, Event.timeFormatter.format(startTime), Event.timeFormatter.format(endTime))
                eventViewModel.addEvent(newEvent)
            } else {
                val newEvent = Event(title, Event.dateFormatter.format(date), dsc, null, null)
                eventViewModel.addEvent(newEvent)
            }
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