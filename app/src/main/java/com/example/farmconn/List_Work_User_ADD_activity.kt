package com.example.farmconn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.example.farmconn.ListAdapters.MyListAdapterViev_MachineAtWork
import com.example.farmconn.ListAdapters.MyListAdapterView_UserAtWork
import com.example.farmconn.Objects.Machine
import com.example.farmconn.Objects.User

class List_Work_User_ADD_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_work_user_add)
        if (HelperUser.getCurrentUser() == null) {
            //Jak uzytkownik nie jest zalogowny to go usuwa
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val backBTN = findViewById<Button>(R.id.back_Button_ALUMA)
        backBTN.setOnClickListener {
            val intent = Intent(this, WorksActivity::class.java)
            startActivity(intent)
        }

        val listView = findViewById<ListView>(R.id.user_ListView_ALUMA)
        var userArrayList = getData()

        // My custom adpter
        if (userArrayList != null) {
            val myAdapter =
                MyListAdapterView_UserAtWork(this, R.layout.item_user_at_work, userArrayList)
            listView.adapter = myAdapter

        } else {
            Toast.makeText(this, "BRAK DANYCH DO WYŚWIETLENIA", Toast.LENGTH_LONG).show()
        }
    }

    fun getData(): List<User> {
        val dbHandler = DatabaseHandler(this)
        val helperUser = HelperUser
        var id = helperUser.getCurrentUser()?.idFarm
        if (id != null) {
            return dbHandler.viewUserOfOneFarmList(id)
        }
        return emptyList()
    }

    fun clearData(): List<User>? {
        return null
    }
}