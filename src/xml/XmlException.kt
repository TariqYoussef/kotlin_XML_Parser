package xml

class InvalidXmlElementException(message: String): Exception(message)
class InvalidXmlAnnotationException(annotationName: String, message: String): Exception("$annotationName: $message")