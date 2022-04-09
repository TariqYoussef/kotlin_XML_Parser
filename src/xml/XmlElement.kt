package xml

class XmlElement(private val name: String, private val value: Any = "")
{
    private val children: MutableList<XmlElement> = mutableListOf()

    private val attributes: MutableList<XmlAttribute> = mutableListOf()

    fun addChild(xmlElement: XmlElement) = children.add(xmlElement)

    fun addAttribute(xmlAttribute: XmlAttribute) = attributes.add(xmlAttribute)

    fun dump(intent: Int = -1, intentOffset: String = ""): String
    {
        var content: String = "<$name"

        for(attribute in attributes)
            content+=" ${attribute.name}=\"${attribute.value}\""

        content+=">$value" + if(intent > -1) "\n" else ""

        var spacing: String = intentOffset
        if(intent > 0) for(i in 0..intent) spacing += " "

        for(child in children)
        {
            content += spacing
            content += child.dump(intent, intentOffset + spacing)
        }

        content += intentOffset
        content += "</$name>" + if(intent > -1) "\n" else ""

        return content
    }
}