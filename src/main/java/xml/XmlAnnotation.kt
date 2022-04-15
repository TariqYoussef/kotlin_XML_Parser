package xml

/**
 * Name of the element
 */
annotation class XmlElementName(val name: String)

/**
 * Content of the element.
 */
annotation class XmlElementContent

/**
 * This element will be ignored.
 */
annotation class XmlElementIgnore

/**
 * Attribute of the element.
 */
annotation class XmlElementAttribute