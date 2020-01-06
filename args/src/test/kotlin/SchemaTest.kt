import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class SchemaTest {

    @Test
    fun testNormalFunction() {
        val schema = Schema(" a:int b:string   c:boolean")

        assertEquals(Int::class.java, schema.getClass("a"))
        assertEquals(String::class.java, schema.getClass("b"))
        assertEquals(Boolean::class.java, schema.getClass("c"))

        assertThrows(IllegalArgumentException::class.java) {
            schema.getClass("no exist")
        }
    }

    @Test
    fun testAbnormalInput() {
        assertThrows(IllegalArgumentException::class.java) {
            Schema("a:Int b:unknownType")
        }
    }
}