import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RoverTest {

    @Test
    fun testRect() {
        val rect = Rect(100, 100)

        assertEquals(false, rect.isInRect(-1, -1))
        assertEquals(false, rect.isInRect(101, 0))
        assertEquals(false, rect.isInRect(0, 101))
        assertEquals(true, rect.isInRect(50, 50))
    }

    @ParameterizedTest
    @CsvSource(
        "100,100,50,50,0,50,50,0,''",
        "100,100,0,0,0,50,0,0,'error'",
        "100,100,50,50,1,51,50,100,'error'",
        "100,100,0,0,2,150,100,0,'error'",
        "100,100,0,0,2,-150,0,0,'error'",
        "100,100,0,0,3,-150,100,0,'error'"
    )
    fun testForwardCommand(w: Int, h: Int, x: Int, y: Int, dir: Int, distance: Int, rx: Int, ry: Int, message: String) {
        val rect = Rect(w, h)
        val car = Car(rect, x, y, dir)
        val report = car.exec(listOf(ForwardCommand(distance)))

        assertEquals(rx, report.x)
        assertEquals(ry, report.y)
        assertEquals(dir, report.dir)
        assertEquals(message, report.message)
    }

    @ParameterizedTest
    @CsvSource(
        "0,0,3",
        "1,0,2",
        "2,0,0",
        "3,0,1",
        "0,1,2",
        "1,1,3",
        "2,1,1",
        "3,1,0"
    )
    fun testTurnDirection(curDir: Int, leftOrRight: Int, dir: Int) {
        val report = Car(Rect(0, 0), 0, 0, curDir).exec(listOf(TurnCommand(leftOrRight)))
        assertEquals(dir, report.dir)
    }

    @Test
    fun testComposeCommand() {
        val commands = listOf(
            TurnCommand(0),
            ForwardCommand(50),
            TurnCommand(1),
            ForwardCommand(-50)
        )

        val rect = Rect(100, 100)
        val car = Car(rect, 50, 50, 0)
        val report = car.exec(commands)

        assertEquals(0, report.x)
        assertEquals(100, report.y)
        assertEquals(0, report.dir)
    }
}