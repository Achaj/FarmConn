package com.example.farmconn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.farmconn.Objects.User


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
            val intent= Intent(this,MaschineActivity::class.java)
            startActivity(intent)
        }

        val btnWorksActivity=findViewById<Button>(R.id.btnWorkActivity)
        btnWorksActivity.setOnClickListener{
            val intent= Intent(this,WorksActivity::class.java)
            startActivity(intent)
        }
        val btnFieldActivity=findViewById<Button>(R.id.btnFieldsActivity)
        btnFieldActivity.setOnClickListener{
            val intent= Intent(this,FieldActivity::class.java)
            startActivity(intent)
        }
        var helperUser=HelperUser
        val user=helperUser.getCurrentUser()
        if (user != null) {
            Toast.makeText(applicationContext, "Witaj "+user.nameUser.toString(), Toast.LENGTH_LONG).show()
        }else{
            // Jak uzytkownik nie jest zalogowny to go usuwa
           //  val intent= Intent(this,MainActivity::class.java)
          //  startActivity(intent)
        }


    }



}