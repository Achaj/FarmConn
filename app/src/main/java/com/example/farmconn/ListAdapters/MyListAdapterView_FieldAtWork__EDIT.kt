package com.example.farmconn.ListAdapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.farmconn.Objects.Fields
import com.example.farmconn.R
import com.example.farmconn.WorksActivity

class MyListAdapterView_FieldAtWork__EDIT(var mCtx: Context, var resource:Int, var items:List<Fields>)
    : ArrayAdapter<Fields>( mCtx , resource , items ){



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(resource , null )


        var nameOfField: TextView = view.findViewById(R.id.nameOfField_LFAW)
        var idOfField: TextView = view.findViewById(R.id.idOfField_LFAW)
        var xCordinatsEtitableText: TextView = view.findViewById(R.id.xCordinatsFieldTextViewListFields_LFAW)!!
        var yCordinatsEtitableText: TextView = view.findViewById(R.id.yCordinatsFieldTextViewListFields_LFAW)!!

        var fieldList: Fields = items[position]

        nameOfField.text= fieldList.nameField
        idOfField.text= fieldList.idField.toString()
        xCordinatsEtitableText.text= fieldList.xField.toString()
        yCordinatsEtitableText.text= fieldList.yField.toString()

        var add=view.findViewById<ImageView>(R.id.addField_imageView_LFAW)
        add.setOnClickListener {
            WorksActivity.remoweField()
          //  Toast.makeText(mCtx, "Wybtano pole " + fieldList.nameField, Toast.LENGTH_LONG).show()
            view.findViewById<LinearLayout>(R.id.linearLayout_LFAW).setBackgroundColor(Color.parseColor("#DCF5C3"))


        }
        var dell=view.findViewById<ImageView>(R.id.dellField_imageView_LFAW)
        dell.setOnClickListener {
            WorksActivity.setField(fieldList)
            view.findViewById<LinearLayout>(R.id.linearLayout_LFAW).setBackgroundColor(Color.RED)
        }


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