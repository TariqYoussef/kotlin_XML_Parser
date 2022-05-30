package xmlparser.core.element

import xmlparser.core.IObservable

/**
 * Represents an attribute that can be assigned to a xml element.
 */
class XmlElementAttribute(name: String, value: String = ""): IObservable<(XmlElementAttribute) -> Unit>
{
    override val observers: MutableList<(XmlElementAttribute) -> Unit> = mutableListOf()
    var name: String = name
    set(name)
    {
        field = name
        notifyObservers { it(this) }
    }
    var value: String = value
    set(value){
        field = value
        notifyObservers { it(this) }
    }

    init{
        require(name != ""){"Attribute name cannot be empty."}
    }
}

