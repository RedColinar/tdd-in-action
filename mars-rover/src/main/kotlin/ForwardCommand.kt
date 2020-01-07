class ForwardCommand(private val distance: Int) : Command {
    override fun run(rect: Rect, car: Car): Boolean {
        var tx = car.x
        var ty = car.y
        when (car.dir) {
            DIR_N -> ty -= distance
            DIR_E -> tx += distance
            DIR_S -> ty += distance
            DIR_W -> tx -= distance
        }

        val stopped: Boolean
        stopped = rect.isInRect(tx, ty)
        car.x = rect.resetX(tx)
        car.y = rect.resetY(ty)

        return stopped
    }
}