package xmlparser.core

class InvalidXmlElementException(message: String): Exception(message)
class InvalidXmlAnnotationException(annotationName: String, message: String): Exception("$annotationName: $message")
class InvalidXmlAnnotationTypeException(annotationName: String, type: String): Exception("$annotationName: $type")