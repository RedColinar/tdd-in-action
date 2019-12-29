import junit.framework.Assert.assertEquals
import org.junit.Test

class MainTest {
    @Test
    fun test_raw_number() {
        assertEquals("1", Gamer.toString(1))
    }

    @Test
    fun test_3_number() {
        assertEquals("fizz", Gamer.toString(3))
    }

    @Test
    fun test_5_number() {
        assertEquals("buzz", Gamer.toString(5))
    }

    @Test
    fun test_3_5() {
        assertEquals("fizzbuzz", Gamer.toString(15))
    }

    @Test
    fun testEquals() {
        val main = Main()
        for (i in 1..300) {
            assertEquals("问题在 $i", main.match(i), Gamer.toString(i))
        }
    }
}

