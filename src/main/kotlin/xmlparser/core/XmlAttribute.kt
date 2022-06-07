package xmlparser.core

/**
 * Represents an attribute that can be assigned to a xml element.
 */
class XmlAttribute(name: String, value: String = ""): IObservable<(XmlAttribute) -> Unit>
{
    override val observers: MutableList<(XmlAttribute) -> Unit> = mutableListOf()
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

