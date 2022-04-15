package xml

import xml.element.XmlElement
import xml.utils.isArray
import xml.element.XmlElementAttribute as XmlElementAttribute
import xml.utils.isBasicType
import xml.utils.isCollection
import xml.utils.isEnum
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

typealias XmlElementAttributeAnnotation = xml.XmlElementAttribute

/**
 * Represents a Xml context.
 */
class XmlContext(version: String = "1.0", encoding: String = "UTF-8", standalone: String = "no")
{
    private val xmlHeader: XmlHeader = XmlHeader(version, encoding, standalone)
    private val xmlElements: MutableList<XmlElement> = mutableListOf()

    /**
     * Adds a xml element to the context.
     */
    fun addXmlElement(element: Any) {
        val kClass: KClass<out Any> = element::class

        val xmlElement = createXmlElement(kClass, element)

        addXmlElementChildren(kClass, element, xmlElement)

        addXmlElement(xmlElement)
    }

    /**
     * Adds a xml element to the context.
     */
    fun addXmlElement(xmlElement: XmlElement)
    {
        xmlElements.add(xmlElement)
    }

    /**
     * Dumps the context.
     */
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

        return createXmlElement(kClass, element, elementName)
    }

    private fun createXmlElement(kClass: KClass<out Any>, element: Any, elementName: String): XmlElement
    {
        val memberProperties = kClass.declaredMemberProperties.filter {
            !it.hasAnnotation<XmlElementIgnore>()
        }

        val elementContent = memberProperties.filter{
            it.hasAnnotation<XmlElementContent>()
        }

        val xmlElement = if(elementContent.isNotEmpty()) {
            if(elementContent.size > 1)
                throw InvalidXmlAnnotationException("XmlElementContent", "Can't be used more than one time in one class")

            if(elementContent[0].hasAnnotation<XmlElementAttributeAnnotation>())
                throw InvalidXmlAnnotationException("XmlElementContent", "Can't be used with XmlElementAttribute")

            if(elementContent[0].hasAnnotation<XmlElementName>())
                throw InvalidXmlAnnotationException("XmlElementContent", "Can't be used with XmlElementName")

            XmlElement(elementName, elementContent[0].call(element)!!)
        } else {
            XmlElement(elementName)
        }

        val elementAttributes = memberProperties.filter{
            it.hasAnnotation<XmlElementAttributeAnnotation>()}

        elementAttributes.forEach{
            if(it.hasAnnotation<XmlElementContent>())
                throw InvalidXmlAnnotationException("XmlElementAttribute", "Can't be used with XmlElementContent")

            if(it.call(element) == null) return@forEach

            val elementAttributeName: String = if(it.hasAnnotation<XmlElementName>())
            {
                if(it.findAnnotation<XmlElementName>()?.name == null)
                    throw InvalidXmlAnnotationException("XmlElementChildName", "Invalid name")

                it.findAnnotation<XmlElementName>()?.name!!
            }else
            {
                it.name
            }

            if(!isBasicType(it.call(element)!!) && !isEnum(it.call(element)!!))
                throw InvalidXmlAnnotationTypeException("XmlElementAttributeAnnotation",
                    "An Attribute must be a basic type or an enum and not a/an ${it.call(element)!!::class.qualifiedName}")

            val xmlElementAttribute = XmlElementAttribute(elementAttributeName, it.call(element)!!)
            xmlElement.addAttribute(xmlElementAttribute)
        }

        return xmlElement
    }

    private fun addXmlElementChildren(kClass: KClass<out Any>, element: Any, xmlElement: XmlElement)
    {
        val properties = kClass.declaredMemberProperties.filter{
            !it.hasAnnotation<XmlElementContent>() &&
            !it.hasAnnotation<XmlElementIgnore>() &&
            !it.hasAnnotation<XmlElementAttributeAnnotation>()
        }
        properties.forEach{
            val elementChildName: String = if(it.hasAnnotation<XmlElementName>())
            {
                if(it.findAnnotation<XmlElementName>()?.name == null)
                    throw InvalidXmlAnnotationException("XmlElementChildName", "Invalid name")

                it.findAnnotation<XmlElementName>()?.name!!
            }else
            {
                it.name
            }

            if(it.call(element) == null)
            {
                val xmlElementChild = XmlElement(elementChildName)
                xmlElement.addChild(xmlElementChild)
                return@forEach
            }

            if(isBasicType(it.call(element)!!) || isEnum(it.call(element)!!))
            {
                val xmlElementChild = XmlElement(elementChildName, it.call(element)!!)
                xmlElement.addChild(xmlElementChild)
            }
            else if(isArray(it.call(element)!!) || isCollection(it.call(element)!!))
            {
                val xmlElementChild = XmlElement(elementChildName)
                val elementChild: Iterable<Any> = if(isArray(it.call(element)!!))
                {
                    val elementArrayChild: Array<Any> = it.call(element)!! as Array<Any>
                    elementArrayChild.asList()
                }
                else
                {
                    it.call(element)!! as Iterable<Any>
                }

                elementChild.forEach {
                    if(isBasicType(it) || isEnum(it))
                    {
                        val xmlElementItem = XmlElement("item", it)
                        xmlElementChild.addChild(xmlElementItem)
                    }
                    else
                    {
                        val kClassChild: KClass<out Any> = it::class
                        val xmlElementItem: XmlElement = createXmlElement(kClassChild, it, "item")
                        addXmlElementChildren(kClassChild, it, xmlElementItem)
                        xmlElementChild.addChild(xmlElementItem)
                    }
                }
                xmlElement.addChild(xmlElementChild)
            }
            else
            {
                val elementChild: Any = it.call(element)!!
                val kClassChild: KClass<out Any> = elementChild::class
                val xmlElementChild: XmlElement = createXmlElement(kClassChild, elementChild, elementChildName)
                addXmlElementChildren(kClassChild, elementChild, xmlElementChild)
                xmlElement.addChild(xmlElementChild)
            }
        }
    }

}

//TODO: IntArray, ShortArray, etc Support