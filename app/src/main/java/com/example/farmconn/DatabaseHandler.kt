package com.example.farmconn

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.farmconn.Objects.Farm
import com.example.farmconn.Objects.Fields
import com.example.farmconn.Objects.Machine
import com.example.farmconn.Objects.Work
import com.example.farmconn.Objects.User as User

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        //wersja bazy
        private val DATABASE_VERSION = 1

        //nazwa bazy
        private val DATABASE_NAME = "FarmDataBase"

        //tabela farm
        private const val Farm_Table = "Farm_Table"
        private const val ID_FARM = "id"
        private const val NAME_FARM = "name"
        private const val X_FARM = "x"
        private const val Y_FARM = "y"

        // tabela pól
        private const val FIELD_Table = "Field_Table"
        private const val ID_FIELD = "id"
        private const val NAME_FIELD = "name"
        private const val DSC_FIELD = "desc"
        private const val X_FIELD = "x"
        private const val Y_FIELD = "y"
        private const val ID_FARM_FIELD = "id_farm"

        //tabela użytkowników
        private const val USER_TABLE = "User_Table"
        private const val ID_USER = "id"
        private const val NAME_USER = "name"
        private const val SURNAME_USER = "surename"
        private const val EMAIL_USER = "email"
        private const val PASS_USER = "pass"
        private const val TYPE_USER = "type"
        private const val ID_FARM_USER = "id_farm"
        // zawiera
        //private val ID_FARM="id"

        //tabela maszyn
        private const val MASCHINE_Table = "Machine_Table"
        private const val ID_MASCHINE = "id"
        private const val BRAND_MACHINE = "brand"
        private const val MODEL_MACHINE = "model"
        private const val TYPE_MACHINE = "type"
        private const val CAPACIY_MACHINE = "capacity"
        private const val FUEL_MACHINE = "fuel"
        private const val WEIGHT_MACHINE = "weight"
        private const val WIDTH_MACHINE = "width"
        private const val X_MACHINE = "x"
        private const val Y_MACHINE = "y"
        private const val ID_FARM_MACHINE = "id_farm"
        // zawiera
        //private val ID_FARM="id"


        //tabela pracy
        private const val WORK_Table = "WORK_Table"
        private const val ID_WORK = "id"
        private const val NAME_WORK = "name"
        private const val STATUS_WORK = "status"
        private const val START_WORK = "start"
        private const val STOP_WORK = "stop"
        private const val ID_USER_WORK = "id_user"
        private const val ID_FIELD_WORK = "id_field"
        private const val ID_FARM_WORK = "id_farm"

        private const val WORK_MACHINE_Table = "WORK_MACHINE_Table"
        private const val ID_WORK_MACHINE = "id"
        private const val ID_MACHIE_WM = "id_machine"
        private const val ID_WORK_WM = "id_work"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CRATE_TABEL_FARM = ("CREATE TABLE IF NOT EXISTS " + Farm_Table + " ("
                + ID_FARM + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + NAME_FARM + " TEXT NOT NULL,"
                + X_FARM + " REAL NOT NULL,"
                + Y_FARM + " REAL NOT NULL);")

        val CRATE_TABLE_FIELD = ("CREATE TABLE IF NOT EXISTS " + FIELD_Table + " ("
                + ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + NAME_FIELD + " TEXT NOT NULL,"
                + DSC_FIELD + " TEXT,"
                + X_FIELD + " REAL NOT NULL,"
                + Y_FIELD + " REAL NOT NULL,"
                + ID_FARM_FIELD + " INTEGER ,"
                + "FOREIGN KEY(" + ID_FARM_FIELD + ") REFERENCES " + Farm_Table + "(" + ID_FARM + ") ON DELETE SET NULL);")

        val CRATE_TABLE_USER = ("CREATE TABLE IF NOT EXISTS " + USER_TABLE + " ("
                + ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + NAME_USER + " TEXT NOT NULL,"
                + SURNAME_USER + " TEXT NOT NULL,"
                + EMAIL_USER + " TEXT UNIQUE NOT NULL ,"
                + PASS_USER + " TEXT NOT NULL,"
                + TYPE_USER + " TEXT NOT NULL,"
                + ID_FARM_USER + " INTEGER ,"
                + "FOREIGN KEY(" + ID_FARM_USER + ") REFERENCES " + Farm_Table + " (" + ID_FARM + ") ON DELETE SET NULL);")

        val CRATE_TABLE_MACHINE = ("CREATE TABLE IF NOT EXISTS " + MASCHINE_Table + " ("
                + ID_MASCHINE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + BRAND_MACHINE + " TEXT NOT NULL,"
                + MODEL_MACHINE + " TEXT NOT NULL,"
                + TYPE_MACHINE + " TEXT NOT NULL,"
                + CAPACIY_MACHINE + " REAL,"
                + FUEL_MACHINE + " REAL,"
                + WEIGHT_MACHINE + " REAL,"
                + WIDTH_MACHINE + " REAL,"
                + X_MACHINE + " REAL NOT NULL,"
                + Y_MACHINE + " REAL NOT NULL,"
                + ID_FARM_MACHINE + " INTEGER ,"
                + "FOREIGN KEY(" + ID_FARM_MACHINE + ") REFERENCES " + Farm_Table + " (" + ID_FARM + ") ON DELETE SET NULL);")

        val CRATE_TABLE_WORK = ("CREATE TABLE IF NOT EXISTS " + WORK_Table + "("
                + ID_WORK + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + NAME_WORK + " TEXT NOT NULL,"
                + STATUS_WORK + " TEXT NOT NULL,"
                + START_WORK + " TEXT ,"
                + STOP_WORK + " TEXT,"
                + ID_USER_WORK + " INTEGER,"
                + ID_FIELD_WORK + " INTEGER,"
                + ID_FARM_WORK + " INTEGER,"
                + "FOREIGN KEY(" + ID_FARM_WORK + ") REFERENCES " + Farm_Table + " (" + ID_FARM + ")ON DELETE SET NULL,"
                + "FOREIGN KEY(" + ID_USER_WORK + ") REFERENCES " + USER_TABLE + " (" + ID_USER + ")ON DELETE SET NULL,"
                + "FOREIGN KEY(" + ID_FIELD_WORK + ") REFERENCES " + FIELD_Table + " (" + ID_FIELD + ")ON DELETE SET NULL);")

        val CRATE_TABLE_WORK_MACHINE = ("CREATE TABLE IF NOT EXISTS " + WORK_MACHINE_Table + "("
                + ID_WORK_MACHINE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + ID_MACHIE_WM + " INTEGER NOT NULL,"
                + ID_WORK_WM + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + ID_MACHIE_WM + ") REFERENCES " + MASCHINE_Table + " (" + ID_MASCHINE + ") ON DELETE CASCADE,"
                + "FOREIGN KEY(" + ID_WORK_WM + ") REFERENCES " + WORK_Table + " (" + ID_WORK + ") ON DELETE CASCADE);")





        if (db != null) {
            val Defalut_FARM = "INSERT INTO Farm_Table(name,x,y) VALUES('PGR_Mazury',0,0);"

            db.execSQL(CRATE_TABEL_FARM)
            db.execSQL(CRATE_TABLE_FIELD)
            db.execSQL(CRATE_TABLE_MACHINE)
            db.execSQL(CRATE_TABLE_USER)
            db.execSQL(CRATE_TABLE_WORK)
            db.execSQL(CRATE_TABLE_WORK_MACHINE)
            db.execSQL(Defalut_FARM)


        }


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        /*
        * We are doing nothing here
        * Just dropping and creating the table
        * */

        val DROP_USERS = "DROP TABLE IF EXISTS " + USER_TABLE + ";"
        val DROP_FIELD = "DROP TABLE IF EXISTS " + FIELD_Table + ";"
        val DROP_MACHINE = "DROP TABLE IF EXISTS " + MASCHINE_Table + ";"
        val DROP_WORK = "DROP TABLE IF EXISTS " + WORK_Table + ";"
        val DROP_FARM = "DROP TABLE IF EXISTS " + Farm_Table + ";"
        val DROP_WM = "DROP TABLE IF EXISTS " + WORK_MACHINE_Table + ";"

        if (db != null) {
            db.execSQL(DROP_USERS)
            db.execSQL(DROP_FIELD)
            db.execSQL(DROP_MACHINE)
            db.execSQL(DROP_WORK)
            db.execSQL(DROP_FARM)
            db.execSQL(DROP_WM)
            onCreate(db)
        }
    }
//----------------------------------//
    //--method to insert data--//
//----------------------------------//

    //==New User
    //////////////////////////////////////////////////////////
    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME_USER, user.nameUser)
        contentValues.put(SURNAME_USER, user.secondNameUser)
        contentValues.put(EMAIL_USER, user.emailUser)
        contentValues.put(PASS_USER, user.passwordUser)
        contentValues.put(TYPE_USER, user.typeUser)
        contentValues.put(ID_FARM_USER, user.idFarm)

        val succes = db.insert(USER_TABLE, null, contentValues)
        db.close()
        return succes
        // succes >-1 its work
    }

    //== New Farm
    /////////////////////////////////////////////////////////////////////
    fun addFarm(farm: Farm): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME_FARM, farm.nameFarm)
        contentValues.put(X_FARM, farm.xCord)
        contentValues.put(Y_FARM, farm.yCord)

        val succes = db.insert(Farm_Table, null, contentValues)
        // db.close()
        return succes
        // succes >-1 its work
    }

    //== New Field
    //////////////////////////////////////////////////////////////
    fun addField(field: Fields): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NAME_FIELD, field.nameField)
        contentValues.put(DSC_FIELD, field.decriptionField)
        contentValues.put(X_FIELD, field.xField)
        contentValues.put(Y_FIELD, field.yField)
        contentValues.put(ID_FARM_FIELD, field.idFarm)


        val succes = db.insert(FIELD_Table, null, contentValues)
        db.close()
        return succes
        // succes >-1 its work
    }

    //== New Machine
    ///////////////////////////////////////////////////////////////
    fun addMachine(machine: Machine): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(BRAND_MACHINE, machine.brandMachine)
        contentValues.put(MODEL_MACHINE, machine.modelMachine)
        contentValues.put(TYPE_MACHINE, machine.typeMachine)
        contentValues.put(CAPACIY_MACHINE, machine.capacityMachine)
        contentValues.put(FUEL_MACHINE, machine.fuelUsageMachine)
        contentValues.put(WEIGHT_MACHINE, machine.weightMachine)
        contentValues.put(WIDTH_MACHINE, machine.widthMachine)
        contentValues.put(X_MACHINE, machine.xCords)
        contentValues.put(Y_MACHINE, machine.yCords)
        contentValues.put(ID_FARM_MACHINE, machine.idFarm)


        val succes = db.insert(MASCHINE_Table, null, contentValues)
        db.close()
        return succes
        // succes >-1 its work
    }

    //== New Work
    ///////////////////////////////////////////////////////////////
    fun addWork(work: Work): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(NAME_WORK, work.nameWork)
        contentValues.put(STATUS_WORK, work.statusWork)
        contentValues.put(START_WORK, work.startTime)
        contentValues.put(STOP_WORK, work.stopTime)
        contentValues.put(ID_USER_WORK, work.idUser)
        contentValues.put(ID_FIELD_WORK, work.idFied)
        contentValues.put(ID_FARM_WORK, work.idFarm)

        val succes = db.insert(WORK_Table, null, contentValues)
        db.close()
        return succes
        // succes >-1 its work
    }

    //== New Machine at work
    ///////////////////////////////////////////////////////////////
    fun addWorkMachine(id_Work: Int, id_Machine: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_MACHIE_WM, id_Machine)
        contentValues.put(ID_WORK_WM, id_Work)


        val succes = db.insert(WORK_MACHINE_Table, null, contentValues)
        db.close()
        return succes
        // succes >-1 its work
    }


//----------------------------------//
    //--method to update data--//
//----------------------------------//

    //== Update user
    ///////////////////////////////////////////////////////////////
    fun updateUser(user: User): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME_USER, user.nameUser)
        contentValues.put(SURNAME_USER, user.secondNameUser)
        contentValues.put(EMAIL_USER, user.emailUser)
        contentValues.put(PASS_USER, user.passwordUser)
        contentValues.put(TYPE_USER, user.typeUser)
        contentValues.put(ID_FARM_USER, user.idFarm)
        // Updating Row
        val succes = db.update(USER_TABLE, contentValues, ID_USER + " = " + user.idUser, null)
        //2nd argument is String containing nullColumnHack
        db.close() //close db
        return succes
        // succes >-1 its work
    }

    //== Update field
    ///////////////////////////////////////////////////////////////////
    fun updateField(field: Fields): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME_FIELD, field.nameField)
        contentValues.put(DSC_FIELD, field.decriptionField)
        contentValues.put(X_FIELD, field.xField)
        contentValues.put(Y_FIELD, field.yField)
        contentValues.put(ID_FARM_FIELD, field.idFarm)
        // Updating Row
        val succes = db.update(FIELD_Table, contentValues, ID_FIELD + " = " + field.idField, null)
        //2nd argument is String containing nullColumnHack
        db.close() //close db
        return succes
        // succes >-1 its work
    }


    //== Update Machine
    //////////////////////////////////////////////////
    fun updateMachine(machine: Machine): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(BRAND_MACHINE, machine.brandMachine)
        contentValues.put(MODEL_MACHINE, machine.modelMachine)
        contentValues.put(TYPE_MACHINE, machine.typeMachine)
        contentValues.put(CAPACIY_MACHINE, machine.capacityMachine)
        contentValues.put(FUEL_MACHINE, machine.fuelUsageMachine)
        contentValues.put(WEIGHT_MACHINE, machine.weightMachine)
        contentValues.put(WIDTH_MACHINE, machine.widthMachine)
        contentValues.put(X_MACHINE, machine.xCords)
        contentValues.put(Y_MACHINE, machine.yCords)
        contentValues.put(ID_FARM_MACHINE, machine.idFarm)
        // Updating Row
        val succes =
            db.update(MASCHINE_Table, contentValues, ID_MASCHINE + " = " + machine.idMachine, null)
        //2nd argument is String containing nullColumnHack
        db.close() //close db
        return succes
        // succes >-1 its work
    }

    //== update Work
    ///////////////////////////////////////////////////////////////
    fun updateWork(work: Work): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(NAME_WORK, work.nameWork)
        contentValues.put(STATUS_WORK, work.statusWork)
        contentValues.put(START_WORK, work.startTime)
        contentValues.put(STOP_WORK, work.stopTime)
        contentValues.put(ID_USER_WORK, work.idUser)
        contentValues.put(ID_FIELD_WORK, work.idFied)
        contentValues.put(ID_FARM_WORK, work.idFarm)

        val succes = db.update(WORK_Table, contentValues, ID_WORK + " = " + work.idWork, null)
        db.close()
        return succes
        // succes >-1 its work
    }

    //== update Farm
    ///////////////////////////////////////////////////////////////
    fun updateFarm(farm: Farm): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(NAME_FARM, farm.nameFarm)
        contentValues.put(X_FARM, farm.xCord)
        contentValues.put(Y_FARM, farm.yCord)


        val succes = db.update(Farm_Table, contentValues, ID_FARM + " = " + farm.idFarm, null)
        db.close()
        return succes
        // succes >-1 its work
    }

    //----------------------------------//
    //--method to delete data--//
//----------------------------------//
    //== dell user
    /////////////////////////////////////////////////////////////////////////////////
    fun delateUser(user: User): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_USER, user.idUser)

        // Delating Row
        val succes = db.delete(USER_TABLE, ID_USER + " = '" + user.idUser + "'", null)
        //2nd argument is String containing nullColumnHack
        Log.e("Status usuwania", succes.toString())
        db.close() //close db
        return succes
        // succes >-1 its work
    }

    //== dell field
    //////////////////////////////////////////////////////////
    fun delateField(field: Fields): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_FIELD, field.idField)

        // Delating Row
        val succes = db.delete(
            FIELD_Table,
            ID_FIELD + "=?",
            arrayOf(field.idField.toString())
        );
        //2nd argument is String containing nullColumnHack
        db.close() //close db
        return succes
        // succes >-1 its work
    }

    //== dell Machine
    //////////////////////////////////////////////////////////////
    fun delateMachine(machine: Machine): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_MASCHINE, machine.idMachine)

        // Delating Row
        val succes = db.delete(
            MASCHINE_Table,
            ID_MASCHINE + "=?",
            arrayOf(machine.idMachine.toString())
        );
        //2nd argument is String containing nullColumnHack
        db.close() //close db
        return succes
        // succes >-1 its work
    }

    //== dell Work
    /////////////////////////////////////////////
    fun delateWork(work: Work): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_WORK, work.idWork)

        // Delating Row
        val succes = db.delete(
            WORK_Table,
            ID_WORK + "=?",
            arrayOf(work.idWork.toString())
        );
        //2nd argument is String containing nullColumnHack
        db.close() //close db
        return succes
        // succes >-1 its work
    }

    //== dell Work Machichine
    /////////////////////////////////////////////
    fun delateWorkmachine(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_WORK_MACHINE, id)

        // Delating Row
        val succes = db.delete(
            WORK_MACHINE_Table,
            ID_WORK_MACHINE + "=?",
            arrayOf(id.toString())
        );
        //2nd argument is String containing nullColumnHack
        db.close() //close db
        return succes
        // succes >-1 its work
    }

    //== dell Farm
    /////////////////////////////////////////////
    fun delateFarm(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_FARM, id)

        // Delating Row
        val succes = db.delete(
            Farm_Table,
            ID_FARM + "=?",
            arrayOf(id.toString())
        );
        //2nd argument is String containing nullColumnHack
        db.close() //close db
        return succes
        // succes >-1 its work
    }


    //== dell Work Machichine by Work
    /////////////////////////////////////////////
    fun delateWorkMachineByIdWork(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_WORK_WM, id)

        // Delating Row
        val succes = db.delete(
            WORK_MACHINE_Table,
            ID_WORK_WM + "=?",
            arrayOf(id.toString())
        );
        //2nd argument is String containing nullColumnHack
        db.close() //close db
        return succes
        // succes >-1 its work
    }

    //== dell Work Machichine by Work
    /////////////////////////////////////////////
    fun delateWorkMachineByIdMachine(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_MACHIE_WM, id)

        // Delating Row
        val succes = db.delete(
            WORK_MACHINE_Table,
            ID_MACHIE_WM + "=?",
            arrayOf(id.toString())
        );
        //2nd argument is String containing nullColumnHack
        db.close() //close db
        return succes
        // succes >-1 its work
    }

//----------------------------------//
    //--method to read data--//
//----------------------------------//

    //== ALL user list
    ///////////////////////////////////////////////////
    fun viewUserList(): List<User> {
        val empList: ArrayList<User> = ArrayList<User>()
        val selectQuery = "SELECT  * FROM $USER_TABLE"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var idUser: Int
        var nameUser: String
        var secondNameUser: String
        var emailUser: String
        var passwordUser: String
        var typeUser: String
        var idFarm: Int
        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    idUser = cursor.getInt(cursor.getColumnIndexOrThrow(ID_USER))
                    nameUser = cursor.getString(cursor.getColumnIndexOrThrow(NAME_USER))
                    secondNameUser = cursor.getString(cursor.getColumnIndexOrThrow(SURNAME_USER))
                    emailUser = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL_USER))
                    passwordUser = cursor.getString(cursor.getColumnIndexOrThrow(PASS_USER))
                    typeUser = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_USER))
                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_USER))

                    val emp =
                        User(
                            idFarm,
                            nameUser,
                            secondNameUser,
                            emailUser,
                            passwordUser,
                            typeUser,
                            idFarm
                        )
                    empList.add(emp)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return empList
    }


    //== ALL Fields list
    /////////////////////////////////////////////////////////////
    fun viewFieldsList(): List<Fields> {
        val empList: ArrayList<Fields> = ArrayList<Fields>()
        val selectQuery = "SELECT  * FROM $FIELD_Table"
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }

        var id: Int
        var name: String
        var desc: String
        var x: Double
        var y: Double
        var idFarm: Int
        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM))
                    name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_FIELD))
                    desc = cursor.getString(cursor.getColumnIndexOrThrow(DSC_FIELD))
                    x = cursor.getDouble(cursor.getColumnIndexOrThrow(X_FIELD))
                    y = cursor.getDouble(cursor.getColumnIndexOrThrow(Y_FIELD))
                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_USER))

                    val emp =
                        Fields(id, name, desc, x, y, idFarm)
                    empList.add(emp)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return empList
    }

    //== ALL Fields list
    /////////////////////////////////////////////////////////////
    fun viewFieldsFarmList(id: Int): List<Fields> {
        val empList: ArrayList<Fields> = ArrayList<Fields>()
        val selectQuery = "SELECT  * FROM $FIELD_Table WHERE $ID_FARM_FIELD =$id"
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }

        var id: Int
        var name: String
        var desc: String
        var x: Double
        var y: Double
        var idFarm: Int
        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM))
                    name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_FIELD))
                    desc = cursor.getString(cursor.getColumnIndexOrThrow(DSC_FIELD))
                    x = cursor.getDouble(cursor.getColumnIndexOrThrow(X_FIELD))
                    y = cursor.getDouble(cursor.getColumnIndexOrThrow(Y_FIELD))
                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_USER))

                    val emp =
                        Fields(id, name, desc, x, y, idFarm)
                    empList.add(emp)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return empList
    }


    //== ALL Machine list
    //////////////////////////////////////////////////////////////////
    fun viewMachineList(): List<Machine> {
        val empList: ArrayList<Machine> = ArrayList<Machine>()
        val selectQuery = "SELECT  * FROM $MASCHINE_Table"
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }

        var id: Int
        var brand: String
        var model: String
        var type: String
        var capacty: Int
        var fuel: Int
        var weight: Int
        var width: Int
        var x: Double
        var y: Double
        var idFarm: Int
        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM))
                    brand = cursor.getString(cursor.getColumnIndexOrThrow(BRAND_MACHINE))
                    model = cursor.getString(cursor.getColumnIndexOrThrow(MODEL_MACHINE))
                    type = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_MACHINE))

                    capacty = cursor.getInt(cursor.getColumnIndexOrThrow(CAPACIY_MACHINE))
                    fuel = cursor.getInt(cursor.getColumnIndexOrThrow(FUEL_MACHINE))
                    weight = cursor.getInt(cursor.getColumnIndexOrThrow(WEIGHT_MACHINE))
                    width = cursor.getInt(cursor.getColumnIndexOrThrow(WIDTH_MACHINE))

                    x = cursor.getDouble(cursor.getColumnIndexOrThrow(X_MACHINE))
                    y = cursor.getDouble(cursor.getColumnIndexOrThrow(Y_MACHINE))

                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_MACHINE))

                    val emp =
                        Machine(id, brand, model, type, capacty, fuel, weight, width, x, y, idFarm)
                    empList.add(emp)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return empList
    }

    //== ALL Machine list on one farm
    //////////////////////////////////////////////////////////////////
    fun viewMachineOnFarmList(id: Int): List<Machine> {
        val empList: ArrayList<Machine> = ArrayList<Machine>()
        val selectQuery = "SELECT  * FROM $MASCHINE_Table WHERE $ID_FARM_MACHINE = '$id'"
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }

        var id: Int
        var brand: String
        var model: String
        var type: String
        var capacty: Int
        var fuel: Int
        var weight: Int
        var width: Int
        var x: Double
        var y: Double
        var idFarm: Int
        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM))
                    brand = cursor.getString(cursor.getColumnIndexOrThrow(BRAND_MACHINE))
                    model = cursor.getString(cursor.getColumnIndexOrThrow(MODEL_MACHINE))
                    type = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_MACHINE))

                    capacty = cursor.getInt(cursor.getColumnIndexOrThrow(CAPACIY_MACHINE))
                    fuel = cursor.getInt(cursor.getColumnIndexOrThrow(FUEL_MACHINE))
                    weight = cursor.getInt(cursor.getColumnIndexOrThrow(WEIGHT_MACHINE))
                    width = cursor.getInt(cursor.getColumnIndexOrThrow(WIDTH_MACHINE))

                    x = cursor.getDouble(cursor.getColumnIndexOrThrow(X_MACHINE))
                    y = cursor.getDouble(cursor.getColumnIndexOrThrow(Y_MACHINE))

                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_MACHINE))

                    val emp =
                        Machine(id, brand, model, type, capacty, fuel, weight, width, x, y, idFarm)
                    empList.add(emp)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return empList
    }


    //== ALL Work list
    //////////////////////////////////////////////////////////////////
    fun viewWorkList(): List<Work> {
        val empList: ArrayList<Work> = ArrayList<Work>()
        val selectQuery = "SELECT  * FROM $WORK_Table"
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }

        var id: Int
        var name: String
        var status: String
        var startString: String
        var stopString: String
        var idUser: Int
        var idField: Int
        var idFarm: Int

        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_WORK))
                    name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_WORK))
                    status = cursor.getString(cursor.getColumnIndexOrThrow(STATUS_WORK))
                    startString = cursor.getString(cursor.getColumnIndexOrThrow(START_WORK))
                    stopString = cursor.getString(cursor.getColumnIndexOrThrow(STOP_WORK))
                    idUser = cursor.getInt(cursor.getColumnIndexOrThrow(ID_USER_WORK))
                    idField = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FIELD_WORK))
                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_WORK))

                    var starDate: java.util.Date? = null
                    var stopDate: java.util.Date? = null
                    if (startString != null && startString != null) {
                        val emp =
                            Work(id, name, status, startString, stopString, idUser, idField, idFarm)
                        empList.add(emp)

                    }
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return empList
    }

    fun parseTEXTtoDate(time: String): Date {
        val iso8601Format: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = iso8601Format.parse(time)
        return date
    }


    //== ALL Work list Od One Farm ==//
    /////////////////////////////////////////////////////////////////////////////
    fun viewWorkOfOneFarmList(id: Int): List<Work> {
        val empList: ArrayList<Work> = ArrayList<Work>()
        val selectQuery = "SELECT  * FROM $WORK_Table  WHERE $ID_FARM_WORK  = '$id'"
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }

        var id: Int
        var name: String
        var status: String
        var startString: String
        var stopString: String
        var idUser: Int
        var idField: Int

        var idFarm: Int

        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_WORK))
                    name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_WORK))
                    status = cursor.getString(cursor.getColumnIndexOrThrow(STATUS_WORK))
                    startString = cursor.getString(cursor.getColumnIndexOrThrow(START_WORK))
                    stopString = cursor.getString(cursor.getColumnIndexOrThrow(STOP_WORK))
                    idUser = cursor.getInt(cursor.getColumnIndexOrThrow(ID_USER_WORK))
                    idField = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FIELD_WORK))

                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_WORK))

                    val emp =
                        Work(id, name, status, startString, stopString, idUser, idField, idFarm)
                    empList.add(emp)

                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return empList
    }

    //== ALL user list at Farm
    ///////////////////////////////////////////////////
    fun viewUserOfOneFarmList(id: Int): List<User> {
        val empList: ArrayList<User> = ArrayList<User>()
        val selectQuery = "SELECT  * FROM $USER_TABLE WHERE $ID_FARM_USER = '$id'"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var idUser: Int
        var nameUser: String
        var secondNameUser: String
        var emailUser: String
        var passwordUser: String
        var typeUser: String
        var idFarm: Int
        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    idUser = cursor.getInt(cursor.getColumnIndexOrThrow(ID_USER))
                    nameUser = cursor.getString(cursor.getColumnIndexOrThrow(NAME_USER))
                    secondNameUser = cursor.getString(cursor.getColumnIndexOrThrow(SURNAME_USER))
                    emailUser = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL_USER))
                    passwordUser = cursor.getString(cursor.getColumnIndexOrThrow(PASS_USER))
                    typeUser = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_USER))
                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_USER))

                    val emp =
                        User(
                            idFarm,
                            nameUser,
                            secondNameUser,
                            emailUser,
                            passwordUser,
                            typeUser,
                            idFarm
                        )
                    empList.add(emp)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return empList
    }


    ////////////////////////////////////////
    //         get one row of data        //
    ////////////////////////////////////////


    //== get one user be id ==//
    ////////////////////////////////////////////////////////////////////////////////////////
    fun getOneUserByID(id: Int?): User? {
        val selectQuery = "SELECT  * FROM " + USER_TABLE + " WHERE " + ID_USER + " = '" + id + "'"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("SQL STATUS", e.printStackTrace().toString())
        }

        var idUser: Int
        var nameUser: String
        var secondNameUser: String
        var emailUser: String
        var passwordUser: String
        var typeUser: String
        var idFarm: Int
        var user: User? = null
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    idUser = cursor.getInt(cursor.getColumnIndexOrThrow(ID_USER))
                    nameUser = cursor.getString(cursor.getColumnIndexOrThrow(NAME_USER))
                    secondNameUser = cursor.getString(cursor.getColumnIndexOrThrow(SURNAME_USER))
                    emailUser = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL_USER))
                    passwordUser = cursor.getString(cursor.getColumnIndexOrThrow(PASS_USER))
                    typeUser = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_USER))
                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_USER))

                    user = User(
                        idUser,
                        nameUser,
                        secondNameUser,
                        emailUser,
                        passwordUser,
                        typeUser,
                        idFarm
                    )
                } while (cursor.moveToNext())
            }
        }
        if (cursor != null) {
            cursor.close()
        }
        db.close()
        return user
    }

    //== get one user be  email
    /////////////////////////////////////////////////////////////////////////////////////////////////
    fun viewUser(email: String, pass: String): User? {
        val selectQuery =
            "SELECT  * FROM " + USER_TABLE + " WHERE " + EMAIL_USER + " = '" + email.trim() + "'"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("SQL STATUS", e.printStackTrace().toString())
        }

        var idUser: Int
        var nameUser: String
        var secondNameUser: String
        var emailUser: String
        var passwordUser: String
        var typeUser: String
        var idFarm: Int
        var user: User? = null
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    idUser = cursor.getInt(cursor.getColumnIndexOrThrow(ID_USER))
                    nameUser = cursor.getString(cursor.getColumnIndexOrThrow(NAME_USER))
                    secondNameUser = cursor.getString(cursor.getColumnIndexOrThrow(SURNAME_USER))
                    emailUser = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL_USER))
                    passwordUser = cursor.getString(cursor.getColumnIndexOrThrow(PASS_USER))
                    typeUser = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_USER))
                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_USER))

                    if (passwordUser.equals(pass)) {
                        user = User(
                            idUser,
                            nameUser,
                            secondNameUser,
                            emailUser,
                            passwordUser,
                            typeUser,
                            idFarm
                        )
                    }
                } while (cursor.moveToNext())
            }
        }
        if (cursor != null) {
            cursor.close()
        }
        db.close()
        return user
    }

    //== get One Machine By ID ==//
    ///////////////////////////////////////////////////////////////////////////////////
    fun getOneMachineByID(id: Int): Machine? {

        val selectQuery = "SELECT  * FROM $MASCHINE_Table WHERE $ID_MASCHINE = $id"
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }

        var id: Int
        var brand: String
        var model: String
        var type: String
        var capacty: Int
        var fuel: Int
        var weight: Int
        var width: Int
        var x: Double
        var y: Double
        var idFarm: Int
        var machine: Machine? = null
        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM))
                    brand = cursor.getString(cursor.getColumnIndexOrThrow(BRAND_MACHINE))
                    model = cursor.getString(cursor.getColumnIndexOrThrow(MODEL_MACHINE))
                    type = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_MACHINE))

                    capacty = cursor.getInt(cursor.getColumnIndexOrThrow(CAPACIY_MACHINE))
                    fuel = cursor.getInt(cursor.getColumnIndexOrThrow(FUEL_MACHINE))
                    weight = cursor.getInt(cursor.getColumnIndexOrThrow(WEIGHT_MACHINE))
                    width = cursor.getInt(cursor.getColumnIndexOrThrow(WIDTH_MACHINE))

                    x = cursor.getDouble(cursor.getColumnIndexOrThrow(X_MACHINE))
                    y = cursor.getDouble(cursor.getColumnIndexOrThrow(Y_MACHINE))

                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_MACHINE))

                    machine =
                        Machine(id, brand, model, type, capacty, fuel, weight, width, x, y, idFarm)

                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return machine
    }

    //== get all Machine By work ==//
    ///////////////////////////////////////////////////////////////////////////////////
    fun getAllMachineByWork(id: Int): List<Machine>? {
        // val selectQuery = "SELECT  * FROM $MASCHINE_Table WHERE $ID_MASCHINE = $id"
        val empList: ArrayList<Machine> = ArrayList<Machine>()
        val selectQuery = "SELECT * FROM   Machine_Table  LEFT JOIN WORK_MACHINE_Table ON " +
                " Machine_Table.id= WORK_MACHINE_Table.id_machine WHERE WORK_MACHINE_Table.id_work=$id"
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }

        var id: Int
        var brand: String
        var model: String
        var type: String
        var capacty: Int
        var fuel: Int
        var weight: Int
        var width: Int
        var x: Double
        var y: Double
        var idFarm: Int
        var machine: Machine? = null
        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM))
                    brand = cursor.getString(cursor.getColumnIndexOrThrow(BRAND_MACHINE))
                    model = cursor.getString(cursor.getColumnIndexOrThrow(MODEL_MACHINE))
                    type = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_MACHINE))

                    capacty = cursor.getInt(cursor.getColumnIndexOrThrow(CAPACIY_MACHINE))
                    fuel = cursor.getInt(cursor.getColumnIndexOrThrow(FUEL_MACHINE))
                    weight = cursor.getInt(cursor.getColumnIndexOrThrow(WEIGHT_MACHINE))
                    width = cursor.getInt(cursor.getColumnIndexOrThrow(WIDTH_MACHINE))

                    x = cursor.getDouble(cursor.getColumnIndexOrThrow(X_MACHINE))
                    y = cursor.getDouble(cursor.getColumnIndexOrThrow(Y_MACHINE))

                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_MACHINE))

                    machine =
                        Machine(id, brand, model, type, capacty, fuel, weight, width, x, y, idFarm)
                    empList.add(machine)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return empList
    }

    //== Get One Fields by ID
    ///////////////////////////////////////////////////////////
    fun getOneFieldsByID(id: Int): Fields? {

        val selectQuery = "SELECT  * FROM $FIELD_Table WHERE $ID_FIELD = '$id'"
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }
        var id: Int
        var name: String
        var desc: String
        var x: Double
        var y: Double
        var idFarm: Int
        var field: Fields? = null
        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM))
                    name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_FIELD))
                    desc = cursor.getString(cursor.getColumnIndexOrThrow(DSC_FIELD))
                    x = cursor.getDouble(cursor.getColumnIndexOrThrow(X_FIELD))
                    y = cursor.getDouble(cursor.getColumnIndexOrThrow(Y_FIELD))
                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_USER))

                    field = Fields(id, name, desc, x, y, idFarm)

                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return field
    }

    //==  Get One Work by ID==//
    /////////////////////////////////////////////////////////////////////////////
    fun getOneWorkByID(id: Int): Work? {
        val selectQuery = "SELECT  * FROM $WORK_Table  WHERE $ID_WORK  = '$id'"
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }

        var id: Int
        var name: String
        var status: String
        var startString: String
        var stopString: String
        var idUser: Int
        var idField: Int

        var idFarm: Int
        var work: Work? = null
        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_WORK))
                    name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_WORK))
                    status = cursor.getString(cursor.getColumnIndexOrThrow(STATUS_WORK))
                    startString = cursor.getString(cursor.getColumnIndexOrThrow(START_WORK))
                    stopString = cursor.getString(cursor.getColumnIndexOrThrow(STOP_WORK))
                    idUser = cursor.getInt(cursor.getColumnIndexOrThrow(ID_USER_WORK))
                    idField = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FIELD_WORK))

                    idFarm = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM_WORK))

                    work = Work(id, name, status, startString, stopString, idUser, idField, idFarm)

                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return work
    }

    //==  Get One Farm by ID==//
    /////////////////////////////////////////////////////////////////////////////
    fun getOneFarmByID(id: Int): Farm? {
        val selectQuery = "SELECT  * FROM $Farm_Table  WHERE $ID_FARM  = '$id'"
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }

        var id: Int
        var name: String
        var x: Double
        var y: Double
        var farm: Farm? = null

        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM))
                    name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_FARM))
                    x = cursor.getDouble(cursor.getColumnIndexOrThrow(X_FARM))
                    y = cursor.getDouble(cursor.getColumnIndexOrThrow(Y_FARM))
                    farm = Farm(id, name, x, y)

                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return farm
    }

    /////////////////////////////////////////////////////////////////////////////
    fun getAllFarms(): List<Farm>? {
        val selectQuery = "SELECT  * FROM $Farm_Table  "
        var list: ArrayList<Farm> = ArrayList<Farm>()
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }

        var id: Int
        var name: String
        var x: Double
        var y: Double
        var farm: Farm? = null

        if (cursor!!.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_FARM))
                    name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_FARM))
                    x = cursor.getDouble(cursor.getColumnIndexOrThrow(X_FARM))
                    y = cursor.getDouble(cursor.getColumnIndexOrThrow(Y_FARM))
                    farm = Farm(id, name, x, y)
                    list.add(farm)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return list
    }

    //==  Get One Work by ID==//
    /////////////////////////////////////////////////////////////////////////////
    fun getLastIndexOfWork(): Int? {
        val selectQuery = "SELECT * FROM $WORK_Table "
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // db.execSQL(selectQuery)
            // return ArrayList()
        }

        var id: Int? = null
        if (cursor != null) {
            cursor.moveToLast()
            id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_WORK))
        }

        cursor?.close()
        db.close()
        return id
    }

}