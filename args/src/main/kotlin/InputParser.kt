class InputParser {

    companion object {
        fun parse(input: String): MutableMap<String, String> {
            val map = HashMap<String, String>()
            val metas: List<String> = input.trim().split("-")
            for (meta in metas) {
                val kv: List<String> = meta.trim().split(" ")
                when (kv.size) {
                    1 -> map[kv[0]] = ""
                    2 -> map[kv[0]] = kv[1]
                    else -> throw IllegalArgumentException("Unsupported parameter $meta")
                }
            }

            return map
        }
    }
}
