package com.example.farmconn.Objects

import java.sql.Time

class Work(
    val idWork: Int,
    val nameWork:String,
    val statusWork: String,
    val startTime:Time,
    val stopTime: Time,
    val idUser: Int,
    val idFied:Int,
    val idMachine: Int
) {
}