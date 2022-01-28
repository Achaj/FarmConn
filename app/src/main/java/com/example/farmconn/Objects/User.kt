package com.example.farmconn.Objects

class User(
    val idUser: Int,
    val nameUser:String,
    val secondNameUser:String,
    val emailUser: String,
    val passwordUser:String,
    val typeUser:String,
    val idFarm:Int
) {
    constructor() : this(0,"","","","","",0)

}