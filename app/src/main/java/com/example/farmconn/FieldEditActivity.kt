package com.example.farmconn

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.farmconn.Objects.Fields

class FieldEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_edit)
        if (HelperUser.getCurrentUser() == null) {
            //Jak uzytkownik nie jest zalogowny to go usuwa
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        var field = intent.getSerializableExtra("FIELD") as Fields
        putData(field)

        val showFieldInMapBTN = findViewById<ImageButton>(R.id.show_Button_AFE)
        showFieldInMapBTN.setOnClickListener {
            val uri: String =
                java.lang.String.format("geo:<" + field.xField + ">,<" + field.yField + ">?q=<" + field.xField + ">,<" + field.yField + ">(" + field.nameField + ")")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
        val dellData = findViewById<ImageButton>(R.id.dell_Button_AFE)
        dellData.setOnClickListener {
            val databaseHandler = DatabaseHandler(this)
            val status = databaseHandler.delateField(field)
            if (status > 0) {
                Toast.makeText(
                    applicationContext,
                    "Usunięto Pola " + status.toString(),
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(this, ListFieldsActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    applicationContext,
                    "coś poszło  nie tak " + status.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val edtData = findViewById<ImageButton>(R.id.edit_Button_AFE)
        edtData.setOnClickListener {
            val nameOfField: String
            nameOfField =
                findViewById<EditText>(R.id.editTextName_ActivityFieldEdit).text.toString()
            val descriptionOfField: String
            descriptionOfField =
                findViewById<EditText>(R.id.editTextMultlineDescriptioneditTextName_ActivityFieldEdit).text.toString()
            val xOfField: String
            xOfField = findViewById<EditText>(R.id.editTextTextX_ActivityFieldEdit).text.toString()
            val x = xOfField.toDouble()
            val yOfField: String
            yOfField = findViewById<EditText>(R.id.editTextY_ActivityFieldEdit).text.toString()
            val y = yOfField.toDouble()

            nameOfField.trim()
            descriptionOfField.trim()

            field.nameField = nameOfField
            field.decriptionField = descriptionOfField
            field.xField = x
            field.yField = y



            if (nameOfField != null && descriptionOfField != null && x != null && y != null) {
                val databaseHandler = DatabaseHandler(this)
                val status = databaseHandler.updateField(field)
                if (status > 0) {
                    Toast.makeText(applicationContext, "Zaktualizowano Dane", Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(this, ListFieldsActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "coś poszło  nie tak " + status.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun putData(field: Fields?) {

        if (field != null) {
            var nameOfField: EditText = findViewById(R.id.editTextName_ActivityFieldEdit)
            var descriptionOfField: EditText =
                findViewById(R.id.editTextMultlineDescriptioneditTextName_ActivityFieldEdit)
            var xOfField: EditText = findViewById(R.id.editTextTextX_ActivityFieldEdit)
            var yOfField: EditText = findViewById(R.id.editTextY_ActivityFieldEdit)


            nameOfField.text = Editable.Factory.getInstance().newEditable(field.nameField)
            descriptionOfField.text =
                Editable.Factory.getInstance().newEditable(field.decriptionField)
            xOfField.text = Editable.Factory.getInstance().newEditable(field.xField.toString())
            yOfField.text = Editable.Factory.getInstance().newEditable(field.yField.toString())

        }
    }

}