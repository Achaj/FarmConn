package com.example.farmconn

import com.example.farmconn.Objects.User


class HelperUser(user: User) {
    companion object {
        private var user: User? = null

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

    }
}