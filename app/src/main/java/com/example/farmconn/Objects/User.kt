package com.example.farmconn.Objects

import java.io.Serializable

class User(
    val idUser: Int,
    val nameUser:String,
    val secondNameUser:String,
    val emailUser: String,
    val passwordUser:String,
    val typeUser:String,
    val idFarm:Int
) : Serializable {
    constructor() : this(0,"","","","","",0)

}