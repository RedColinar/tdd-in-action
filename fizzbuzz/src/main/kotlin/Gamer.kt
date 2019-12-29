class Gamer {
    companion object {
        fun toString(value: Int): String {
            if (isRelateTo(value, 3) && isRelateTo(value, 5)) return "fizzbuzz"
            if (isRelateTo(value, 3)) return "fizz"
            if (isRelateTo(value, 5)) return "buzz"
            return value.toString()
        }

        private fun isRelateTo(value: Int, relate: Int): Boolean {
            if (value % relate == 0 || value.toString().contains(relate.toString())) return true
            return false
        }
    }
}

fun main() {
    for (i in 1..100) {
        println(Gamer.toString(i))
    }
}
