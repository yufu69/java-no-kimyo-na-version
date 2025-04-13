import io.kotest.matchers.equals.shouldBeEqual
import org.example.Version
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class VersionTest {
    val target = Version()

    @Nested
    inner class `validかどうか調べる(「JDK{数値}u{数値}」という形式のみがvalidである)` {
        @Test
        fun `「JDK7u40」はvalidである`() {
            target.isValid("JDK7u40") shouldBeEqual true
        }

        @Test
        fun `「hoge」はvalidじゃない`() {
            target.isValid("hoge") shouldBeEqual false
        }

        @Test
        fun `「JDK7u9x」はvalidじゃない`() {
            target.isValid("JDK7u9x") shouldBeEqual false
        }
    }
}