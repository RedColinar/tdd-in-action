data class Car(var rect: Rect, var x: Int, var y: Int, var dir: Int) {

    fun exec(commands: List<Command>): Report {
        var message = ""
        for (command in commands) {
            if (!command.run(rect, this)) {
                message = "error"
                break
            }
        }
        return Report(x, y, dir, message)
    }
}
