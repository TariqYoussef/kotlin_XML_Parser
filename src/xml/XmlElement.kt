package xml

import xml.utils.createFilledString

class XmlElement(private val name: String, private val value: Any = "")
{
    private val children: MutableList<XmlElement> = mutableListOf()

    private val attributes: MutableList<XmlAttribute> = mutableListOf()

    fun addChild(xmlElement: XmlElement) = children.add(xmlElement)

    fun addAttribute(xmlAttribute: XmlAttribute) = attributes.add(xmlAttribute)

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

