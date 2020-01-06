import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArgsTest {

    @Test
    fun testInput() {
        val schema = Schema("l:boolean p:int d:string")
        val input = InputParameter().parse("-l -p 8080 -d /usr/logs")

        val arg = Args().parse(schema, input)
        assertEquals(true, arg.getArg("l"))
        assertEquals(8080, arg.getArg("p"))
        assertEquals("/usr/logs", arg.getArg("d"))
    }
}