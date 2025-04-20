import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.equals.shouldBeEqual
import org.example.JavaVersion
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class JavaVersionTest {
    @Nested
    inner class `validかどうか調べる(「JDK{数値}u{数値}」という形式のみがvalidである)` {
        @Test
        fun `「JDK7u40」はvalidである`() {
            JavaVersion.isValid("JDK7u40") shouldBeEqual true
        }

        @Test
        fun `「hoge」はvalidじゃない`() {
            JavaVersion.isValid("hoge") shouldBeEqual false
        }

        @Test
        fun `「JDK7u9x」はvalidじゃない`() {
            JavaVersion.isValid("JDK7u9x") shouldBeEqual false
        }
    }

    @Nested
    inner class parseする {
        val version = "JDK7u40"
        val target = JavaVersion.parse(version)

        @Test
        fun `「JDK7u40」を表すオブジェクトを返す`() {
            target shouldBeEqual JavaVersion(version)
        }

        @Test
        fun バージョン番号からfamilyNumberを返す() {
            target.familyNumber() shouldBeEqual 7
        }

        @Test
        fun バージョン番号からupdateNumberを返す() {
            target.updateNumber() shouldBeEqual 40
        }

        @Test
        fun バージョン番号が不正な場合は例外を投げる() {
            shouldThrowExactly<IllegalArgumentException> {
                JavaVersion.parse("JDK7u9x")
            }
        }
    }

    @Nested
    inner class バージョンを大小比較する {
        val u40 = JavaVersion.parse("JDK7u40")
        val u51 = JavaVersion.parse("JDK7u51")
        val jdk8u0 = JavaVersion.parse("JDK8u0")

        @Test
        fun `「JDK7u40はJDK7u51小さい」はtrue`() {
            u40.lesserThan(u51) shouldBeEqual true
        }

        @Test
        fun `「JDK7u40はJDK7u51より大きい」はfalse`() {
            u40.greaterThan(u51) shouldBeEqual false
        }

        @Test
        fun `「JDK7u40はJDK8u0より小さい」はtrue`() {
            u40.lesserThan(jdk8u0) shouldBeEqual true
        }

        @Test
        fun `「JDK8u0はJDK7u51より大きい」はtrue`() {
            jdk8u0.greaterThan(u51) shouldBeEqual true
        }
    }

    @Nested
    inner class 次のバージョンを計算する {
        val u45 = JavaVersion.parse("JDK7u45")
//        u45 = Version.parse("JDK7u45")
//
//        u60 = u45.nextLimitedUpdate()
//        u60.updateNumber #=> 60
//
//        u51 = u45.nextCriticalPatchUpdate()
//        u51.updateNumber #=> 51
//
//        u46 = u45.nextSecurityAlert()
//        u46.updateNumber #=> 46
//        ## next～メソッドはすべて、新たなバージョンを表すオブジェクトを返す
//        ## （selfのバージョンを変えて返すのではない）
        @Test
        fun `「JDK7u45」の次のLimitedUpdateは「JDK7u60」`() {
            val u60 = u45.nextLimitedUpdate()
            u60.updateNumber() shouldBeEqual 60
        }

        @Test
        fun `「JDK7u45」の次のCriticalPatchUpdateは「JDK7u51」`() {
            val u51 = u45.nextCriticalPatchUpdate()
            u51.updateNumber() shouldBeEqual 51
        }

        @Test
        fun `「JDK7u45」の次のSecurityAlertは「JDK7u46」`() {
            TODO()
        }
    }
}