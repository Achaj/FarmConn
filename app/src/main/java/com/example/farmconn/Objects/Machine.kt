package com.example.farmconn.Objects

import java.io.Serializable

class Machine(
    val idMachine: Int,
    var brandMachine: String,
    var modelMachine: String,
    var typeMachine: String,
    var capacityMachine: Int,
    var fuelUsageMachine: Int,
    var weightMachine: Int,
    var widthMachine: Int,
    var xCords: Double,
    var yCords: Double,
    val idFarm: Int

) : Serializable