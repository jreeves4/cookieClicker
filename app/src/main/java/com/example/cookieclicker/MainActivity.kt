package com.example.cookieclicker

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var adView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statsButton : Button = findViewById(R.id.statsButton)
        val shopButton : Button = findViewById(R.id.shopButton)
        val imageView : ImageView = findViewById(R.id.cookie)
        val scoreView : TextView = findViewById(R.id.score)
        val jackpotText : TextView = findViewById(R.id.jackpot)
        val progressBar : ProgressBar = findViewById(R.id.progressBar)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        var progress : Int = 0
        var jackpot : Boolean = false

        var sp : SharedPreferences = this.getSharedPreferences("cookieClicker", Context.MODE_PRIVATE)
        val cookies = sp.getInt("cookies", 0)
        val multiplier = sp.getInt("multiplier", 1)
        //Game(cookieCount)
        game = Game(cookies, multiplier)

        var firebase : FirebaseDatabase = FirebaseDatabase.getInstance()
        var reference : DatabaseReference = firebase.getReference("upgrades")

        var exampleUpgrades : Map<String, Int> = mapOf(UpgradeType.SECOND_HAND.name to 3,
            UpgradeType.GRANDMAS_HOUSE.name to 0)
        reference.setValue( exampleUpgrades )

        reference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val data = task.result
                if (data != null) {
                    val valueMap = data.value as Map<UpgradeType, Int>
                    game.setUpgrades(valueMap)
                    Log.w("MainActivity", game.getUpgrades().toString())
                }
            }
        }

        scoreView.text = game.getCookies().toString()

        //handle cookie image preference here
        var imgPref = sp.getInt("imgPref", 0)
        if (imgPref == 0) {
            imageView.setImageResource(R.drawable.umd_athletics_logo)
        } else if (imgPref == 1) {
            imageView.setImageResource(R.drawable.smiley_face)
        } else if (imgPref == 2) {
            imageView.setImageResource(R.drawable.herve_img)
        } else if (imgPref == 3) {
            imageView.setImageResource(R.drawable.coin_img)
        } else if (imgPref == 4) {
            imageView.setImageResource(R.drawable.nap_img)
        }

        imageView.setOnClickListener{
            game.clickCookie()
            val updatedCookies = game.getCookies()
            scoreView.text = updatedCookies.toString()
            sp.edit().putInt("cookies", updatedCookies).apply()

            // progress bar
            if (jackpot) {
                progress = -2
                jackpotText.setText("JACKPOT!")
                imageView.clearAnimation()
                jackpot = false
            }
            else {
                jackpotText.setText("")
            }

            progress += 2
            if (progress >= 100) {
                progress = 100
                game.jackpot()
                val shake = AnimationUtils
                    .loadAnimation(this, R.drawable.shake_animation)
                imageView.startAnimation(shake)
                jackpot = true
            }
            progressBar.progress = progress

        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            // Check which RadioButton is selected
            when (checkedId) {
                R.id.rd_umd -> {
                    imgPref = 0
                    imageView.setImageResource(R.drawable.umd_athletics_logo)
                    sp.edit().putInt("imgPref", imgPref).apply()
                }

                R.id.rd_smiley -> {
                    imgPref = 1
                    imageView.setImageResource(R.drawable.smiley_face)
                    sp.edit().putInt("imgPref", imgPref).apply()
                }

                R.id.rd_herve -> {
                    imgPref = 2
                    imageView.setImageResource(R.drawable.herve_img)
                    sp.edit().putInt("imgPref", imgPref).apply()
                }

                R.id.rd_coin -> {
                    imgPref = 3
                    imageView.setImageResource(R.drawable.coin_img)
                    sp.edit().putInt("imgPref", imgPref).apply()
                }

                R.id.rd_boot -> {
                    imgPref = 4
                    imageView.setImageResource(R.drawable.nap_img)
                    sp.edit().putInt("imgPref", imgPref).apply()
                }
            }
        }

        statsButton.setOnClickListener{
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }

        shopButton.setOnClickListener{
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }

        // create an AdView
        adView = AdView( this )
        var adSize : AdSize = AdSize( AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT )
        adView.setAdSize( adSize )
        var adUnitId : String = "ca-app-pub-3940256099942544/6300978111"
        adView.adUnitId = adUnitId
        // create an AdRequest
        var builder : AdRequest.Builder = AdRequest.Builder( )
        builder.addKeyword( "cookie" )
        var request : AdRequest = builder.build()
        // put the AdView in the LinearLayout
        var adLayout : LinearLayout = findViewById( R.id.ad_view )
        adLayout.addView( adView )
        // load the ad
        adView.loadAd( request )
    }

    companion object {
        lateinit var game : Game
    }
}

