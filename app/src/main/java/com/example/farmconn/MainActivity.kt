package com.example.farmconn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.farmconn.Objects.Farm
import java.math.BigInteger
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.buttonLogin)
        button.setOnClickListener {
        //    val intent = Intent(this, MainMenu::class.java)
         //   startActivity(intent)

            //========Logowanie===============//
           var email:String
           email=findViewById<EditText>(R.id.email_EditText_AM).getText().toString()
           var pass:String
           pass=findViewById<EditText>(R.id.pass_EditText_AM).getText().toString()
           pass=md5(pass)
           val databaseHandler: DatabaseHandler = DatabaseHandler(this)
           var status=databaseHandler.viewUser(email,pass)
           if (status != null) {
               Toast.makeText(applicationContext, "Witaj "+status.nameUser, Toast.LENGTH_LONG).show()
               var helper= HelperUser.setCurrentUser(status)
               val intent = Intent(this, MainMenu::class.java)
               startActivity(intent)

           } else {
               Toast.makeText(
                   applicationContext,
                   "Nieprawidłowe hasło lub login",
                   Toast.LENGTH_LONG
               ).show()
           }




        }
        val singUpBTN = findViewById<Button>(R.id.signInBTN)
        singUpBTN.setOnClickListener {
            val intent = Intent(this, CrateNewUserActivity::class.java)
            startActivity(intent)



        }
        //    change password antivity
        //    val changePassBTN= findViewById<Button>(R.id.changePassActivityBTN)
        //    changePassBTN.setOnClickListener {
        //        val intent =Intent(this, ResetPasswordActivity::class.java)
        //        startActivity(intent)
        //    }

    //    val databaseHandler: DatabaseHandler = DatabaseHandler(this)
    //    val status = databaseHandler.addFarm(Farm(0, "PGR", 0.0, 0.0))
    //    if (status > -1) {
    //        Toast.makeText(applicationContext, "record save", Toast.LENGTH_LONG).show()
//
    //    } else {
    //        Toast.makeText(
    //            applicationContext,
    //            "nie działa",
    //            Toast.LENGTH_LONG
    //        ).show()
    //    }



    }
    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}