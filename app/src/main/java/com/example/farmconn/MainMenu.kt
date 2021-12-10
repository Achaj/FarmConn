package com.example.farmconn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val imgBtnLocalize = findViewById<ImageButton>(R.id.imageButtonLocalization)
        imgBtnLocalize.setOnClickListener{
            val intent= Intent(this,MapsActivity::class.java)
            startActivity(intent)
        }
    }
}