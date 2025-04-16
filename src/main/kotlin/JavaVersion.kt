package org.example

// TODO: なんか「プログラムで正規表現使うのはいけない」って聞いた気がするけどどうなんだろう…
data class JavaVersion(val version: String) {
    companion object {
        fun isValid(version: String): Boolean {
            return version.matches("^JDK[0-9]+u[0-9]+$".toRegex())
        }

        fun parse(version: String): JavaVersion {
            return if (isValid(version)) JavaVersion(version) else throw IllegalArgumentException()
        }
    }

    fun familyNumber(): String {
        return version
            .removePrefix("JDK")
            .replace("u[0-9]+".toRegex(), "")
    }

    fun updateNumber(): String {
        return version.replace("JDK[0-9]+u".toRegex(), "")
    }

    fun lesserThan(other: JavaVersion): Boolean {
        return this.updateNumber() < other.updateNumber()
    }

    fun greaterThan(other: JavaVersion): Boolean {
        return this.updateNumber() > other.updateNumber()
    }
}