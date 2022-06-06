package xmlparser.editor

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement

class DefaultContext : XmlContext() {
    init {
        this.rootXmlElement = XmlElement("Root")
    }
}