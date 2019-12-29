class Main {
    private val fizz = object : Matcher {
        override fun isMatch(value: Int): Boolean =
            value % 3 == 0 || value.toString().toCharArray().contains('3')

        override fun result(): String = "fizz"
    }
    private val buzz = object : Matcher {
        override fun isMatch(value: Int): Boolean =
            value % 5 == 0 || value.toString().toCharArray().contains('5')

        override fun result(): String = "buzz"
    }
    private val fizzBuzz = object : Matcher {
        override fun isMatch(value: Int): Boolean = fizz.isMatch(value) && buzz.isMatch(value)
        override fun result(): String = "fizzbuzz"
    }
    private val matchers = listOf(fizzBuzz, fizz, buzz)

    fun match(value: Int): String {
        for (matcher in matchers) {
            if (matcher.isMatch(value)) {
                return matcher.result()
            }
        }
        return value.toString()
    }
}

fun main() {
    val main = Main()
    for (i in 1..100) {
        println(main.match(i))
    }
}

interface Matcher {
    fun isMatch(value: Int): Boolean
    fun result(): String
}