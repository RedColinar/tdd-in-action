class TurnCommand(private val leftOrRight: Int) : Command {
    override fun run(rect: Rect, car: Car): Boolean {
        car.dir = when (car.dir) {
            DIR_N -> if (leftOrRight == LEFT) DIR_W else DIR_E
            DIR_S -> if (leftOrRight == LEFT) DIR_E else DIR_W
            DIR_E -> if (leftOrRight == LEFT) DIR_N else DIR_S
            DIR_W -> if (leftOrRight == LEFT) DIR_S else DIR_N
            else -> throw IllegalArgumentException("Unsupported Direction")
        }

        return true
    }
}