package xml.element

import xml.utils.createFilledString

/**
 * Represents a xml context that can be assigned to a context or can be part of a context.
 */
class XmlElement(private val name: String, private val value: Any = "")
{
    private val children: MutableList<XmlElement> = mutableListOf()

    private val attributes: MutableList<XmlElementAttribute> = mutableListOf()

    /**
     * Adds a child to the xml element.
     */
    fun addChild(xmlElement: XmlElement) = children.add(xmlElement)

    /**
     * Adds an attribute to the xml element.
     */
    fun addAttribute(xmlElementAttribute: XmlElementAttribute) = attributes.add(xmlElementAttribute)

    /**
     * Dumps the xml element.
     */
    fun dump(intent: Int = -1, intentOffset: Int = 0): String
    {
        var content = "<$name"

        for(attribute in attributes)
            content += " ${attribute.name}=\"${attribute.value}\""

        content += ">$value"

        if(children.isNotEmpty())
        {
            content += if(intent > -1) "\n" else ""
            val spacing: String = createFilledString(intent + intentOffset, ' ')

            for(child in children)
            {
                content += spacing
                content += child.dump(intent, intent + intentOffset)
            }

            content += createFilledString(intentOffset, ' ')
        }

        content += "</$name>" + if(intent > -1) "\n" else ""

        return content
    }
}

