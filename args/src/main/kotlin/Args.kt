class Args {

    fun parse(schema: Schema, input: InputParameter): Args {
        val args = Args()
        schema.parameters().forEach {
            val clazz: Class<*> = schema.getClass(it)
            val value: String = input[it] ?: ""
            args.map[it] = getArgument(clazz, value)
        }

        return args
    }

    private fun getArgument(clazz: Class<*>, value: String): Any {
        return when (clazz) {
            Int::class.java -> value.toInt()
            Boolean::class.java -> {
                if (value == "") {
                    true
                } else {
                    value.toBoolean()
                }
            }
            String::class.java -> value
            else -> clazz.newInstance()
        }
    }

    private val map = HashMap<String, Any>()

    fun getArg(name: String): Any {
        if (map.containsKey(name)) {
            return map[name]!!
        } else {
            throw IllegalArgumentException("Args doesn't contain parameter $name")
        }
    }
}
