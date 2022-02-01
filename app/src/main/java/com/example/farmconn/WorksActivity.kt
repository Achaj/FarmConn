package com.example.farmconn

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.farmconn.Objects.Fields
import com.example.farmconn.Objects.Machine
import com.example.farmconn.Objects.User
import com.example.farmconn.Objects.Work
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WorksActivity : AppCompatActivity() {
    companion object {
        var macchineList: ArrayList<Machine> = ArrayList()
        var fields:Fields?=null
        var user:User?=null

        fun addMachine(machine: Machine){
            macchineList.add(machine)
        }
        fun remoweMachine(machine: Machine){
            macchineList.remove(machine)
        }
        fun cleanMachine(){
            this.macchineList.clear()
        }
        fun setField(fields: Fields){
            this.fields=fields
        }
        fun remoweField(){
            this.fields=null
        }
        fun addUser(user: User){
            this.user=user
        }
        fun remoweUser(){
            this.user=null
        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_works)



        val btnList_Work_Activity=findViewById<Button>(R.id.listWork_Button_AW)
        btnList_Work_Activity.setOnClickListener{
            val intent = Intent(this, List_Work_Activity::class.java)
            startActivity(intent)
        }
        val btnback=findViewById<Button>(R.id.back_Button_AW)
        btnback.setOnClickListener{
            val intent= Intent(this,MainMenu::class.java)
            startActivity(intent)
        }
        val choseMachineBTN=findViewById<Button>(R.id.choseMachine_Button_AW)
        choseMachineBTN.setOnClickListener{
            val intent= Intent(this, List_Work_Machine_ADD_activity::class.java)
            startActivity(intent)
        }
        val choseFieldBTN=findViewById<Button>(R.id.choseField_Button_AW)
        choseFieldBTN.setOnClickListener{
            val intent= Intent(this,List_Work_Field_ADD_activity::class.java)
            startActivity(intent)
        }
        val choseUserdBTN=findViewById<Button>(R.id.choseUser_Button_AW)
        choseUserdBTN.setOnClickListener{
            val intent= Intent(this,List_Work_User_ADD_activity::class.java)
            startActivity(intent)
        }

        val spinner: Spinner = findViewById(R.id.spinnerChoiceStatusWork)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.statusWorkArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }


        val saveWork_Button_AW=findViewById<Button>(R.id.saveWork_Button_AW)
        saveWork_Button_AW.setOnClickListener{
            var machineOK:Boolean=true
            var userOK:Boolean=true
            var fieldsOK:Boolean=true

           if(macchineList==null){
               Toast.makeText(this, "Nie wybrano maszyn do pracy", Toast.LENGTH_LONG).show()
               machineOK=false
           }
            if(user==null){
                Toast.makeText(this, "Nie wwybrano pracownika", Toast.LENGTH_LONG).show()
                userOK=false
            }
            if (fields==null){
                Toast.makeText(this, "nie wybrano pola", Toast.LENGTH_LONG).show()
                machineOK=false
            }
            var status=spinner.selectedItem.toString()
            val name:String
            name=findViewById<EditText>(R.id.nameWork_EditText_AW).getText().toString()

            if(name.trim()!=null && userOK==true && machineOK == true && fieldsOK == true && status!=null){
                var startD:String=""
                var stopD:String=""
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                //val formatted = current.format(formatter)

                if(status.equals("Wykonano")){
                    startD=current.format(formatter)
                    stopD=current.format(formatter)
                }

                if(status.equals("Realizacja")){
                    startD=current.format(formatter)
                }
                if(status.equals("Przerwane")){
                    startD=current.format(formatter)
                    stopD=current.format(formatter)
                }
                if(status.equals("Anulowano")){
                    startD=current.format(formatter)
                    stopD=current.format(formatter)
                }
                var work=Work(0,name,status,startD,stopD, user!!.idUser, fields!!.idField, fields!!.idFarm)

                val databaseHandler: DatabaseHandler = DatabaseHandler(this)
                val status = databaseHandler.addWork(work)
                if (status > -1) {
                var ok=false

                    var lastIDWork=databaseHandler.getLastIndexOfWork()
                    for (item in macchineList) {
                        if (lastIDWork != null) {
                            ok = databaseHandler.addWorkMachine(lastIDWork, item.idMachine)>-1
                            Log.d("Co mi tu zapisuje",lastIDWork.toString() + " id machine "+item.idMachine)
                        }
                    }
                    if(ok){
                        Toast.makeText(
                            applicationContext, "Dane zostały zapisane", Toast.LENGTH_LONG).show()
                            remoweUser()
                            remoweField()
                            cleanMachine()
                    }
                } else {
                    Toast.makeText(
                        applicationContext, "Nie udało się zapisać danych", Toast.LENGTH_LONG).show()
                }

            }

        }

    }



}