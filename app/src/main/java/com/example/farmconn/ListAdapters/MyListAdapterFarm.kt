package com.example.farmconn.ListAdapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.farmconn.HelperUser
import com.example.farmconn.Objects.Farm
import com.example.farmconn.Objects.Fields
import com.example.farmconn.R

class MyListAdapterFarm(var mCtx: Context, var resource:Int, var items:List<Farm>)
    : ArrayAdapter<Farm>( mCtx , resource , items ){



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(resource , null )


        var name_EditText_LFI: TextView = view.findViewById(R.id.name_EditText_LFI)
        var x_EditText_LFI: TextView = view.findViewById(R.id.x_EditText_LFI)
        var y_EditText_LFI: TextView = view.findViewById(R.id.y_EditText_LFI)


       var farm=items[position]
        name_EditText_LFI.text=farm.nameFarm
        x_EditText_LFI.text=farm.xCord.toString()
        y_EditText_LFI.text=farm.yCord.toString()

        var user=HelperUser.getCurrentUser()

        if (user != null) {
            if(user.idFarm.equals(farm.idFarm)){
                view.findViewById<LinearLayout>(R.id.linearLayout_LFI).setBackgroundColor(Color.parseColor("#DCF5C3"))
            }else{
                view.findViewById<LinearLayout>(R.id.linearLayout_LFI).setBackgroundColor(Color.parseColor("#66FAE205"))
            }
        }

        return view
    }

    override fun getItem(position: Int): Farm? {
        return super.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getCount(): Int {
        return super.getCount()
    }
}