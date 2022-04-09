package xml.utils

fun isBasicType(element: Any): Boolean
{
    return (element is Int || element is Short || element is Byte || element is Long
            || element is Double || element is Float
            || element is UInt || element is UShort || element is UByte || element is ULong
            || element is Boolean || element is String)
}