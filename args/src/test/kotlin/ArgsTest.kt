import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ArgsTest {

    @Test
    fun testSchema() {
        val schemaText = "l:boolean p:int d:string"
        val schema = Schema(schemaText)
        assertEquals(Boolean::class.java, schema.getClass("l"))
        assertEquals(Int::class.java, schema.getClass("p"))
        assertEquals(String::class.java, schema.getClass("d"))
    }

    @ParameterizedTest
    @CsvSource("-l -p 8080 -d /usr/logs")
    fun testRealInput(args: String) {
        val kv: Map<String, String> = InputParser.parse(args)
        assertEquals("", kv["l"])
        assertEquals("8080", kv["p"])
        assertEquals("/usr/logs", kv["d"])
    }

    @ParameterizedTest
    @CsvSource("-l -p 8080 -d /usr/logs")
    fun testInput(args: String) {
        val schemaText = "l:boolean p:int d:string"
        val schema = Schema(schemaText)

        val kv: Map<String, String> = InputParser.parse(args)

        val arg = Args.parse(schema, kv)
        assertEquals(true, arg.getArg("l"))
        assertEquals(8080, arg.getArg("p"))
        assertEquals("/usr/logs", arg.getArg("d"))
    }
}