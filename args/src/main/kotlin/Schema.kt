class Schema(schemaText: String) {
    private val map = HashMap<String, Class<*>>()

    init {
        val metas: List<String> = schemaText.trim().split(" ")
        for (meta in metas) {
            val type = meta.split(":")
            if (type.size != 2) throw IllegalArgumentException("schema should be like <name1>:<type1> <name2>:<type2>")
            map[type[0]] = getClassByType(type[1])
        }
    }

    private fun getClassByType(type: String): Class<*> {
        return when (type) {
            "boolean" -> Boolean::class.java
            "int" -> Int::class.java
            "string" -> String::class.java
            else -> throw IllegalArgumentException("Unsupported type $type")
        }
    }

    fun parameters(): Set<String> {
        return map.keys
    }

    fun getClass(key: String): Class<*> {
        return if (map.containsKey(key)) {
            map[key]!!
        } else {
            throw IllegalArgumentException("Schema doesn't contain key $key")
        }
    }
}
