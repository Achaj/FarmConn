package com.example.farmconn

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.ImageButton
import com.example.farmconn.Objects.Fields

class FieldEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_edit)

        var field= intent.getSerializableExtra("FIELD") as Fields
        putData(field)

        val showFieldInMapBTN =findViewById<ImageButton>(R.id.showLocationFieldBTN_ActivityFieldEdit)
        showFieldInMapBTN.setOnClickListener{
            val uri: String = java.lang.String.format("geo:<" + field.xField  + ">,<" + field.yField + ">?q=<" + field.xField  + ">,<" + field.yField + ">(" + field.nameField + ")")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
          startActivity(intent)
        }
    }

    private fun putData(field: Fields?) {

        if (field != null) {
            var nameOfField: EditText = findViewById(R.id.editTextName_ActivityFieldEdit)
            var descriptionOfField: EditText = findViewById(R.id.editTextMultlineDescriptioneditTextName_ActivityFieldEdit)
            var xOfField: EditText = findViewById(R.id.editTextTextX_ActivityFieldEdit)
            var yOfField: EditText = findViewById(R.id.editTextY_ActivityFieldEdit)


            nameOfField.text=Editable.Factory.getInstance().newEditable(field.nameField)
            descriptionOfField.text=Editable.Factory.getInstance().newEditable(field.decriptionField)
            xOfField.text=Editable.Factory.getInstance().newEditable(field.xField.toString())
            yOfField.text=Editable.Factory.getInstance().newEditable(field.yField.toString())

        }
    }

}