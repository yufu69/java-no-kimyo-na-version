package org.example

// TODO: なんか「プログラムで正規表現使うのはいけない」って聞いた気がするけどどうなんだろう…
data class Version(val version: String) {
    companion object {
        fun isValid(version: String): Boolean {
            return version.matches("^JDK[0-9]+u[0-9]+$".toRegex())
        }

        fun parse(version: String): Version {
            return Version(version)
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
}