package com.example.farmconn

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.farmconn.Objects.Fields

class MyListAdapter(var mCtx: Context, var resource:Int, var items:List<Fields>)
    : ArrayAdapter<Fields>( mCtx , resource , items ){



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(resource , null )


        var nameOfField: TextView = view.findViewById(R.id.nameOfField)
        var idOfField: TextView = view.findViewById(R.id.idOfField)
        var xCordinatsEtitableText: TextView = view.findViewById(R.id.xCordinatsFieldTextViewListFields)!!
        var yCordinatsEtitableText: TextView = view.findViewById(R.id.yCordinatsFieldTextViewListFields)!!

        var fieldList: Fields = items[position]

        nameOfField.text= fieldList.nameField
        idOfField.text= fieldList.idField.toString()
        xCordinatsEtitableText.text= fieldList.xField.toString()
        yCordinatsEtitableText.text= fieldList.yField.toString()

        return view
    }

    override fun getItem(position: Int): Fields? {
        return super.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getCount(): Int {
        return super.getCount()
    }
}