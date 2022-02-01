package com.example.farmconn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.example.farmconn.ListAdapters.MyListAdapterField
import com.example.farmconn.ListAdapters.MyListAdapterWork
import com.example.farmconn.Objects.Fields
import com.example.farmconn.Objects.Work

class List_Work_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_work)

        val btnbackActivity=findViewById<Button>(R.id.back_Button_ALW)
        btnbackActivity.setOnClickListener{
            val intent= Intent(this,WorksActivity::class.java)
            startActivity(intent)
        }


        var listView =findViewById<ListView>(R.id.machine_ListView_ALW)
        var workArrayList= getData()

        // My custom adpter
        if(workArrayList != null) {
            val myAdapter = MyListAdapterWork(this, R.layout.item_work_list, workArrayList)
            listView.adapter = myAdapter

            // On clik Listinier
            listView.setOnItemClickListener() { adapterView, view, position, id ->
                //  val itemAtPos = adapterView.getItemAtPosition(position)
                //  val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                //  Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Work_Edit_Activity::class.java)
                intent.putExtra("WORK", workArrayList[position])
                startActivity(intent)

            }
        }
    }

    fun getData():List<Work>{
        val dbHandler= DatabaseHandler(this)
        val helperUser= HelperUser
        var id= helperUser.getCurrentUser()?.idFarm
        if(id!=null) {
            return dbHandler.viewWorkOfOneFarmList(id)
            //return dbHandler.viewFieldsList()
        }
        return emptyList()
    }


    fun clearData(): List<Fields>? {
        return  null
    }
}