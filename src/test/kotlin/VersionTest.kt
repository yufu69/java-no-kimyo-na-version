import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.equals.shouldBeEqual
import org.example.Version
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class VersionTest {
    @Nested
    inner class `validかどうか調べる(「JDK{数値}u{数値}」という形式のみがvalidである)` {
        @Test
        fun `「JDK7u40」はvalidである`() {
            Version.isValid("JDK7u40") shouldBeEqual true
        }

        @Test
        fun `「hoge」はvalidじゃない`() {
            Version.isValid("hoge") shouldBeEqual false
        }

        @Test
        fun `「JDK7u9x」はvalidじゃない`() {
            Version.isValid("JDK7u9x") shouldBeEqual false
        }
    }

    @Nested
    inner class parseする {
        val version = "JDK7u40"
        val target = Version.parse(version)

        // TODO: これを通すためにテスト対象を data class にしたんだけど それでいーんだっけ・・・？
        @Test
        fun `「JDK7u40」を表すオブジェクトを返す`() {
            target shouldBeEqual Version(version)
        }

        @Test
        fun バージョン番号からfamilyNumberを返す() {
            target.familyNumber() shouldBeEqual "7"
        }

        @Test
        fun バージョン番号からupdateNumberを返す() {
            target.updateNumber() shouldBeEqual "40"
        }

        // Version.parse("JDK7u9x") #=> 例外
        @Test
        fun バージョン番号が不正な場合は例外を投げる() {
            shouldThrowExactly<IllegalArgumentException> {
                Version.parse("JDK7u9x")
            }
        }
    }

    //    u40 = Version.parse("JDK7u40")
//    u51 = Version.parse("JDK7u51")
//    jdk8u0 = Version.parse("JDK8u0")
//
//    u40.lt(u51)      #=> true
//    u40.gt(u51)      #=> false
//    u40.lt(jdk8u0)   #=> true
//    jdk8u0.gt(u51)   #=> true
//    #          ↑ 言語に応じて演算子オーバーロード等はご自由に
    @Nested
    inner class バージョンを大小比較する {
        val u40 = Version.parse("JDK7u40")
        val u51 = Version.parse("JDK7u51")
        val jdk8u0 = Version.parse("JDK8u0")

        @Test
        fun `JDK7u40はJDK7u51より小さい`() {
            TODO()
        }
    }
}