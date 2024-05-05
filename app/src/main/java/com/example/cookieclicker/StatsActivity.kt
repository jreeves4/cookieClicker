package com.example.cookieclicker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StatsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val homeButton : Button = findViewById(R.id.homeButton)
        homeButton.setOnClickListener{
            finish()
        }
    }
}