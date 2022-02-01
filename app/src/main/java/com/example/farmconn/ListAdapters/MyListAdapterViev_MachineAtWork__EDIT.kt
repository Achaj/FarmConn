package com.example.farmconn.ListAdapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.farmconn.DatabaseHandler
import com.example.farmconn.Objects.Machine
import com.example.farmconn.R
import com.example.farmconn.WorksActivity

class MyListAdapterViev_MachineAtWork__EDIT(
    var mCtx: Context,
    var resource: Int,
    var items: List<Machine>
) : ArrayAdapter<Machine>(mCtx, resource, items) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resource, null)


        var typeMachine: TextView = view.findViewById(R.id.typeMachine_TextView_LMIAW)
        var brandMachine: TextView = view.findViewById(R.id.brandMachine_TextView_LMIAW)
        var modelMachine: TextView = view.findViewById(R.id.modelMachine_TextView_LMIAW)
        var weightMachine: TextView = view.findViewById(R.id.weightMachine_TextView_LMIAW)
        var widthMachine: TextView = view.findViewById(R.id.width_TextView_LMIAW)


        var machineList: Machine = items[position]

        typeMachine.text = machineList.typeMachine
        brandMachine.text = machineList.brandMachine
        modelMachine.text = machineList.modelMachine
        weightMachine.text = machineList.weightMachine.toString()
        widthMachine.text = machineList.widthMachine.toString()

        var add = view.findViewById<ImageView>(R.id.addMachine_IMAGE_LWIAW)
        add.setOnClickListener {
            WorksActivity.remoweMachine(machineList)
            view.findViewById<LinearLayout>(R.id.linearLayout_LMIAW)
                .setBackgroundColor(Color.parseColor("#DCF5C3"))
        }
        var dell = view.findViewById<ImageView>(R.id.remoweMachine_IMAGE_LWIAW)
        dell.setOnClickListener {
            WorksActivity.addMachine(machineList)
            view.findViewById<LinearLayout>(R.id.linearLayout_LMIAW).setBackgroundColor(Color.RED)
        }

        //  nameOfField.text= fieldList.nameField
        //  idOfField.text= fieldList.idField.toString()
        //  xCordinatsEtitableText.text= fieldList.xField.toString()
        //  yCordinatsEtitableText.text= fieldList.yField.toString()

        return view
    }

    override fun getItem(position: Int): Machine? {
        return super.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getCount(): Int {
        return super.getCount()
    }
}