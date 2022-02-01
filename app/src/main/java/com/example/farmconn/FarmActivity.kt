package com.example.farmconn

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.farmconn.ListAdapters.MyListAdapterFarm
import com.example.farmconn.ListAdapters.MyListAdapterView_FieldAtWork
import com.example.farmconn.Objects.Farm
import com.example.farmconn.Objects.Fields
import com.example.farmconn.Objects.Machine
import com.example.farmconn.Objects.User
import com.example.farmconn.WorksActivity.Companion.user
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class FarmActivity : AppCompatActivity() {
    companion object {
        var farm: Farm? = null

        @JvmName("setFarm1")
        fun setFarm(farm: Farm) {
            this.farm = farm
        }

        fun dellFarm(farm: Farm) {
            this.farm = null
        }
    }

    val dbHandler = DatabaseHandler(this)

    val helperUser = HelperUser
    var x: Double? = null
    var y: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm)
        var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        var tmpFarm: Farm? = null

        tmpFarm = helperUser.getCurrentUser()?.let { dbHandler.getOneFarmByID(it?.idFarm) }
        if (tmpFarm != null && farm == null) {
            putData(tmpFarm)
            farm = tmpFarm
        }
        val listView = findViewById<ListView>(R.id.farm_LISTVIEW)
        //inicjalize som doata tu use
        var farmArrayList: List<Farm>? = null
        if (farmArrayList != null) {
            farmArrayList = getData()
        } else {
            farmArrayList = null
            farmArrayList = getData()
        }
        // My custom adpter
        if (farmArrayList != null) {
            val myAdapter = MyListAdapterFarm(this, R.layout.list_farm_items, farmArrayList)
            listView.adapter = myAdapter
            listView.setOnItemClickListener() { adapterView, view, position, id ->
                putData(farmArrayList!![position])
                tmpFarm = farmArrayList[position]
                setFarm(farmArrayList[position])
            }

        }
        // Lokalizacja użytkownika w danym momencie

        findViewById<Button>(R.id.newLocalize_Button_AF).setOnClickListener {
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
                    //Toast.makeText(applicationContext,"${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()

                    findViewById<EditText>(R.id.x_EditText_AF).setText("${it.latitude}");
                    findViewById<EditText>(R.id.y_EditText_AF).setText("${it.longitude}");
                    if (farm != null) {
                        farm!!.xCord = it.latitude
                        farm!!.yCord = it.longitude
                        if (dbHandler.updateFarm(farm!!) > -1) {
                            Toast.makeText(
                                applicationContext,
                                "Udało się Zaktualizować  dane " + farm!!.nameFarm,
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(this, FarmActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Coś poszło nie tak ",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }
            }
        }

        val edit_Button_AF = findViewById<ImageButton>(R.id.edit_Button_AF)
        edit_Button_AF.setOnClickListener {
            var name: String? = null
            name = findViewById<EditText>(R.id.name_EditText_AF).text.toString().trim()
            var xC: String? = null
            xC = findViewById<EditText>(R.id.x_EditText_AF).text.toString().trim()
            var xD: Double? = null
            try {
                xD = xC.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    applicationContext,
                    "Błąd konwersji zamień , na .",
                    Toast.LENGTH_LONG
                ).show()
            }
            var yC: String? = null
            yC = findViewById<EditText>(R.id.y_EditText_AF).text.toString().trim()
            var yD: Double? = null
            try {
                yD = yC.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    applicationContext,
                    "Błąd konwersji zamień , na .",
                    Toast.LENGTH_LONG
                ).show()
            }


            if (name != null && farm != null && xD != null && yD != null) {
                farm!!.nameFarm = name
                farm!!.yCord = xD
                farm!!.yCord = yD
                //var latLng= getCurrentLocation()
                //farm!!.yCord=latLng.latitude
                //farm!!.yCord=latLng.latitude
                if (dbHandler.updateFarm(farm!!) > -1) {
                    Toast.makeText(
                        applicationContext,
                        "Udało się Zaktualizować  dane " + farm!!.nameFarm,
                        Toast.LENGTH_LONG
                    ).show()
                    farm = null
                    tmpFarm = null
                    val intent = Intent(this, FarmActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Coś poszło nie tak ", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "Brak nazyw Farmy ", Toast.LENGTH_LONG)
                    .show()
            }
        }
        val showFieldInMapBTN = findViewById<ImageButton>(R.id.show_Button_AF)
        showFieldInMapBTN.setOnClickListener {
            val uri: String = java.lang.String.format(
                "geo:<" + farm!!.xCord + ">,<" + farm!!.yCord + ">?q=<" + farm!!.xCord + ">,<" + farm!!.yCord + ">" +
                        "(" + farm!!.nameFarm + ")"
            )
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
        val newFarmOnUser_Button_AF = findViewById<Button>(R.id.newFarmOnUser_Button_AF)
        newFarmOnUser_Button_AF.setOnClickListener {
            var user = helperUser.getCurrentUser()
            if (user != null) {
                user.idFarm = farm!!.idFarm
                if (dbHandler.updateUser(user) > -1) {
                    Toast.makeText(
                        applicationContext,
                        "Twoje nowa farma to  " + farm!!.nameFarm,
                        Toast.LENGTH_LONG
                    )
                        .show()
                    farm = null
                    tmpFarm = null
                    val intent = Intent(this, FarmActivity::class.java)
                    startActivity(intent)
                }
            }

        }
        val newFarm_Button_AF = findViewById<Button>(R.id.newFarm_Button_AF)
        newFarm_Button_AF.setOnClickListener {
            var name: String? = null
            name = findViewById<EditText>(R.id.name_EditText_AF).text.toString().trim()
            var xC: String? = null
            xC = findViewById<EditText>(R.id.x_EditText_AF).text.toString().trim()
            var xD: Double? = null
            try {
                xD = xC!!.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    applicationContext,
                    "Błąd konwersji zamień , na .",
                    Toast.LENGTH_LONG
                ).show()
            }
            var yC: String? = null
            yC = findViewById<EditText>(R.id.y_EditText_AF).text.toString().trim()
            var yD: Double? = null
            try {
                yD = yC!!.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    applicationContext,
                    "Błąd konwersji zamień , na .",
                    Toast.LENGTH_LONG
                ).show()
            }
            if (name != null && xD != null && yD != null) {
                var newFarm: Farm = Farm(0, name!!, xD!!, yD!!)
                if (dbHandler.addFarm(newFarm) > -1) {
                    Toast.makeText(
                        applicationContext,
                        "Zapisano  farme   " + newFarm.nameFarm,
                        Toast.LENGTH_LONG
                    )
                        .show()
                    farm = null
                    tmpFarm = null
                    val intent = Intent(this, FarmActivity::class.java)
                    startActivity(intent)
                }
            }

        }

        val back = findViewById<Button>(R.id.back_Button_AF)
        back.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
        val dell = findViewById<ImageButton>(R.id.dell_Button_AF)
        dell.setOnClickListener {
            if (!helperUser.getCurrentUser()?.typeUser.equals("admin")) {
                Toast.makeText(
                    applicationContext,
                    "Nie masz uprawnie do tego",
                    Toast.LENGTH_LONG
                )
            } else {
                val builder = AlertDialog.Builder(this)

                builder.setTitle("UWAGA")
                builder.setMessage("Czy jesteś pewny że chcesz usunąć Farme ?")
                builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                    if (dbHandler.delateFarm(farm!!.idFarm) > 0) {
                        Toast.makeText(
                            applicationContext,
                            "Usnięto Farme",
                            Toast.LENGTH_LONG
                        ).show()
                        farm = null
                        tmpFarm = null
                        val intent = Intent(this, FarmActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "coś nie działa",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                    Toast.makeText(applicationContext, "Anulowano Usuwanie", Toast.LENGTH_SHORT)
                        .show()
                }
                builder.show()
            }
        }

    }

    fun putData(farm: Farm) {
        Log.d(
            "FARM",
            "--" + farm.idFarm + "-" + farm.nameFarm + farm.xCord.toString() + farm.yCord.toString()
        )
        findViewById<EditText>(R.id.name_EditText_AF).text =
            Editable.Factory.getInstance().newEditable(farm.nameFarm)
        findViewById<EditText>(R.id.x_EditText_AF).text =
            Editable.Factory.getInstance().newEditable(farm.xCord.toString())
        findViewById<EditText>(R.id.y_EditText_AF).text =
            Editable.Factory.getInstance().newEditable(farm.yCord.toString())
    }

    fun getData(): List<Farm>? {

        return dbHandler.getAllFarms()
    }

    fun clearData(): List<Farm>? {
        return null
    }

    fun getCurrentLocation(): LatLng {
        var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val task = fusedLocationProviderClient.lastLocation
        var latLng: LatLng = LatLng(0.0, 0.0)
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
                //Toast.makeText(applicationContext,"${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()
                findViewById<EditText>(R.id.x_EditText_AF).setText("${it.latitude}");
                findViewById<EditText>(R.id.y_EditText_AF).setText("${it.longitude}");
                latLng = LatLng(it.latitude, it.longitude)
            }
        }
        return latLng
    }
}