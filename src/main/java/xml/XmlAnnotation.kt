package xml

/**
 * Name of the element in the xml tag.
 */
annotation class XmlElementName(val name: String)

/**
 * Content of the element in the xml tag.
 */
annotation class XmlElementContent

/**
 * This element will be ignored.
 */
annotation class XmlElementIgnore

/**
 * Attribute of the element in the xml tag.
 */
annotation class XmlElementAttribute