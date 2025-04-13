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
        // TODO: これを通すためにテスト対象を data class にしたんだけど それでいーんだっけ・・・？
        @Test
        fun `「JDK7u40」を表すオブジェクトを返す`() {
            val version = "JDK7u40"
            Version.parse(version) shouldBeEqual Version(version)
        }

        @Test
        fun バージョン番号からfamilyNumberを返す() {
            val target = Version.parse("JDK7u40")
            target.familyNumber() shouldBeEqual "7"
        }

        @Test
        fun バージョン番号からupdateNumberを返す() {
            val target = Version.parse("JDK7u40")
            target.updateNumber() shouldBeEqual "40"
        }
    }
}