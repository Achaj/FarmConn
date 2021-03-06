package com.example.farmconn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.farmconn.Objects.User
import java.math.BigInteger
import java.security.MessageDigest

class CrateNewUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crate_new_user)

        val addNewUserBTN = findViewById<Button>(R.id.addUser_BUTTON_CNU)
        addNewUserBTN.setOnClickListener {
            val name: String
            name = findViewById<EditText>(R.id.name_EDITTEXT_CNU).getText().toString()
            val surname: String
            surname = findViewById<EditText>(R.id.surname_EDITTEXT_CNU).getText().toString()
            val email: String
            email = findViewById<EditText>(R.id.email_EditText_AM).getText().toString()
            val pass: String
            pass = findViewById<EditText>(R.id.pass_EditText_AM).getText().toString()
            val passConfirm: String
            passConfirm = findViewById<EditText>(R.id.passTwo_EDITTEXT_CNU).getText().toString()

            // check if data not null valuable

            var okName: Boolean
            var okSurname: Boolean
            var okEmail: Boolean
            var okPass: Boolean
            if (name != "null" && surname != "null" && email != "null" && pass != "null" && passConfirm != "null") {
                okName = isNamewlValid(name)
                okSurname = isNamewlValid(name)
                okEmail = isEmailValid(email)
                okPass = isPassEquales(pass, passConfirm)
                if (okName == true && okSurname == true && okEmail == true && okPass == true) {
                    var user = User(0, name, surname, email, md5(pass), "normal", 1);
                    // Toast.makeText(applicationContext, user.toString(), Toast.LENGTH_LONG).show()
                    val databaseHandler: DatabaseHandler = DatabaseHandler(this)
                    val status = databaseHandler.addUser(user)
                    if (status > -1) {
                        Toast.makeText(
                            applicationContext,
                            "Uda??o si?? zapisa?? dane",
                            Toast.LENGTH_LONG
                        )
                            .show()

                    } else {
                        Toast.makeText(
                            applicationContext, "Nie uda??o si?? zapisa?? danych", Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    if (okName != true) {
                        Toast.makeText(
                            applicationContext,
                            "Twoje imie mo??e si?? ska??ada?? tylko z liter",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (okSurname != true) {
                        Toast.makeText(
                            applicationContext,
                            "Twoje nazwisko mo??e si?? ska??ada?? tylko z liter",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (okEmail != true) {
                        Toast.makeText(
                            applicationContext,
                            "Tw??j email nie jest w odpowiedmin foramacie xyz@xyx.xyz",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (okPass != true) {
                        Toast.makeText(
                            applicationContext,
                            "Has??o musi si?? sk??ada?? z liter i cyfr o d??ugo??ci minimu 8 znak??w has??a musz?? by?? identyczne",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            } else {

            }
        }
    }

    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    // Validation data hear

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isNamewlValid(it: String): Boolean {
        val REX_NAME = "[A-Za-z????????????????????????????????????]{3,30}".toRegex()
        return REX_NAME.matches(it)
    }

    // Retur treue is paswword is ok
    fun isPassworlValid(it: String): Boolean {
        val REX_NAME = "[A-Za-z0-9]\\w{8,}".toRegex()
        return !REX_NAME.matches(it)
    }

    fun isPassEquales(p1: String, p2: String): Boolean {
        if (isPassworlValid(p1)) {
            if (!p1.equals(p2)) {
                return false;
                Log.d("przesz??o", "nie")
            } else {
                Log.d("przesz??o", "tak")
                return true

            }
        } else {
            return false
            //   Toast.makeText(applicationContext, "Minimum osiem znak??w,a-z A-Z 0-9 ", Toast.LENGTH_LONG).show()
        }
    }
}