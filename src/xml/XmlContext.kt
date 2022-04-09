package xml

import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties


class XmlContext(version: String = "1.0", encoding: String = "UTF-8", standalone: String = "no")
{
    private val xmlHeader: XmlHeader = XmlHeader(version, encoding, standalone)
    private val xmlElements: MutableList<XmlElement> = mutableListOf()

    inline fun <reified T : Any>addXmlElement(element: T) {
        val kClass: KClass<T> = T::class
        val propertyValues: List<Pair<String, Any?>> = kClass.declaredMemberProperties.map { Pair(it.name, it.call(element)) }

        if(kClass.simpleName == null)
            throw InvalidXmlElementException("Class doesn't have a name")
        val xmlElement: XmlElement = XmlElement(kClass.simpleName!!)

        for(propertyValue in propertyValues)
        {
            if(propertyValue.second != null)
            {
                val propertyXmlElement: XmlElement = XmlElement(propertyValue.first, propertyValue.second!!)
                xmlElement.addChild(propertyXmlElement)
            }
            else
            {
                val propertyXmlElement: XmlElement = XmlElement(propertyValue.first)
                xmlElement.addChild(propertyXmlElement)
            }
        }

        addXmlElement(xmlElement)
    }

    fun addXmlElement(xmlElement: XmlElement)
    {
        xmlElements.add(xmlElement)
    }

    fun dump(intent: Int = -1): String
    {
        var content: String = xmlHeader.dump() + if(intent > -1) "\n" else ""

        for(element in xmlElements)
            content += element.dump(intent)

        return content
    }
}