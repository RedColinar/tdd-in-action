import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class MainTest {

    @ParameterizedTest(name = "input {0} should return {1}")
    @CsvSource(
        "1, '1'",
        "3, 'fizz'",
        "5, 'buzz'"
    )
    fun test(input: Int, word: String) {
        assertEquals(word, FizzBuzz.of(input))
    }
}

