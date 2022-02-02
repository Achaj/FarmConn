package com.example.farmconn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.example.farmconn.ListAdapters.MyListAdapterViev_MachineAtWork
import com.example.farmconn.Objects.Machine

class List_Work_Machine_ADD_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_work_machine_add)
        if (HelperUser.getCurrentUser() == null) {
            //Jak uzytkownik nie jest zalogowny to go usuwa
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val backBTN = findViewById<Button>(R.id.back_Button_ALWMA)
        backBTN.setOnClickListener {
            val intent = Intent(this, WorksActivity::class.java)
            startActivity(intent)
        }

        val listView = findViewById<ListView>(R.id.machine_ListView_ALWMA)
        var machineArrayList = getData()

        // My custom adpter
        if (machineArrayList != null) {
            val myAdapter = MyListAdapterViev_MachineAtWork(
                this,
                R.layout.list_machine_at_work,
                machineArrayList
            )
            listView.adapter = myAdapter

        } else {
            Toast.makeText(this, "BRAK DANYCH DO WYŚWIETLENIA", Toast.LENGTH_LONG).show()
        }
    }

    fun getData(): List<Machine> {
        val dbHandler = DatabaseHandler(this)
        val helperUser = HelperUser
        var id = helperUser.getCurrentUser()?.idFarm
        if (id != null) {
            return dbHandler.viewMachineOnFarmList(id)
        }
        return emptyList()
    }

    fun clearData(): List<Machine>? {
        return null
    }
}