package com.example.farmconn

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.farmconn.Objects.Fields
import com.example.farmconn.Objects.Machine

class MyListAdapterMachine(var mCtx: Context, var resource:Int,var items:List<Machine>)
    : ArrayAdapter<Machine>( mCtx , resource , items ){



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(resource , null )


        var typeMachine: TextView = view.findViewById(R.id.typeMachine_TextView_lMi)
        var brandMachine: TextView = view.findViewById(R.id.brandMachine_TextView_lMi)
        var modelMachine: TextView = view.findViewById(R.id.modelMachine_TextView_lMi)
        var weightMachine: TextView = view.findViewById(R.id.weightMachine_TextView_lMi)
        var widthMachine: TextView = view.findViewById(R.id.width_TextView_lMi)


       var machineList: Machine = items[position]

        typeMachine.text=machineList.typeMachine
        brandMachine.text=machineList.brandMachine
        modelMachine.text=machineList.modelMachine
        weightMachine.text=machineList.weightMachine.toString()
        widthMachine.text=machineList.widthMachine.toString()

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