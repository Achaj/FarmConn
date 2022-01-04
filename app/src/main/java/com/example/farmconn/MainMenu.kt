package com.example.farmconn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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
        val btnMaschineActivity = findViewById<Button>(R.id.btnMachineActivitiy)
        btnMaschineActivity.setOnClickListener{
            val intent= Intent(this,MachineryMainActivity::class.java)
            startActivity(intent)
        }

        val btnWorksActivity=findViewById<Button>(R.id.btnWorkActivity)
        btnWorksActivity.setOnClickListener{
            val intent= Intent(this,WorksActivity::class.java)
            startActivity(intent)
        }



    }



}