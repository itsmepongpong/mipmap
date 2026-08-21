package com.example.mipmap

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

/**
 * Map screen. Only knows about the map and its buildings - the slider is
 * a separate, self-contained component (see SlayderPanel) that this class
 * just talks to through open()/close()/onOpen/onClose.
 */
class ITBuilding : AppCompatActivity() {

    private val zoomScale = 1.8f
    private val animDuration = 300L

    private lateinit var mapContent: RelativeLayout
    private lateinit var slayderPanel: Slider

    // Every building button paired with the label shown on its panel.
    private val buildings = listOf(
        R.id.btn_1a_block to "1A Building",
        R.id.btn_1b_block to "1B Building",
        R.id.btn_2a_block to "2A Building",
        R.id.btn_2b_block to "2B Building",
        R.id.btn_4_block to "Comp Lab",
        R.id.btn_3_block to "Internet Room",
        R.id.btn_faculty_block to "Faculty Building"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

        mapContent = findViewById(R.id.mapContent)

        slayderPanel = Slider(rootView = findViewById(android.R.id.content))
        slayderPanel.onClose = { resetZoom() }

        for ((id, label) in buildings) {
            findViewById<Button>(id).setOnClickListener { button ->
                zoomToBuilding(button)
                slayderPanel.open(label)
            }
        }
    }

    private fun zoomToBuilding(button: View) {
        // Zoom in, centered on the building that was tapped.
        mapContent.pivotX = button.left + button.width / 2f
        mapContent.pivotY = button.top + button.height / 2f
        mapContent.animate()
            .scaleX(zoomScale)
            .scaleY(zoomScale)
            .setDuration(animDuration)
            .start()
    }

    private fun resetZoom() {
        mapContent.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(animDuration)
            .start()
    }

    override fun onBackPressed() {
        if (slayderPanel.isOpen) {
            slayderPanel.close()
        } else {
            super.onBackPressed()
        }
    }
}