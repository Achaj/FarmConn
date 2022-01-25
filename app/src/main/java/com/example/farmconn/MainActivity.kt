package com.example.farmconn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button= findViewById<Button>(R.id.buttonLogin)
            button.setOnClickListener{
                val intent=Intent(this,MainMenu::class.java)
                startActivity(intent)
            }
        val singUpBTN= findViewById<Button>(R.id.signInBTN)
            singUpBTN.setOnClickListener {
                val intent =Intent(this, CrateNewUserActivity::class.java)
                startActivity(intent)
            }
   //    change password antivity
   //    val changePassBTN= findViewById<Button>(R.id.changePassActivityBTN)
   //    changePassBTN.setOnClickListener {
   //        val intent =Intent(this, ResetPasswordActivity::class.java)
   //        startActivity(intent)
   //    }

    }
}