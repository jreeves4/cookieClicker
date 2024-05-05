package com.example.cookieclicker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var game : Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statsButton : Button = findViewById(R.id.statsButton)
        val shopButton : Button = findViewById(R.id.shopButton)
        val imageView : ImageView = findViewById(R.id.cookie)
        val scoreView : TextView = findViewById(R.id.score)

        var sp : SharedPreferences = this.getSharedPreferences("cookieClicker", Context.MODE_PRIVATE)
        val cookies = sp.getInt("cookies", 0)
        val multiplier = sp.getInt("multiplier", 1)
        //Game(cookieCount)
        game = Game(cookies, multiplier)

        scoreView.text = game.getCookies().toString()

        //handle cookie image preference here
        imageView.setImageResource(R.drawable.umd_athletics_logo)

        imageView.setOnClickListener{
            game.clickCookie()
            val updatedCookies = game.getCookies()
            scoreView.text = updatedCookies.toString()
            sp.edit().putInt("cookies", updatedCookies).apply()
        }

        statsButton.setOnClickListener{
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }

        shopButton.setOnClickListener{
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }

    }
}