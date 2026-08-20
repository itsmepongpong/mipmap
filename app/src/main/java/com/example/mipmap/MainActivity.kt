package com.example.mipmap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Force the app to open your custom 2D ITBuilding map screen instantly
        val intent = Intent(this, ITBuilding::class.java)
        startActivity(intent)
        finish()
    }
}