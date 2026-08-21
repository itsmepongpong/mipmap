package com.example.mipmap

import android.view.View
import android.widget.Button
import android.widget.TextView

/**
 * The slider, separated from the map entirely. This class only knows how
 * to slide a panel in/out from the right over a dimmed background - it has
 * no idea what a "building" is, and it doesn't touch the map's zoom.
 * Callers hook into onOpen/onClose if they want to react to the panel
 * opening or closing (e.g. to zoom a map).
 */
class Slider(
    rootView: View,
    private val animDuration: Long = 300L
) {

    private val panelView: View = rootView.findViewById(R.id.buildingPanel)
    private val scrimView: View = rootView.findViewById(R.id.scrim)
    private val panelTitle: TextView = rootView.findViewById(R.id.panelBuildingName)
    private val closeButton: Button = rootView.findViewById(R.id.btn_close_panel)

    private var panelWidthPx = 0

    var isOpen = false
        private set

    var onOpen: (() -> Unit)? = null
    var onClose: (() -> Unit)? = null

    init {
        // Start off-screen to the right until something opens the panel.
        panelView.post {
            panelWidthPx = panelView.width
            panelView.translationX = panelWidthPx.toFloat()
        }

        closeButton.setOnClickListener { close() }
        scrimView.setOnClickListener { close() }
    }

    fun open(title: String) {
        panelTitle.text = title
        isOpen = true

        scrimView.visibility = View.VISIBLE
        scrimView.alpha = 0f
        scrimView.animate().alpha(1f).setDuration(animDuration).start()
        panelView.animate().translationX(0f).setDuration(animDuration).start()

        onOpen?.invoke()
    }

    fun close() {
        isOpen = false

        panelView.animate()
            .translationX(panelWidthPx.toFloat())
            .setDuration(animDuration)
            .start()
        scrimView.animate()
            .alpha(0f)
            .setDuration(animDuration)
            .withEndAction { scrimView.visibility = View.GONE }
            .start()

        onClose?.invoke()
    }
}