package com.example.farmconn.Objects

import java.io.Serializable

class User(
    val idUser: Int,
    var nameUser: String,
    var secondNameUser: String,
    var emailUser: String,
    var passwordUser: String,
    val typeUser: String,
    var idFarm: Int
) : Serializable {
    constructor() : this(0, "", "", "", "", "", 0)

}