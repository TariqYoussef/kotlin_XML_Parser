package xml.utils

fun createFilledString(size: Int, char: Char): String
{
    var string: String = ""
    for(i in 0 until size) string += char
    return string;
}