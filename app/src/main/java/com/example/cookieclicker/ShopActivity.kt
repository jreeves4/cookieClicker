package com.example.cookieclicker

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.log

class ShopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val homeButton : Button = findViewById(R.id.homeButton)
        homeButton.setOnClickListener{
            finish()
        }

        findViewById<Button>(R.id.twoXButton).setOnClickListener{
            MainActivity.game.buyTwoX()
        }

        findViewById<Button>(R.id.threeXButton).setOnClickListener{
            MainActivity.game.buyThreeX()
        }
    }
}