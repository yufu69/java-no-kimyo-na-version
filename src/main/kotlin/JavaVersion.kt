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

    fun familyNumber(): Int {
        return version
            .removePrefix("JDK")
            .substringBefore("u")
            .toInt()
    }

    fun updateNumber(): Int {
        return version.substringAfter("u").toInt()
    }

    fun lesserThan(other: JavaVersion): Boolean {
        return if (this.familyNumber() < other.familyNumber()) true
        else this.updateNumber() < other.updateNumber()
    }

    fun greaterThan(other: JavaVersion): Boolean {
        return if (this.familyNumber() > other.familyNumber()) true
        else this.updateNumber() > other.updateNumber()
    }

    fun nextLimitedUpdate(): JavaVersion {
        val nextUpdateNumber = ((updateNumber() / 20) + 1) * 20

        return parse("JDK${familyNumber()}u${nextUpdateNumber}")
    }

    fun nextCriticalPatchUpdate(): JavaVersion {
        val nextMultipleOf5 = ((updateNumber() / 5) + 1) * 5
        val nextUpdateNumber =
            nextMultipleOf5.takeIf { it % 2 == 1 }
                ?: (nextMultipleOf5 + 1)

        return parse("JDK${familyNumber()}u${nextUpdateNumber}")
    }

    fun nextSecurityAlert(): JavaVersion {
        val nextNumber = updateNumber() + 1
        val nextLimitedNumber = nextLimitedUpdate().updateNumber()
        val nextCriticalPatchNumber = nextCriticalPatchUpdate().updateNumber()

//        while ((nextNumber == nextLimitedNumber) || (nextNumber == nextCriticalPatchNumber)) {
//            nextNumber++
//        }
//
//        return parse("JDK${familyNumber()}u${nextNumber}")

        val resultNumber = when(nextNumber) {
            nextLimitedNumber, nextCriticalPatchNumber -> nextNumber + 1
            else -> nextNumber
        }

        return parse("JDK${familyNumber()}u${resultNumber}")
    }
}