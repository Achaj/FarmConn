package com.example.farmconn.ListAdapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.farmconn.DatabaseHandler
import com.example.farmconn.Objects.Fields
import com.example.farmconn.Objects.Work
import com.example.farmconn.R

class MyListAdapterWork(var mCtx: Context, var resource:Int, var items:List<Work>)
    : ArrayAdapter<Work>( mCtx , resource , items ){



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(resource , null )


        var nameWork_TextView_IWL: TextView = view.findViewById(R.id.nameWork_TextView_IWL)
        var statusWork_TextView_IWL: TextView = view.findViewById(R.id.statusWork_TextView_IWL)
        var userWork_TextView_IWL: TextView = view.findViewById(R.id.userWork_TextView_IWL)
        var fieldWork_TextView_IWL: TextView = view.findViewById(R.id.fieldWork_TextView_IWL)!!

        var work:Work=items[position]


        nameWork_TextView_IWL.text=work.nameWork
        statusWork_TextView_IWL.text=work.statusWork
        val dbHelper= DatabaseHandler(mCtx)

        userWork_TextView_IWL.text=dbHelper.getOneUserByID(work.idUser)?.nameUser
        fieldWork_TextView_IWL.text= work.idFied?.let { dbHelper.getOneFieldsByID(it)?.nameField }

        if(work.statusWork.equals("Wykonano")){
            view.findViewById<LinearLayout>(R.id.linearLayout_IWL).setBackgroundColor(Color.GRAY)
        }
        if(work.statusWork.equals("Zaplanowano")){
            view.findViewById<LinearLayout>(R.id.linearLayout_IWL).setBackgroundColor(Color.BLUE)
        }
        if(work.statusWork.equals("Realizacja")){
            view.findViewById<LinearLayout>(R.id.linearLayout_IWL).setBackgroundColor(Color.GREEN)
        }
        if(work.statusWork.equals("Przerwane")){
            view.findViewById<LinearLayout>(R.id.linearLayout_IWL).setBackgroundColor(Color.RED)
        }
        if(work.statusWork.equals("Anulowano")){
            view.findViewById<LinearLayout>(R.id.linearLayout_IWL).setBackgroundColor(Color.WHITE)
        }

        return view
    }

    override fun getItem(position: Int): Work? {
        return super.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getCount(): Int {
        return super.getCount()
    }
}