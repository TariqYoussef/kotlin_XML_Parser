package xmlparser.plugins.calendar

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement

class BaseContext : XmlContext() {
    init {
        this.rootXmlElement = XmlElement("Calendar")
    }
}