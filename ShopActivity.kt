package com.example.cookieclicker

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ShopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val homeButton : Button = findViewById(R.id.homeButton)
        homeButton.setOnClickListener{
            finish()
        }
    }
}