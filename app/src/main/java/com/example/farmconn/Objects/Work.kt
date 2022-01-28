package com.example.farmconn.Objects

import java.util.*

class Work(
    val idWork: Int,
    val nameWork:String,
    val statusWork: String,
    val startTime: Date,
    val stopTime: Date,
    val idUser: Int,
    val idFied:Int,
    val idMachine: Int
) {
}