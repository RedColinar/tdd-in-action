interface Command {
    fun run(rect: Rect, car: Car): Boolean
}