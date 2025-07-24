package com.sb.seekbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toDrawable

class LightToneSeekBar : AppCompatSeekBar, OnSeekBarChangeListener {

    private var seekBarChangeListener : OnSeekBarChangeListener? = null
    private var startColor = ContextCompat.getColor(context, R.color.warm)
    private var endColor = ContextCompat.getColor(context, R.color.white)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initSeekBar()
    }

    constructor(context: Context) : super(context) {
        initSeekBar()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        initSeekBar()
    }

    private fun initSeekBar() {
        setOnSeekBarChangeListener(this)
        post {
            onProgressChanged(this, progress, false)
        }
    }


    private fun getThumb(context: Context, progress: Int): Drawable {
        val thumbView = LayoutInflater.from(context).inflate(R.layout.thumb, null, false)
        thumbView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        val markerView = thumbView.findViewById<ImageView>(R.id.iv_marker)
        markerView.setColorFilter(
            getMarkerColor(progress, max),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        val bitmap = createBitmap(markerView.measuredWidth, markerView.measuredHeight)
        val canvas = Canvas(bitmap)
        thumbView.layout(0, 0, markerView.measuredWidth, markerView.measuredHeight)
        thumbView.draw(canvas)
        return bitmap.toDrawable(resources)
    }

    fun setMarkerColour(startColourRes: Int, endColourRes: Int) {
        startColor = ContextCompat.getColor(context, startColourRes)
        endColor = ContextCompat.getColor(context, endColourRes)
    }

    private fun getMarkerColor(progress: Int, max: Int): Int {
        val ratio = progress.toFloat() / max
        val r = ((1 - ratio) * ((startColor shr 16) and 0xFF) + ratio * ((endColor shr 16) and
                0xFF)).toInt()
        val g = ((1 - ratio) * ((startColor shr 8) and 0xFF) + ratio * ((endColor shr 8) and 0xFF)).toInt()
        val b = ((1 - ratio) * (startColor and 0xFF) + ratio * (endColor and 0xFF)).toInt()
        return (0xFF shl 24) or (r shl 16) or (g shl 8) or b
    }


    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
        seekBar.thumb = getThumb(context, progress)
        seekBarChangeListener?.onProgressChanged(seekBar, i, b)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        seekBarChangeListener?.onStartTrackingTouch(seekBar)
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        seekBarChangeListener?.onStopTrackingTouch(seekBar)
    }

    override fun setOnSeekBarChangeListener(listener: OnSeekBarChangeListener?) {
        super.setOnSeekBarChangeListener(this)
        if(listener != this) {
            seekBarChangeListener = listener
        }
    }
}