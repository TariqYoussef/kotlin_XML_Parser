package xmlparser.core.element

import xmlparser.core.IObservable
import xmlparser.core.IVisitable
import xmlparser.core.IVisitor
import xmlparser.core.utils.createFilledString

/**
 * Represents a xml context that can be assigned to a context or can be part of a context.
 */
class XmlElement(name: String, value: Any = "") : IVisitable, IObservable<(XmlElement) -> Unit>
{
    var name: String = name
    set(name){
        field = name
        notifyObservers { it(this) }
    }
    var value: Any = value
    set(value){
        field = value
        notifyObservers { it(this) }
    }
    init {
        require(name != ""){"Element name cannot be empty."}
    }

    val children: MutableList<XmlElement> = mutableListOf()
    val attributes: MutableList<XmlElementAttribute> = mutableListOf()
    var father: XmlElement? = null
    private set

    override val observers: MutableList<(XmlElement) -> Unit> = mutableListOf()

    /**
     * Adds a child to the xml element.
     */
    fun addChild(name: String, value: Any = "") = addChild(XmlElement(name, value))
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
    fun addAttribute(xmlElementAttribute: XmlElementAttribute)
    {
        attributes.add(xmlElementAttribute)
        notifyObservers { it(this) }
    }
    /**
     * Removes an attribute to the xml element.
     */
    fun removeAttribute(xmlElementAttribute: XmlElementAttribute)
    {
        attributes.remove(xmlElementAttribute)
        notifyObservers { it(this) }
    }

    /**
     * Updates an attribute of the xml element.
     */
    fun updateAttribute(xmlElementAttribute: XmlElementAttribute, newName: String, newValue: String)
    {
        xmlElementAttribute.name = newName
        xmlElementAttribute.value = newValue
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
     * Updates name and value.
     */
    fun updateEntity(newName: String, newValue: String)
    {
        name = newName
        value = newValue
        notifyObservers { it(this) }
    }

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
    internal fun addObserverToAllChildren(handler: (XmlElement) -> Unit)
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
}

//TODO find out which members of the class should be public to be filtered by the visitor accept function
