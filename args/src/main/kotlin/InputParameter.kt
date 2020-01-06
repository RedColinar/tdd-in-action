class InputParameter {

    private val parameters = HashMap<String, String>()

    fun parse(input: String): InputParameter {
        parameters.clear()
        val metas: List<String> = input.trim().split(Regex("\\s+"))

        val iterator: ListIterator<String> = metas.listIterator()
        while (iterator.hasNext()) {
            val literal = iterator.next()
            if (!isValue(literal)) {
                val key = literal.substring(1)
                var value: String
                if (iterator.hasNext()) {
                    value = iterator.next()
                } else {
                    parameters[key] = ""
                    break
                }

                if (isValue(value)) {
                    parameters[key] = value
                } else if (iterator.hasPrevious()) {
                    iterator.previous()
                    parameters[key] = ""
                } else {
                    parameters[key] = ""
                }
            }
        }

        return this
    }

    private fun isValue(literal: String): Boolean {
        return if (literal.startsWith("-")) {
            literal.length > 1 && Regex("[0-9]").matches(literal[1].toString())
        } else {
            true
        }
    }

    operator fun get(name: String): String? {
        return parameters[name]
    }
}
