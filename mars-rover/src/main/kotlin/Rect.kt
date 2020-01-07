data class Rect(val w: Int, val h: Int) {

    fun isInRect(x: Int, y: Int): Boolean = isInX(x) && isInY(y)

    private fun isInX(x: Int): Boolean = x in 0..w

    private fun isInY(y: Int): Boolean = y in 0..h

    fun resetX(x: Int): Int {
        if (x > w) return w
        if (x < 0) return 0
        return x
    }

    fun resetY(y: Int): Int {
        if (y > h) return h
        if (y < 0) return 0
        return y
    }
}

const val DIR_N = 0
const val DIR_S = 1
const val DIR_E = 2
const val DIR_W = 3

const val LEFT = 0
const val RIGHT = 1
