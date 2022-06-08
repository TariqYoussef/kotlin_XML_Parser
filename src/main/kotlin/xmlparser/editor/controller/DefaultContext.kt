package xmlparser.editor.controller

import xmlparser.core.XmlContext
import xmlparser.core.XmlElement

class DefaultContext : XmlContext() {
    init {
        this.rootXmlElement = XmlElement("Root")
    }
}