package xmlparser.core.element

import xmlparser.core.*
import xmlparser.core.type.createXmlElement
import xmlparser.core.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.isAccessible

/**
 * Represents a xml context that can be assigned to a context or can be part of a context.
 */
class XmlElement(name: String, value: String = "") : IVisitable, IObservable<(XmlElement) -> Unit>
{
    var name: String = name
    set(name){
        field = name
        notifyObservers { it(this) }
    }
    var value: String = value
    set(value){
        field = value
        notifyObservers { it(this) }
    }

    val children: MutableList<XmlElement> = mutableListOf()
    val attributes: MutableList<XmlAttribute> = mutableListOf()
    var father: XmlElement? = null
    private set

    override val observers: MutableList<(XmlElement) -> Unit> = mutableListOf()

    init {
        require(name != ""){"Element name cannot be empty."}
    }

    constructor(element: Any) : this(element, getName(element))

    constructor(element: Any, name: String) : this(name, getValue(element))
    {
        if (isBasicType(element) || isEnum(element))
        {
            return
        }
        else if (isArray(element) || isCollection(element))
        {
            val iterable: Iterable<Any> = if (isArray(element)) {
                val elementArrayChild: Array<Any> = element as Array<Any>
                elementArrayChild.asList()
            } else {
                element as Iterable<Any>
            }
            iterable.createXmlElement(this)
            return
        }
        else if(isMap(element))
        {
            val map: Map<Any, Any> = element as Map<Any, Any>
            map.createXmlElement(this)
            return
        }
        else
        {
            assignElementAttributes(element,this)
            populateChildren(element,this)
        }
    }

    /**
     * Adds a child to the xml element.
     */
    fun addChild(name: String, value: String = "") = addChild(XmlElement(name, value))
    /**
     * Adds a child to the xml element.
     */
    fun addChild(xmlElement: XmlElement)
    {
        xmlElement.father = this
        children.add(xmlElement)
        notifyObservers { it(this) }
    }

    /**
     * Removes child of the xml element.
     */
    fun removeChild(xmlElement: XmlElement)
    {
        children.remove(xmlElement)
        xmlElement.father = null
        notifyObservers { it(this) }
    }

    /**
     * Adds an attribute to the xml element.
     */
    fun addAttribute(xmlAttribute: XmlAttribute)
    {
        attributes.add(xmlAttribute)
        notifyObservers { it(this) }
    }
    /**
     * Removes an attribute to the xml element.
     */
    fun removeAttribute(xmlAttribute: XmlAttribute)
    {
        attributes.remove(xmlAttribute)
        notifyObservers { it(this) }
    }

    /**
     * Tells if the xml element has children.
     */
    fun hasChildren() = children.isNotEmpty()

    /**
     * Tells the number of children of the xml element.
     */
    fun childrenCount() = children.count()

    /**
     * Tells if xml element has a father.
     */
    fun hasFather() = father != null

    /**
     * Clones without children.
     */
    fun cloneWithoutChildren(): XmlElement
    {
        val clonedXmlElement = XmlElement(name, value)
        clonedXmlElement.attributes.addAll(attributes)
        return clonedXmlElement
    }

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
    /**
     * Deep copies XmlEntity.
     */
    fun deepCopy(): XmlElement
    {
        val clonedXmlElement = XmlElement(name, value)
        clonedXmlElement.attributes.addAll(attributes)
        children.forEach{
            clonedXmlElement.addChild(it.deepCopy())
        }
        return clonedXmlElement
    }

    /**
     * Adds observer to all children
     */
    fun addObserverToAllChildren(handler: (XmlElement) -> Unit)
    {
        this.addObserver(handler)
        children.forEach{
            it.addObserverToAllChildren(handler)
        }
    }

    override fun accept(visitor: IVisitor) {
        if(visitor.visit(this))
            children.forEach {
                it.accept(visitor)
            }
        visitor.endVisit(this)
    }

    override fun toString(): String {
        return dump(4)
    }

    private companion object
    {
        private fun getName(element: Any): String
        {
            val kClass: KClass<out Any> = element::class
            return if (kClass.hasAnnotation<XmlElementName>()) {
                if (kClass.findAnnotation<XmlElementName>()?.name == null)
                    throw InvalidXmlAnnotationException("XmlElementName", "Invalid name")

                kClass.findAnnotation<XmlElementName>()?.name!!
            } else {
                if (kClass.simpleName == null)
                    throw InvalidXmlElementException("Class doesn't have a name")

                kClass.simpleName!!
            }
        }

        private fun getValue(element: Any): String
        {
            if (isBasicType(element) || isEnum(element)) {
                return element.toString()
            }
            val kClass: KClass<out Any> = element::class
            val elementContent = kClass.declaredMemberProperties.filter {
                !it.hasAnnotation<XmlElementIgnore>() && it.hasAnnotation<XmlElementContent>()
            }
            if (elementContent.isNotEmpty()) {
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

                return elementContent[0].call(element)!!.toString()
            }
            return ""
        }

        private fun assignElementAttributes(element: Any, xmlElement: XmlElement)
        {
            val kClass: KClass<out Any> = element::class
            val elementAttributes = kClass.declaredMemberProperties.filter {
                !it.hasAnnotation<XmlElementIgnore>() && it.hasAnnotation<XmlElementAttributeAnnotation>()
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

                val xmlAttribute = XmlAttribute(elementAttributeName, it.call(element)!! as String)
                xmlElement.addAttribute(xmlAttribute)
            }
        }

        private fun populateChildren(element: Any, xmlElement: XmlElement)
        {
            val kClass: KClass<out Any> = element::class
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
                val child = XmlElement(it.call(element)!!, elementChildName)
                xmlElement.addChild(child)
            }
        }
    }
}

//TODO find out which members of the class should be public to be filtered by the visitor accept function
