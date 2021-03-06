package com.example.farmconn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.example.farmconn.ListAdapters.MyListAdapterField
import com.example.farmconn.Objects.Fields

class ListFieldsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_fields)
        if (HelperUser.getCurrentUser() == null) {
            //Jak uzytkownik nie jest zalogowny to go usuwa
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val btnFieldActivity = findViewById<Button>(R.id.addFieldActivityActivityListFieldsBTN)
        btnFieldActivity.setOnClickListener {
            val intent = Intent(this, FieldActivity::class.java)
            startActivity(intent)
        }


        val listView = findViewById<ListView>(R.id.listOfFieldView)

        //inicjalize som doata tu use

        // fieldsArrayList.add(Fields(0,"Moje","Zenka",50.258541,22.085240,1))
        // fieldsArrayList.add(Fields(0,"Moje","Marka",2.0,0.0,1))
        // fieldsArrayList.add(Fields(0,"Moje","Grześka",3.0,0.0,1))
        // fieldsArrayList.add(Fields(0,"Moje","Maćka",4.0,0.0,1))

        var fieldsArrayList = getData()

        // My custom adpter
        if (fieldsArrayList != null) {
            val myAdapter = MyListAdapterField(this, R.layout.list_fields_item, fieldsArrayList)
            listView.adapter = myAdapter


            // On clik Listinier
            listView.setOnItemClickListener() { adapterView, view, position, id ->
                //  val itemAtPos = adapterView.getItemAtPosition(position)
                //  val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                //  Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
                val intent = Intent(this, FieldEditActivity::class.java)
                intent.putExtra("FIELD", fieldsArrayList[position])
                startActivity(intent)

            }
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

