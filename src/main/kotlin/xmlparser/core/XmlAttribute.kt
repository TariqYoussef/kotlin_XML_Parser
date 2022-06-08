package xmlparser.core

/**
 * Represents an attribute that can be assigned to a xml element.
 */
class XmlAttribute(name: String, value: String = ""): IObservable<(XmlAttribute, NotificationTypeAttribute) -> Unit>
{
    override val observers: MutableList<(XmlAttribute, NotificationTypeAttribute) -> Unit> = mutableListOf()
    var name: String = name
    set(name)
    {
        field = name
        notifyObservers { it(this, NotificationTypeAttribute.CHANGE_NAME) }
    }
    var value: String = value
    set(value){
        field = value
        notifyObservers { it(this, NotificationTypeAttribute.CHANGE_VALUE) }
    }

    init{
        require(name != ""){"Attribute name cannot be empty."}
    }
}

