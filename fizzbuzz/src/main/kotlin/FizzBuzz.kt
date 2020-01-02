class FizzBuzz {
    companion object {
        fun of(value: Int): String {
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