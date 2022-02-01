package com.example.farmconn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.example.farmconn.ListAdapters.MyListAdapterField
import com.example.farmconn.ListAdapters.MyListAdapterView_FieldAtWork
import com.example.farmconn.Objects.Fields

class List_Work_Field_ADD_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_field_add)

        val btnFieldActivity = findViewById<Button>(R.id.back_Button_AWFA)
        btnFieldActivity.setOnClickListener {
            val intent = Intent(this, WorksActivity::class.java)
            startActivity(intent)
        }


        val listView = findViewById<ListView>(R.id.field_ListView_AWFA)

        //inicjalize som doata tu use

        var fieldsArrayList = getData()

        // My custom adpter
        if (fieldsArrayList != null) {
            val myAdapter =
                MyListAdapterView_FieldAtWork(this, R.layout.list_fields_at_work, fieldsArrayList)
            listView.adapter = myAdapter

        }
    }

    fun getData(): List<Fields> {
        val dbHandler = DatabaseHandler(this)
        val helperUser = HelperUser
        var id = helperUser.getCurrentUser()?.idFarm
        if (id != null) {
            return dbHandler.viewFieldsFarmList(id)
            //return dbHandler.viewFieldsList()
        }
        return emptyList()
    }


    fun clearData(): List<Fields>? {
        return null
    }
}