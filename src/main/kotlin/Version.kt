package org.example

class Version {
    fun isValid(version: String): Boolean {
        return version.matches(Regex("^JDK[0-9]+u[0-9]+$"))
    }
}