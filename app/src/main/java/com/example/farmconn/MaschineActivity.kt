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
import com.example.farmconn.Objects.Machine
import com.example.farmconn.Objects.User
import com.google.android.gms.location.LocationServices

class MaschineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maschine)

        if (HelperUser.getCurrentUser() == null) {
            //Jak uzytkownik nie jest zalogowny to go usuwa
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        var x: Double = 0.0
        var y: Double = 0.0

        val btnMachileListActivity = findViewById<Button>(R.id.listMaschine_Button_AM)
        btnMachileListActivity.setOnClickListener {
            val intent = Intent(this, ListMachineActivity::class.java)
            startActivity(intent)
        }
        val backBTN = findViewById<Button>(R.id.back_Button_AM)
        backBTN.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }


        val saveMachine = findViewById<Button>(R.id.addMaschine_Button_AM)
        saveMachine.setOnClickListener {
            val brand: String
            brand = findViewById<EditText>(R.id.brand_editText_AM).text.toString()
            val model: String
            model = findViewById<EditText>(R.id.model_editText_AM).text.toString()
            val type: String
            type = findViewById<EditText>(R.id.type_editText_AM).text.toString()
            val capacityString: String
            capacityString = findViewById<EditText>(R.id.capacity_editText_AM).text.toString()
            var capacit = 0
            try {
                capacit = capacityString.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    applicationContext,
                    "zostanie wstawione 0",
                    Toast.LENGTH_LONG
                ).show()
            }

            val fuelString: String
            fuelString = findViewById<EditText>(R.id.fuel_editText_AM).text.toString()
            var fuele = 0
            try {
                fuele = fuelString.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, " zostanie wstawione 0", Toast.LENGTH_LONG)
                    .show()
            }

            val weightString: String
            weightString = findViewById<EditText>(R.id.weight_editText_AM).text.toString()
            var weight = 0
            try {
                weight = weightString.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "zostanie wstawione 0", Toast.LENGTH_LONG).show()
            }

            val widthString: String
            widthString = findViewById<EditText>(R.id.width_editText_AM).text.toString()
            var width = 0

            try {
                width = widthString.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, " zostanie wstawione 0", Toast.LENGTH_LONG)
                    .show()
            }


            if (brand != null && model != null && type != null && capacit != null && fuele != null && weight != null && width != null && x != null && y != null) {
                // heleper get curent id farm to using in saving date
                val user: User? = HelperUser.getCurrentUser()
                if (user != null) {
                    val idfarm = user.idFarm

                    var machine =
                        Machine(0, brand, model, type, capacit, fuele, weight, width, x, y, idfarm)
                    //  Toast.makeText(applicationContext,machine.toString(),Toast.LENGTH_SHORT).show()

                    val databaseHandler: DatabaseHandler = DatabaseHandler(this)
                    val status = databaseHandler.addMachine(machine)
                    if (status > -1) {
                        Toast.makeText(
                            applicationContext,
                            "Udało się zapisać dane",
                            Toast.LENGTH_LONG
                        ).show()
                        clearEditText()
                        x = 0.0
                        y = 0.0

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
        var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        findViewById<Button>(R.id.getLocalize_Butto_AM).setOnClickListener {
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
                    x = it.latitude
                    y = it.longitude
                    //findViewById<TextView>(R.id.xCordinatsEtitableText).setText("${it.latitude}");
                    // findViewById<TextView>(R.id.yCordinatsEtitableText).setText("${it.longitude}");
                }
            }
        }
    }

    fun clearEditText() {
        findViewById<EditText>(R.id.brand_editText_AM).text.clear()
        findViewById<EditText>(R.id.model_editText_AM).text.clear()
        findViewById<EditText>(R.id.type_editText_AM).text.clear()
        findViewById<EditText>(R.id.capacity_editText_AM).text.clear()
        findViewById<EditText>(R.id.fuel_editText_AM).text.clear()
        findViewById<EditText>(R.id.weight_editText_AM).text.clear()
        findViewById<EditText>(R.id.width_editText_AM).text.clear()
    }
}