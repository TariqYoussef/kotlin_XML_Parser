package xml.utils

fun isBasicType(element: Any): Boolean = (element is Number
        || element is UInt || element is UShort || element is UByte || element is ULong
        || element is Boolean || element is String)

fun isEnum(element: Any): Boolean = element is Enum<*>

fun isArray(element: Any): Boolean = element is Array<*>

fun isCollection(element: Any): Boolean = element is Collection<*>