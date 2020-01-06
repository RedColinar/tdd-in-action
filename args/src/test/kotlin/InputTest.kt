import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InputTest {
    @Test
    fun testNormal() {
        val input = InputParameter().parse("-l -p 8080 -d /usr/logs -n -9 -s")

        assertEquals("", input["l"])
        assertEquals("8080", input["p"])
        assertEquals("/usr/logs", input["d"])
        assertEquals("-9", input["n"])
        assertEquals("", input["s"])
        assertEquals(null, input["no exist"])
    }
}