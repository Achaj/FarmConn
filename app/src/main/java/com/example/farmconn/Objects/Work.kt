package com.example.farmconn.Objects

import java.io.Serializable
import java.util.*

class Work(
    val idWork: Int,
    var nameWork: String,
    var statusWork: String,
    var startTime: String? = null,
    var stopTime: String? = null,
    var idUser: Int? = null,
    var idFied: Int? = null,
    val idFarm: Int
) : Serializable {

}