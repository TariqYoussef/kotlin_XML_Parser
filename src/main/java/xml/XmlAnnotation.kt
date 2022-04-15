package xml

/**
 * Name of the element in the xml tag.
 */
annotation class XmlElementName(val name: String)

/**
 * Name of the element child in the xml tag.
 */
annotation class XmlElementChildName(val name: String)

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