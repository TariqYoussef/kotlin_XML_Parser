package xmlparser.core

import xmlparser.core.element.XmlElement
import xmlparser.core.type.createXmlElement
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
class XmlContext(version: String = "1.0", encoding: String = "UTF-8", standalone: String = "no") : IVisitable {
    private val xmlHeader: XmlHeader = XmlHeader(version, encoding, standalone)
    private var rootXmlElement: XmlElement? = null

    private var observers: MutableList<(XmlElement) -> Unit> = mutableListOf()
    /**
     * Sets root of the context.
     */
    fun setRootXmlElement(element: Any) {
        val kClass: KClass<out Any> = element::class

        if (kClass.simpleName == null)
            throw InvalidXmlElementException("Class doesn't have a name")

        val xmlElement = createXmlElement(element, kClass.simpleName!!)

        rootXmlElement = xmlElement
    }

    /**
     * Sets root of the context.
     */
    fun setRootXmlElement(xmlElement: XmlElement) {
        rootXmlElement = xmlElement
        observers.forEach {
            rootXmlElement!!.addObserverToAllChildren(it)
        }
        rootXmlElement!!.notifyObservers { it(rootXmlElement!!) }
    }
    //TODO solve: cannot redo remove root
    /**
     * Clears Xml elements in the context.
     */
    fun clearXmlElements() {
        rootXmlElement = null
    }

    /**
     * Dumps the context.
     */
    fun dump(intent: Int = -1): String {
        var content: String = xmlHeader.dump() + if (intent > -1) "\n" else ""

        if (rootXmlElement != null)
            content += rootXmlElement!!.dump(intent)

        return content
    }

    /**
     * Gets Xml Header.
     */
    fun xmlHeader() = xmlHeader

    /**
     * Gets root Xml Element (parent)
     */
    fun root() = rootXmlElement

    /**
     * Deep copies XmlContext.
     */
    fun deepCopy(): XmlContext
    {
        val clonedXmlContext = XmlContext(xmlHeader.version, xmlHeader.encoding, xmlHeader.standalone)
        if (rootXmlElement != null)
            clonedXmlContext.rootXmlElement = rootXmlElement!!.deepCopy()
        return clonedXmlContext
    }

    /**
     * Adds observer to all children
     */
    fun addObserverToAllChildren(handler: (XmlElement) -> Unit)
    {
        observers.add(handler)
        if (rootXmlElement != null)
            rootXmlElement?.addObserverToAllChildren(handler)
    }

    override fun accept(visitor: IVisitor) {
        if(visitor.visit(this))
            if(rootXmlElement != null) rootXmlElement!!.accept(visitor)
        visitor.endVisit(this)
    }

    override fun toString(): String {
        return dump(4)
    }

    internal fun addXmlElementChild(xmlElement: XmlElement, elementChild: Any, elementChildName: String)
    {
        if (isBasicType(elementChild) || isEnum(elementChild)) {
            xmlElement.addChild(elementChildName, elementChild)
        }
        else if (isArray(elementChild) || isCollection(elementChild)) {
            val iterable: Iterable<Any> = if (isArray(elementChild)) {
                val elementArrayChild: Array<Any> = elementChild as Array<Any>
                elementArrayChild.asList()
            } else {
                elementChild as Iterable<Any>
            }
            val xmlElementChild = iterable.createXmlElement(this, elementChildName)
            xmlElement.addChild(xmlElementChild)
        }
        else if(isMap(elementChild))
        {
            val map: Map<Any, Any> = elementChild as Map<Any, Any>
            val xmlElementChild = map.createXmlElement(this, elementChildName)
            xmlElement.addChild(xmlElementChild)
        }
        else {
            val xmlElementChild = createXmlElementAnother(elementChild, elementChildName)
            xmlElement.addChild(xmlElementChild)
        }
    }

    private fun createXmlElement(element: Any, elementName: String): XmlElement
    {
        return if (isBasicType(element) || isEnum(element)) {
            XmlElement(elementName, element)
        } else if (isArray(element) || isCollection(element)) {
            val iterable: Iterable<Any> = if (isArray(element)) {
                val elementArrayChild: Array<Any> = element as Array<Any>
                elementArrayChild.asList()
            } else {
                element as Iterable<Any>
            }
            iterable.createXmlElement(this)
        } else if(isMap(element)) {
            val map: Map<Any, Any> = element as Map<Any, Any>
            map.createXmlElement(this)
        } else {
            createXmlElementAnother(element)
        }
    }

//region XML_ELEMENT_ANOTHER_CREATION
    private fun createXmlElementAnother(element: Any): XmlElement
    {
        val kClass: KClass<out Any> = element::class
        val xmlElement: XmlElement = createXmlElementAnotherAux(kClass, element)
        return addXmlElementAnotherChildren(kClass, element, xmlElement)
    }

    private fun createXmlElementAnother(element: Any, elementName: String): XmlElement
    {
        val kClass: KClass<out Any> = element::class
        val xmlElement: XmlElement = createXmlElementAnotherAux(kClass, element, elementName)
        return addXmlElementAnotherChildren(kClass, element, xmlElement)
    }

    private fun createXmlElementAnotherAux(kClass: KClass<out Any>, element: Any): XmlElement {
        val elementName: String = if (kClass.hasAnnotation<XmlElementName>()) {
            if (kClass.findAnnotation<XmlElementName>()?.name == null)
                throw InvalidXmlAnnotationException("XmlElementName", "Invalid name")

            kClass.findAnnotation<XmlElementName>()?.name!!
        } else {
            if (kClass.simpleName == null)
                throw InvalidXmlElementException("Class doesn't have a name")

            kClass.simpleName!!
        }

        return createXmlElementAnotherAux(kClass, element, elementName)
    }

    private fun createXmlElementAnotherAux(kClass: KClass<out Any>, element: Any, elementName: String): XmlElement {
        val memberProperties = kClass.declaredMemberProperties.filter {
            !it.hasAnnotation<XmlElementIgnore>()
        }

        val elementContent = memberProperties.filter {
            it.hasAnnotation<XmlElementContent>()
        }

        val xmlElement = if (elementContent.isNotEmpty()) {
            elementContent[0].isAccessible = true
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
            it.isAccessible = true
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

            val xmlElementAttribute = XmlElementAttribute(elementAttributeName, it.call(element)!! as String)
            xmlElement.addAttribute(xmlElementAttribute)
        }

        return xmlElement
    }

    private fun addXmlElementAnotherChildren(kClass: KClass<out Any>, element: Any, xmlElement: XmlElement) : XmlElement
    {
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
                xmlElement.addChild(elementChildName)
                return@forEach
            }

            addXmlElementChild(xmlElement, it.call(element)!!,elementChildName)
        }

        return xmlElement
    }

//endregion
}