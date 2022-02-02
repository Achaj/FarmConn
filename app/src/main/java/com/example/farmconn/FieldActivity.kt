package com.example.farmconn

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.farmconn.Objects.Fields
import com.example.farmconn.Objects.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class FieldActivity : AppCompatActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field)
        if (HelperUser.getCurrentUser() == null) {
            //Jak uzytkownik nie jest zalogowny to go usuwa
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val btnFieldActivity = findViewById<Button>(R.id.addFieldActivityActivityFieldsBTN)
        btnFieldActivity.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }


        val list_field_activityBTN = findViewById<Button>(R.id.list_field_activity)
        list_field_activityBTN.setOnClickListener {
            val intent = Intent(this, ListFieldsActivity::class.java)
            startActivity(intent)
        }
        //== Zapisywanie pola do bazy ==//
        val savefieldInDbBTN = findViewById<Button>(R.id.saveFieldInDbBTN)
        savefieldInDbBTN.setOnClickListener {

            val name: String
            name = findViewById<EditText>(R.id.editText_NameFieldActivity).getText().toString()
            val desc: String
            desc = findViewById<EditText>(R.id.editTextMultiLine_DESC_FieldActivity).getText()
                .toString()
            val x: Double
            x = findViewById<EditText>(R.id.xCordinatsEtitableText).getText().toString().toDouble()
            val y: Double
            y = findViewById<EditText>(R.id.yCordinatsEtitableText).getText().toString().toDouble()

            if (name != null && desc != null && x != null && y != null) {
                val user: User? = HelperUser.getCurrentUser()
                if (user != null) {
                    val idfarm = user.idFarm

                    val field = Fields(0, name, desc, x, y, idfarm)
                    //Toast.makeText(applicationContext,field.toString(),Toast.LENGTH_SHORT).show()
                    val databaseHandler: DatabaseHandler = DatabaseHandler(this)
                    val status = databaseHandler.addField(field)
                    if (status > -1) {
                        Toast.makeText(
                            applicationContext,
                            "Udało się zapisać dane",
                            Toast.LENGTH_LONG
                        )
                            .show()

                    } else {
                        Toast.makeText(
                            applicationContext, "Nie udało się zapisać danych", Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    applicationContext, "Nie Uzpełnionow szystkich danych", Toast.LENGTH_LONG
                ).show()
            }
        }

        // Lokalizacja użytkownika w danym momencie
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        findViewById<Button>(R.id.getCurrentLocalizationBTN).setOnClickListener {
            val task = fusedLocationProviderClient.lastLocation

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )
                //Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
            }
            task.addOnSuccessListener {
                if (it != null) {
                    Toast.makeText(
                        applicationContext,
                        "${it.latitude} ${it.longitude}",
                        Toast.LENGTH_SHORT
                    ).show()
                    findViewById<TextView>(R.id.xCordinatsEtitableText).setText("${it.latitude}");
                    findViewById<TextView>(R.id.yCordinatsEtitableText).setText("${it.longitude}");
                }
            }
        }


    }

}