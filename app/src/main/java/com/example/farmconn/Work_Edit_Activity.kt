package com.example.farmconn

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.*
import com.example.farmconn.Objects.Fields
import com.example.farmconn.Objects.Machine
import com.example.farmconn.Objects.User
import com.example.farmconn.Objects.Work
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.farmconn.ListAdapters.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Work_Edit_Activity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_edit)

        var workOLD= intent.getSerializableExtra("WORK") as Work
        putDate(workOLD)

        // spiner
        val spinner: Spinner = findViewById(R.id.spinnerChoiceStatusWork_AWE)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this, R.array.statusWorkArray, android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.setSelection(adapter.getPosition(workOLD.statusWork))

        }
        
          //////////////
         //  machines//
        //////////////
        val listViewMachine =findViewById<ListView>(R.id.machine_ListView_AWE)
        var machineArrayList= getMachineList(workOLD)
        Log.d("Machine list", machineArrayList?.size.toString())

        // My custom adpter
        if(machineArrayList != null) {
            val myAdapter = MyListAdapterViev_MachineAtWork__EDIT(this, R.layout.list_machine_at_work, machineArrayList)
            listViewMachine.adapter = myAdapter


        }


        //////////////
         //  field   //
        //////////////
        val listViewFields =findViewById<ListView>(R.id.field_ListView_AWE)
        var fieldsArrayList=getFieldsData(workOLD)
        // My custom adpter
        if(fieldsArrayList != null) {
            val myAdapter = MyListAdapterView_FieldAtWork__EDIT(this, R.layout.list_fields_at_work, fieldsArrayList)
            listViewFields.adapter = myAdapter

        }
          //////////////
         //  user   //
        //////////////

        val listViewUser =findViewById<ListView>(R.id.user_ListView_AWE)
        var userArrayList= getUserData(workOLD)
        // My custom adpter
        if(userArrayList != null) {
            val myAdapter = MyListAdapterView_UserAtWork_EDIT(this, R.layout.item_user_at_work, userArrayList)
            listViewUser.adapter = myAdapter

        }

        val dellWork_Button_AWE=findViewById<Button>(R.id.dellWork_Button_AWE)
        dellWork_Button_AWE.setOnClickListener{
            var db=DatabaseHandler(this)

            val builder = AlertDialog.Builder(this)
            builder.setTitle("UWAGA")
            builder.setMessage("Czy jesteś pewny że chcesz usunąć zadanie ?")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                if(db.delateWorkMachineByIdWork(workOLD.idWork)>-1){
                    if(db.delateWork(workOLD)>-1){
                        val intent= Intent(this,List_Work_Activity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Została usunięta paraco o id "+ workOLD.idWork.toString(), Toast.LENGTH_LONG).show()
                    }
                }

            }

            builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                Toast.makeText(applicationContext, "Anulowano Usuwanie", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }
        val saveWork_Button_AWE=findViewById<Button>(R.id.saveWork_Button_AWE)
        saveWork_Button_AWE.setOnClickListener {
            var db=DatabaseHandler(this)
            var worksA = WorksActivity
            val name:String
            name=findViewById<EditText>(R.id.nameWork_EditText_AWE).getText().toString().trim()
            if(!workOLD.nameWork.equals(name)){
                workOLD.nameWork=name
            }
            if(!workOLD.statusWork.equals(spinner.selectedItem.toString())) {

                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                if (spinner.selectedItem.toString().equals("Wykonano")) {
                    workOLD.stopTime = current.format(formatter)
                }

                if (spinner.selectedItem.toString().equals("Realizacja")) {
                    workOLD.startTime = current.format(formatter)
                }
                if (spinner.selectedItem.toString().equals("Przerwane")) {
                    workOLD.stopTime = current.format(formatter)
                }
                if (spinner.selectedItem.toString().equals("Anulowano")) {
                    workOLD.stopTime = current.format(formatter)
                }

                if (worksA.user != null) {
                    workOLD.idUser = null
                }
                if (worksA.fields != null) {
                    workOLD.idFied = null
                }
            }
            val builder = AlertDialog.Builder(this)
                if(worksA.macchineList.size!=0){

                    builder.setTitle("UWAGA")
                    builder.setMessage("Czy jesteś pewny że chcesz usunąć maszyny ?")
                    builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                        for (item in WorksActivity.macchineList) {
                            db.delateWorkMachineByIdMachine(item.idMachine)
                        }
                        worksA.cleanMachine()
                    }
                    builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                        Toast.makeText(applicationContext, "Anulowano Usuwanie", Toast.LENGTH_SHORT).show()
                    }
                    //builder.show()
                }
            builder.setTitle("UWAGA")
            builder.setMessage("Czy chcesz zapisać dane ?")
            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
               if( db.updateWork(workOLD)>-1){
                   val intent= Intent(this,List_Work_Activity::class.java)
                   startActivity(intent)
                   Toast.makeText(applicationContext, "Dane zostały zaktualizowanie", Toast.LENGTH_SHORT).show()
               }else{
                   Toast.makeText(applicationContext, "Coś poszło nie tak", Toast.LENGTH_SHORT).show()
               }
            }
            builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                Toast.makeText(applicationContext, "Anulowano Usuwanie", Toast.LENGTH_SHORT).show()
            }
            builder.show()




        }

    }
    fun putDate( work: Work){


      if(work!=null) {
          findViewById<EditText>(R.id.nameWork_EditText_AWE).text =
              Editable.Factory.getInstance().newEditable(work.nameWork)
          findViewById<EditText>(R.id.timeStatr_EditText_AWE).text =
              Editable.Factory.getInstance().newEditable(work.startTime)
          findViewById<EditText>(R.id.timeStop_EditText_AWE).text =
              Editable.Factory.getInstance().newEditable(work.startTime)

      }
    }
    ////////////////Machines
    fun getMachineList(work: Work):List<Machine>?{
        val dbHandler= DatabaseHandler(this)

        if(work!=null) {
            return dbHandler.getAllMachineByWork(work.idWork)
        }
        return emptyList()
    }
    fun clearMachineData(): List<Machine>?
    {
        return  null
    }
    
    ////////////////////////////Fields
    fun getFieldsData(work: Work):List<Fields>{
        val dbHandler= DatabaseHandler(this)
        var fields:ArrayList<Fields> = ArrayList<Fields>()
        //val helperUser= HelperUser
        // id= helperUser.getCurrentUser()?.idFarm

        if(work!=null) {
            var field: Fields? = work.idFied?.let { dbHandler.getOneFieldsByID(it) }
            if (field != null) {
                fields.add(field)
            }
            //return dbHandler.viewFieldsList()
        }
        return fields
    }


    fun clearFielsData(): List<Fields>? {
        return  null
    }
    //////////// Users
    fun getUserData(work: Work):List<User>{
        val dbHandler= DatabaseHandler(this)
        var users:ArrayList<User> = ArrayList<User>()
        if(work!=null) {
           var user=dbHandler.getOneUserByID(work.idUser)
            if (user != null) {
                users.add(user)
            }
        }
        return users
    }
    fun clearUserData(): List<User>?
    {
        return  null
    }
}