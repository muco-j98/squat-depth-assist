package com.muco.squatdepthassist.utils

object HelperFunctions {

    fun concat(vararg strings: String): String {
        return strings.fold("") { acc, next -> "$acc$next" }
    }
}