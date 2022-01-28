package com.example.farmconn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.example.farmconn.Objects.Fields
import com.example.farmconn.Objects.Machine

class ListMachineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_machine)

        val listView =findViewById<ListView>(R.id.machine_ListView_ALM)

        val backBTN=findViewById<Button>(R.id.back_Button_ALM)
        backBTN.setOnClickListener{
            val intent= Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
        val refreshBTN=findViewById<Button>(R.id.refresh_Button_ALM)
        refreshBTN.setOnClickListener{
            val intent= Intent(this, ListMachineActivity::class.java)
            startActivity(intent)
        }

        var machineArrayList= getData()

        // My custom adpter
        if(machineArrayList != null) {
            val myAdapter = MyListAdapterMachine(this, R.layout.list_machine_item, machineArrayList)
            listView.adapter = myAdapter



            // On clik Listinier
            listView.setOnItemClickListener() { adapterView, view, position, id ->
                 // val itemAtPos = adapterView.getItemAtPosition(position)
                 // val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                 // Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()

               val intent = Intent(this, Maschine__Edit__Activity::class.java)
              intent.putExtra("MACHICHINE", machineArrayList[position])
                startActivity(intent)

            }
        }else{
            Toast.makeText(this, "BRAK DANYCH DO WYŚWIETLENIA", Toast.LENGTH_LONG).show()
        }

    }
    fun getData():List<Machine>{
        val dbHandler= DatabaseHandler(this)
        var machineArrayList= dbHandler.viewMachineList()
        if (machineArrayList!=null){
            return machineArrayList
        }else{
            return emptyList()
        }

    }
    fun clearData(): List<Machine>?
    {
        return  null
    }
}