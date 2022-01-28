package com.example.farmconn

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.farmconn.Objects.Fields
import com.example.farmconn.Objects.Machine
import com.example.farmconn.Objects.User
import com.google.android.gms.location.LocationServices

class Maschine__Edit__Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maschine_edit)

        var maschineOLD= intent.getSerializableExtra("MACHICHINE") as Machine
        putData(maschineOLD)

        val showFieldInMapBTN =findViewById<ImageButton>(R.id.show_Button_AME)
        showFieldInMapBTN.setOnClickListener{
            val uri: String = java.lang.String.format("geo:<" + maschineOLD.xCords  + ">,<" + maschineOLD.yCords + ">?q=<" + maschineOLD.xCords  + ">,<" + maschineOLD.yCords + ">(" + maschineOLD.typeMachine + ")")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
        val dellData =findViewById<ImageButton>(R.id.dell_Button_AME)
        dellData.setOnClickListener{
            val databaseHandler= DatabaseHandler(this)
            val status=databaseHandler.delateMachine(maschineOLD)
            if(status > 0){
                Toast.makeText(applicationContext,"Usunięto Pola " + status.toString(), Toast.LENGTH_SHORT).show()

                val intent= Intent(this, ListMachineActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext,"coś poszło  nie tak "+ status.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        var x=0.0
        var y=0.0

        // Aktualizacja Danych
        val saveMachine= findViewById<ImageButton>(R.id.edit_ButtonAME)
        saveMachine.setOnClickListener{
            val brand:String
            brand= findViewById<EditText>(R.id.brand_editText_AME).text.toString()
            val model:String
            model= findViewById<EditText>(R.id.model_editText_AME).text.toString()
            val type:String
            type= findViewById<EditText>(R.id.type_editText_AME).text.toString()
            val capacityString:String
            capacityString= findViewById<EditText>(R.id.capacity_editText_AME).text.toString()
            var capacit=capacityString.toInt()
            val fuelString:String
            fuelString= findViewById<EditText>(R.id.fuel_editText_AME).text.toString()
            var fuele=fuelString.toInt()
            val weightString:String
            weightString= findViewById<EditText>(R.id.weight_editText_AME).text.toString()
            val weight=weightString.toInt()
            val widthString:String
            widthString= findViewById<EditText>(R.id.width_editText_AME).text.toString()
            val width=widthString.toInt()

            if(fuele==null){
                fuele=0
            }
            if(capacit==null){
                capacit=0
            }
            if (brand!=null && model !=null && type!=null && capacit!=null && fuele!=null && weight!=null && width!=null && x!=null && y!=null){
                // heleper get curent id farm to using in saving date
                val user: User? = HelperUser.getCurrentUser()
                if(user!= null ){
                    val idfarm= user.idFarm
                    // var machine=Machine(0,brand,model,type,capacit,fuele,weight,width,x,y, idfarm )
                }
               // var machine=Machine(maschineOLD.idMachine,brand,model,type,capacit,fuele,weight,width,x,y,1)
               // var machine=Machine(0,brand,model,type,capacit,fuele,weight,width,x,y, user.idFarm )
                //  Toast.makeText(applicationContext,machine.toString(),Toast.LENGTH_SHORT).show()

                maschineOLD.brandMachine=brand
                maschineOLD.modelMachine=model
                maschineOLD.typeMachine=type
                maschineOLD.capacityMachine=capacit
                maschineOLD.fuelUsageMachine=fuele
                maschineOLD.weightMachine=weight
                maschineOLD.widthMachine=width
                maschineOLD.xCords=x
                maschineOLD.yCords=y
                val databaseHandler: DatabaseHandler = DatabaseHandler(this)
                val status = databaseHandler.updateMachine(maschineOLD)
                if (status > 0) {
                    Toast.makeText(applicationContext, "Udało się zapisać dane", Toast.LENGTH_LONG).show()
                    clearEditText()
                    x=0.0
                    y=0.0
                    val intent= Intent(this, ListMachineActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        applicationContext, "Nie udało się zapisać danych", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(
                    applicationContext, "Nie Uzpełnionow szystkich danych", Toast.LENGTH_LONG).show()
            }

        }
        // Lokalizacja użytkownika w danym momencie
        var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        findViewById<Button>(R.id.getLocalize_Butto_AME).setOnClickListener{
            val task = fusedLocationProviderClient.lastLocation

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),101)
                //Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
            }
            task.addOnSuccessListener {
                if(it != null){
                    Toast.makeText(applicationContext,"${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()
                    x=it.latitude
                    y=it.latitude
                    //findViewById<TextView>(R.id.xCordinatsEtitableText).setText("${it.latitude}");
                    // findViewById<TextView>(R.id.yCordinatsEtitableText).setText("${it.longitude}");
                }
            }
        }
    }
    fun clearEditText(){
        findViewById<EditText>(R.id.brand_editText_AME).text.clear()
        findViewById<EditText>(R.id.model_editText_AME).text.clear()
        findViewById<EditText>(R.id.type_editText_AME).text.clear()
        findViewById<EditText>(R.id.capacity_editText_AME).text.clear()
        findViewById<EditText>(R.id.fuel_editText_AME).text.clear()
        findViewById<EditText>(R.id.weight_editText_AME).text.clear()
        findViewById<EditText>(R.id.width_editText_AME).text.clear()
    }
    private fun putData(machine: Machine?) {

        if (machine != null) {
            findViewById<EditText>(R.id.brand_editText_AME).text=   Editable.Factory.getInstance().newEditable(machine.brandMachine)
            findViewById<EditText>(R.id.model_editText_AME).text=   Editable.Factory.getInstance().newEditable(machine.modelMachine)
            findViewById<EditText>(R.id.type_editText_AME).text=    Editable.Factory.getInstance().newEditable(machine.typeMachine)
            findViewById<EditText>(R.id.capacity_editText_AME).text=Editable.Factory.getInstance().newEditable(machine.capacityMachine.toString())
            findViewById<EditText>(R.id.fuel_editText_AME).text=    Editable.Factory.getInstance().newEditable(machine.fuelUsageMachine.toString())
            findViewById<EditText>(R.id.weight_editText_AME).text=  Editable.Factory.getInstance().newEditable(machine.weightMachine.toString())
            findViewById<EditText>(R.id.width_editText_AME).text=   Editable.Factory.getInstance().newEditable(machine.widthMachine.toString())
        }
    }
}