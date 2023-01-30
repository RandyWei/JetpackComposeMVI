package icu.bughub.app.todo.utils

import kotlin.random.Random

object RandomUtil {

    private val CHARS = arrayOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "0")

    fun randomString(len:Int = 6):String{
        val randStr = StringBuilder()
        for (i in 0..len) {
            randStr.append(CHARS.random(Random(System.currentTimeMillis())))
        }
        return randStr.toString()
    }
}