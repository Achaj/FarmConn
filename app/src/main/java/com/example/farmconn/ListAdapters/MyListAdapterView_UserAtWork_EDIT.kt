package com.example.farmconn.ListAdapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.farmconn.DatabaseHandler
import com.example.farmconn.Objects.Fields
import com.example.farmconn.Objects.User
import com.example.farmconn.R
import com.example.farmconn.WorksActivity

class MyListAdapterView_UserAtWork_EDIT(
    var mCtx: Context,
    var resource: Int,
    var items: List<User>
) : ArrayAdapter<User>(mCtx, resource, items) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resource, null)


        var name_EditText_IUAW: TextView = view.findViewById(R.id.name_EditText_IUAW)
        var surname_EditText_IUAW: TextView = view.findViewById(R.id.surname_EditText_IUAW)

        var user: User = items[position]

        name_EditText_IUAW.text = user.nameUser
        surname_EditText_IUAW.text = user.secondNameUser


        var add = view.findViewById<ImageView>(R.id.addUser_ImageView_IUAW)
        add.setOnClickListener {
            WorksActivity.remoweUser()
            view.findViewById<LinearLayout>(R.id.linearLayout_LFAW)
                .setBackgroundColor(Color.parseColor("#DCF5C3"))


        }
        var dell = view.findViewById<ImageView>(R.id.dellUser_ImageView_IUAW)
        dell.setOnClickListener {
            WorksActivity.addUser(user)
            view.findViewById<LinearLayout>(R.id.linearLayout_IUAW).setBackgroundColor(Color.RED)

        }


        return view
    }

    override fun getItem(position: Int): User? {
        return super.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getCount(): Int {
        return super.getCount()
    }
}