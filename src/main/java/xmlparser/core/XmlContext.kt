package xmlparser.core

import xmlparser.core.element.XmlElement
import xmlparser.core.utils.*
import xmlparser.core.element.XmlElementAttribute as XmlElementAttribute
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.isAccessible

typealias XmlElementAttributeAnnotation = xmlparser.core.XmlElementAttribute

/**
 * Represents a Xml context.
 */
class XmlContext(version: String = "1.0", encoding: String = "UTF-8", standalone: String = "no") : Visitable {
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
    fun addXmlElement(xmlElement: XmlElement) {
        xmlElements.add(xmlElement)
    }

    /**
     * Dumps the context.
     */
    fun dump(intent: Int = -1): String {
        var content: String = xmlHeader.dump() + if (intent > -1) "\n" else ""

        for (element in xmlElements)
            content += element.dump(intent)

        return content
    }

    override fun accept(visitor: Visitor) {
        if(visitor.visit(this))
            xmlElements.forEach {
                it.accept(visitor)
            }
        visitor.endVisit(this)
    }

    private fun createXmlElement(kClass: KClass<out Any>, element: Any): XmlElement {
        val elementName: String = if (kClass.hasAnnotation<XmlElementName>()) {
            if (kClass.findAnnotation<XmlElementName>()?.name == null)
                throw InvalidXmlAnnotationException("XmlElementName", "Invalid name")

            kClass.findAnnotation<XmlElementName>()?.name!!
        } else {
            if (kClass.simpleName == null)
                throw InvalidXmlElementException("Class doesn't have a name")

            kClass.simpleName!!
        }

        return createXmlElement(kClass, element, elementName)
    }

    private fun createXmlElement(kClass: KClass<out Any>, element: Any, elementName: String): XmlElement {
        val memberProperties = kClass.declaredMemberProperties.filter {
            !it.hasAnnotation<XmlElementIgnore>()
        }

        val elementContent = memberProperties.filter {
            it.hasAnnotation<XmlElementContent>()
        }

        val xmlElement = if (elementContent.isNotEmpty()) {
            if (elementContent.size > 1)
                throw InvalidXmlAnnotationException(
                    "XmlElementContent",
                    "Can't be used more than one time in one class"
                )

            if (elementContent[0].hasAnnotation<XmlElementAttributeAnnotation>())
                throw InvalidXmlAnnotationException("XmlElementContent", "Can't be used with XmlElementAttribute")

            if (elementContent[0].hasAnnotation<XmlElementName>())
                throw InvalidXmlAnnotationException("XmlElementContent", "Can't be used with XmlElementName")

            XmlElement(elementName, elementContent[0].call(element)!!)
        } else {
            XmlElement(elementName)
        }

        val elementAttributes = memberProperties.filter {
            it.hasAnnotation<XmlElementAttributeAnnotation>()
        }

        elementAttributes.forEach {
            if (it.hasAnnotation<XmlElementContent>())
                throw InvalidXmlAnnotationException("XmlElementAttribute", "Can't be used with XmlElementContent")

            if (it.call(element) == null) return@forEach

            val elementAttributeName: String = if (it.hasAnnotation<XmlElementName>()) {
                if (it.findAnnotation<XmlElementName>()?.name == null)
                    throw InvalidXmlAnnotationException("XmlElementChildName", "Invalid name")

                it.findAnnotation<XmlElementName>()?.name!!
            } else {
                it.name
            }

            if (!isBasicType(it.call(element)!!) && !isEnum(it.call(element)!!))
                throw InvalidXmlAnnotationTypeException(
                    "XmlElementAttributeAnnotation",
                    "An Attribute must be a basic type or an enum and not a/an ${it.call(element)!!::class.qualifiedName}"
                )

            val xmlElementAttribute = XmlElementAttribute(elementAttributeName, it.call(element)!!)
            xmlElement.addAttribute(xmlElementAttribute)
        }

        return xmlElement
    }

    private fun addXmlElementChildren(kClass: KClass<out Any>, element: Any, xmlElement: XmlElement) {
        val properties = kClass.declaredMemberProperties.filter {
            !it.hasAnnotation<XmlElementContent>() &&
                    !it.hasAnnotation<XmlElementIgnore>() &&
                    !it.hasAnnotation<XmlElementAttributeAnnotation>()
        }
        properties.forEach {
            it.isAccessible = true
            val elementChildName: String = if (it.hasAnnotation<XmlElementName>()) {
                if (it.findAnnotation<XmlElementName>()?.name == null)
                    throw InvalidXmlAnnotationException("XmlElementChildName", "Invalid name")

                it.findAnnotation<XmlElementName>()?.name!!
            } else {
                it.name
            }

            if (it.call(element) == null) {
                val xmlElementChild = XmlElement(elementChildName)
                xmlElement.addChild(xmlElementChild)
                return@forEach
            }

            assignElementChildConversionType(xmlElement, it.call(element)!!, elementChildName)
        }
    }

    private fun assignElementChildConversionType(xmlElement: XmlElement, elementChild: Any, elementChildName: String)
    {
        if (isBasicType(elementChild) || isEnum(elementChild)) {
            val xmlElementChild = XmlElement(elementChildName, elementChild)
            xmlElement.addChild(xmlElementChild)
        }
        else if (isArray(elementChild) || isCollection(elementChild)) {
            val xmlElementChild = addXmlElementChildCollection(elementChild, elementChildName)
            xmlElement.addChild(xmlElementChild)
        }
        else if(isMap(elementChild))
        {
            val xmlElementChild = addXmlElementChildMap(elementChild, elementChildName)
            xmlElement.addChild(xmlElementChild)
        }
        else {
            val xmlElementChild = addXmlElementChildAnother(elementChild, elementChildName)
            xmlElement.addChild(xmlElementChild)
        }
    }

    private fun addXmlElementChildCollection(element: Any, elementChildName: String): XmlElement
    {
        val xmlElementChild = XmlElement(elementChildName)
        val elementChild: Iterable<Any> = if (isArray(element)) {
            val elementArrayChild: Array<Any> = element as Array<Any>
            elementArrayChild.asList()
        } else {
            element as Iterable<Any>
        }

        elementChild.forEach {
            assignElementChildConversionType(xmlElementChild, it, "item")
        }

        return xmlElementChild
    }

    private fun addXmlElementChildMap(element: Any, elementChildName: String): XmlElement
    {
        val xmlElementChild = XmlElement(elementChildName)
        val elementChild: Map<Any, Any> = element as Map<Any, Any>
        elementChild.forEach{
            val xmlElementItem = XmlElement("item")
            assignElementChildConversionType(xmlElementItem, it.key, "key")
            assignElementChildConversionType(xmlElementItem, it.value, "value")
            xmlElementChild.addChild(xmlElementItem)
        }
        return xmlElementChild
    }

    private fun addXmlElementChildAnother(element: Any, elementChildName: String): XmlElement
    {
        val kClassChild: KClass<out Any> = element::class
        val xmlElementChild: XmlElement = createXmlElement(kClassChild, element, elementChildName)
        addXmlElementChildren(kClassChild, element, xmlElementChild)
        return xmlElementChild
    }

}

//TODO: IntArray, ShortArray, etc Support