package xmlparser.core.element

/**
 * Represents an attribute that can be assigned to a xml element.
 */
data class XmlElementAttribute(var name: String, var value: String)
{
    init{
        require(name != ""){"Attribute name cannot be empty."}
    }
}

