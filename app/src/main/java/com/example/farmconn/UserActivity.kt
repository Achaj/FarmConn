package com.example.farmconn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.farmconn.Objects.User
import java.math.BigInteger
import java.security.MessageDigest

class UserActivity : AppCompatActivity() {
    val databaseHandler: DatabaseHandler = DatabaseHandler(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        var helperUser = HelperUser
        val user = helperUser.getCurrentUser()
        if (user == null) {
            //Jak uzytkownik nie jest zalogowny to go usuwa
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
            this.finishAndRemoveTask();
        }

        val back_Button_AU = findViewById<Button>(R.id.back_Button_AU)
        back_Button_AU.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }

        var oldUser: User? = HelperUser.getCurrentUser()
        val logOut = findViewById<ImageButton>(R.id.logOut)
        logOut.setOnClickListener {
            HelperUser.setCurrentUserNull()
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
            finishAndRemoveTask();
        }

        if (oldUser != null) {
            putData(oldUser)
            val edit_User_AU = findViewById<Button>(R.id.edit_User_AU)
            edit_User_AU.setOnClickListener {
                val name: String
                name = findViewById<EditText>(R.id.name_EDITTEXT_AU).getText().toString().trim()
                if (!oldUser.nameUser.equals(name)) {
                    oldUser.nameUser = name
                }
                val surname: String
                surname =
                    findViewById<EditText>(R.id.surname_EDITTEXT_AU).getText().toString().trim()
                if (!oldUser.secondNameUser.equals(surname)) {
                    oldUser.secondNameUser = surname
                }
                val email: String
                email = findViewById<EditText>(R.id.email_EditText_AU).getText().toString().trim()
                if (!oldUser.emailUser.equals(email)) {
                    oldUser.emailUser = email
                }
                val pass: String
                pass = findViewById<EditText>(R.id.pass_EditText_AU).getText().toString().trim()
                val passConfirm: String
                passConfirm =
                    findViewById<EditText>(R.id.passTwo_EDITTEXT_AU).getText().toString().trim()

                // check if data not null valuable

                var okName: Boolean
                var okSurname: Boolean
                var okEmail: Boolean
                var okPass: Boolean
                if (name != null && surname != null && email != null ) {
                    okName = isNamewlValid(name)
                    okSurname = isNamewlValid(name)
                    okEmail = isEmailValid(email)
                    okPass = isPassEquales(pass, passConfirm)

                    if (okName == true && okSurname == true && okEmail == true && okPass==true ) {

                        if (!oldUser.passwordUser.equals(md5(pass)) &&  isPassworlValid(pass) && pass.length > 7 && passConfirm.length>7) {
                            oldUser.passwordUser = md5(pass)
                           Log.d("Pass","zsotało nowe hasło")
                        }else{
                            Log.d("Pass","zsotało stare hasło")
                        }
                        val status = databaseHandler.updateUser(oldUser)
                        if (status > -1) {
                            Toast.makeText(
                                applicationContext,
                                "Udało się zapisać dane",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            HelperUser.setCurrentUser(oldUser)

                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Nie udało się zapisać danych",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    fun putData(user: User) {

        findViewById<EditText>(R.id.name_EDITTEXT_AU).text =
            Editable.Factory.getInstance().newEditable(user.nameUser)
        findViewById<EditText>(R.id.surname_EDITTEXT_AU).text =
            Editable.Factory.getInstance().newEditable(user.secondNameUser)
        findViewById<EditText>(R.id.email_EditText_AU).text =
            Editable.Factory.getInstance().newEditable(user.emailUser)
        findViewById<EditText>(R.id.location_EditText_AU).text =
            Editable.Factory.getInstance().newEditable(
                databaseHandler.getOneFarmByID(user.idFarm)?.nameFarm
            )
    }

    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    // Validation data hear

    fun isEmailValid(email: String): Boolean {
        var x=android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if(!x){
            Toast.makeText(applicationContext, "Nie poprawny format danych ", Toast.LENGTH_LONG).show()
        }
        return x
    }

    fun isNamewlValid(it: String): Boolean {
        val REX_NAME = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{3,30}".toRegex()
        var x= REX_NAME.matches(it)
        if(!x){
            Toast.makeText(applicationContext, "Użyto nie dozwolonych znaków ", Toast.LENGTH_LONG).show()
        }
        return x
    }

    // Retur treue is paswword is ok
    fun isPassworlValid(it: String): Boolean {
        val REX_NAME = "[A-Za-z0-9]\\w{8,}".toRegex()
        return !REX_NAME.matches(it)
    }

    fun isPassEquales(p1: String, p2: String): Boolean {
        if (p1 != null && p2 != null) {
            if (isPassworlValid(p1)) {
                if (!p1.equals(p2)) {
                    return false;
                    Toast.makeText(applicationContext, "Hasła nie są takike same ", Toast.LENGTH_LONG).show()

                    Log.d("przeszło", "nie")
                } else {
                    Log.d("przeszło", "tak")
                    return true

                }
            } else {
                Toast.makeText(applicationContext, "Hasła nie spełnia warubków", Toast.LENGTH_LONG).show()
                return false

            }
        } else {
            Toast.makeText(applicationContext, "Pola są puste ", Toast.LENGTH_LONG).show()

            return false

        }
    }
}