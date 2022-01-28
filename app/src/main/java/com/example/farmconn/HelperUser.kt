package com.example.farmconn

import com.example.farmconn.Objects.Fields
import com.example.farmconn.Objects.Machine
import com.example.farmconn.Objects.User


class HelperUser(user: User) {
    companion object {
        private var user: User? = null
        private var fieldWork: Fields? = null
        private var machineWork:Machine? = null
        private var userWork: User? = null


        //----------------------------------//
            //User who logiin the app//
        //----------------------------------//
        //Logged user
        fun getCurrentUser(): User? {
            if (user == null) {
                println("nie jesteś zalogowany")
            }
            return user
        }

        fun setCurrentUser(user1: User): User? {
            user = user1
            return user
        }
        fun setCurrentUserNull() {
            user=null

        }

        //----------------------------------//
                //curen field to doing work//
        //----------------------------------//

        // get field to do work
        fun getCurrentFieldWork(): Fields? {
            if (fieldWork == null) {
                println("nie jesteś zalogowany")
            }
            return fieldWork
        }
        // chose machine to do work
        fun setCurrentFieldWork(fields: Fields): Fields? {
            this.fieldWork=fields
            return fieldWork
        }
        // set null
        fun setCurrentFieldWorkNull() {
            fieldWork=null

        }


        //----------------------------------//
                //curen user who do work//
        //----------------------------------//
        // get user to do work
        fun getCurrentUserWork(): User? {
            if (user == null) {
                println("nie jesteś zalogowany")
            }
            return userWork
        }
        // chose user to do work
        fun setCurrentUserWork(user1: User): User? {
            userWork = user1
            return user
        }
        // set null
        fun setCurrentUserWorkNull() {
            userWork=null

        }


        //----------------------------------//
        //curen Machine who do work//
        //----------------------------------//
        // get user to do work
        fun getCurrentMACHINEeWork(): Machine? {
            if (machineWork == null) {
                println("nie jesteś zalogowany")
            }
            return machineWork
        }
        // chose user to do work
        fun setCurrentMACHINEork(machine: Machine): Machine? {
           this.machineWork=machine
            return machineWork
        }
        // set null
        fun setCurrentMachineWorkNull() {
            machineWork=null

        }

    }
}