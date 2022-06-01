package xmlparser.plugins.calendar

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement

class BaseContext : XmlContext(version = "10.0") {
    init {
        this.setRootXmlElement(XmlElement("Calendar"))
    }
}