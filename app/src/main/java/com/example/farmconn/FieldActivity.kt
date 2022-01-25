package com.example.farmconn

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class FieldActivity : AppCompatActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field)

        val btnFieldActivity=findViewById<Button>(R.id.addFieldActivityActivityFieldsBTN)
        btnFieldActivity.setOnClickListener{
            val intent= Intent(this,FieldActivity::class.java)
            startActivity(intent)
        }


        val list_field_activityBTN= findViewById<Button>(R.id.list_field_activity)
        list_field_activityBTN.setOnClickListener{
            val intent= Intent(this,ListFieldsActivity::class.java)
            startActivity(intent)
        }

    // Lokalizacja użytkownika w danym momencie
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        findViewById<Button>(R.id.getCurrentLocalizationBTN).setOnClickListener{
            val task = fusedLocationProviderClient.lastLocation

            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)
                //Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
            }
            task.addOnSuccessListener {
                if(it != null){
                    Toast.makeText(applicationContext,"${it.latitude} ${it.longitude}",Toast.LENGTH_SHORT).show()
                    findViewById<TextView>(R.id.xCordinatsEtitableText).setText("${it.latitude}");
                    findViewById<TextView>(R.id.yCordinatsEtitableText).setText("${it.longitude}");
                }
            }
        }
    }
    private fun fetchLocation() {
    }
}