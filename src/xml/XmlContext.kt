package xml

import xml.utils.isBasicType
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class XmlContext(version: String = "1.0", encoding: String = "UTF-8", standalone: String = "no")
{
    private val xmlHeader: XmlHeader = XmlHeader(version, encoding, standalone)
    private val xmlElements: MutableList<XmlElement> = mutableListOf()

    fun addXmlElement(element: Any) {
        val kClass: KClass<out Any> = element::class

        val xmlElement = createXmlElement(kClass, element)

        addXmlElementChildren(kClass, element, xmlElement)

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

    private fun createXmlElement(kClass: KClass<out Any>, element: Any): XmlElement
    {
        val elementName: String = if(kClass.hasAnnotation<XmlElementName>())
        {
            if(kClass.findAnnotation<XmlElementName>()?.name == null)
                throw InvalidXmlAnnotationException("XmlElementName", "Invalid name")

            kClass.findAnnotation<XmlElementName>()?.name!!
        } else {
            if(kClass.simpleName == null)
                throw InvalidXmlElementException("Class doesn't have a name")

            kClass.simpleName!!
        }

        val elementContent = kClass.declaredMemberProperties.filter{it.hasAnnotation<XmlElementContent>() }

        return if(elementContent.isNotEmpty()) {
            if(elementContent.size > 1)
                throw InvalidXmlAnnotationException("XmlElementContent", "Can't be used more than one time in one class")

            XmlElement(elementName, elementContent[0].call(element)!!)
        } else {
            XmlElement(elementName)
        }
    }

    private fun addXmlElementChildren(kClass: KClass<out Any>, element: Any, xmlElement: XmlElement)
    {
        val properties = kClass.declaredMemberProperties.filter{!it.hasAnnotation<XmlElementContent>() && !it.hasAnnotation<XmlIgnore>()}
        properties.forEach{
            if(it.call(element) != null)
            {
                if(isBasicType(it.call(element)!!))
                {
                    val propertyXmlElement = XmlElement(it.name, it.call(element)!!)
                    xmlElement.addChild(propertyXmlElement)
                }
                else
                {
                    val elementChild: Any = it.call(element)!!
                    val kClassChild: KClass<out Any> = elementChild::class
                    val xmlElementChild: XmlElement = createXmlElement(kClassChild, elementChild)
                    addXmlElementChildren(kClassChild, elementChild, xmlElementChild)
                    xmlElement.addChild(xmlElementChild)
                }
            }
            else
            {
                val propertyXmlElement = XmlElement(it.name)
                xmlElement.addChild(propertyXmlElement)
            }
        }
    }

}