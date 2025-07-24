package com.sb.seekbar

import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : ComponentActivity() {
    private lateinit var seekbar : LightToneSeekBar
    private lateinit var themeRadioGroup: RadioGroup
    private lateinit var mainLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekbar = findViewById(R.id.sb_light_tone)
        // seekbar.setMarkerColour(R.color.black, R.color.white)

        themeRadioGroup = findViewById(R.id.rg_theme)
        mainLayout = findViewById(R.id.main_layout)
        themeRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {
                R.id.rb_light_theme -> {
                    mainLayout.setBackgroundColor(getColor(R.color.white))
                }
                R.id.rb_dark_theme -> {
                    mainLayout.setBackgroundColor(getColor(R.color.black))
                }
            }
        }
    }
}