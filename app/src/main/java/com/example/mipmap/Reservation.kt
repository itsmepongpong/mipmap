package com.example.classroomreservation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.classroomreservation.data.AppDatabase
import com.example.classroomreservation.data.Reservation
import com.example.classroomreservation.databinding.ActivityMakeReservationBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class MakeReservation : AppCompatActivity() {
    private lateinit var binding: ActivityMakeReservationBinding
    private lateinit var db: AppDatabase
    private var userId: Int = 1 // Default for testing since Login is removed
    private var userName: String = "User"
    private var selectedRoomId: Int = 0
    private var selectedDate: String = ""
    private var selectedStartTime: String = ""
    private var selectedEndTime: String = ""
    private var roomIds = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra("USER_ID", 1)
        userName = intent.getStringExtra("USER_NAME") ?: "User"
        db = AppDatabase.getDatabase(this)

        binding.toolbar.setNavigationOnClickListener { finish() }
        loadRooms()

        binding.etDate.setOnClickListener { showDatePicker() }
        binding.etStartTime.setOnClickListener { showTimePicker(true) }
        binding.etEndTime.setOnClickListener { showTimePicker(false) }
        binding.btnReserve.setOnClickListener { makeReservation() }
    }

    private fun loadRooms() {
        lifecycleScope.launch {
            val rooms = db.appDao().getAllClassrooms().first()
            val roomNames = mutableListOf<String>()
            roomIds.clear()
            for (room in rooms) {
                roomNames.add("${room.name} - ${room.building}")
                roomIds.add(room.id)
            }
            val adapter = ArrayAdapter(this@MakeReservation, android.R.layout.simple_dropdown_item_1line, roomNames)
            binding.spinnerRoom.setAdapter(adapter)
            binding.spinnerRoom.setOnItemClickListener { _, _, position, _ -> selectedRoomId = roomIds[position] }
        }
    }
    
    }

    private fun showTimePicker(isStartTime: Boolean) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(this, { _, hour, minute ->
            val time = String.format("%02d:%02d", hour, minute)
            if (isStartTime) { selectedStartTime = time; binding.etStartTime.setText(time) } 
            else { selectedEndTime = time; binding.etEndTime.setText(time) }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
    }

    private fun makeReservation() {
        val purpose = binding.etPurpose.text.toString().trim()
        if (selectedRoomId == 0 || selectedDate.isEmpty() || selectedStartTime.isEmpty() || selectedEndTime.isEmpty() || purpose.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val classroom = db.appDao().getClassroomById(selectedRoomId)
            val reservation = Reservation(
                userId = userId, classroomId = selectedRoomId, date = selectedDate,
                startTime = selectedStartTime, endTime = selectedEndTime, purpose = purpose,
                status = "pending", userName = userName, classroomName = classroom?.name ?: ""
            )
            db.appDao().insertReservation(reservation)
            Toast.makeText(this@MakeReservation, "Reservation submitted!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}