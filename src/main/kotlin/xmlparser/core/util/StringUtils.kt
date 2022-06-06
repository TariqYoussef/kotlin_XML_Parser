package xmlparser.core.util

fun createFilledString(size: Int, char: Char): String
{
    var string = ""
    for(i in 0 until size) string += char
    return string
}