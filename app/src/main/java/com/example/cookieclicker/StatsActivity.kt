package com.example.cookieclicker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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

    override fun onStart(){
        super.onStart()
        val cookieStats = findViewById<TextView>(R.id.cookieStatsB)
        cookieStats.text = MainActivity.game.getCookies().toString()

        val cookieStats2 = findViewById<TextView>(R.id.cookieStatsD)
        cookieStats2.text = MainActivity.game.getMultiplier().toString()
    }
}