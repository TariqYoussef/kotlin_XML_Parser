package xml

class InvalidXmlElementException(message: String): Exception(message)
class InvalidXmlAnnotationException(annotationName: String): Exception("Annotation invalid: $annotationName")