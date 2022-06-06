package xmlparser.plugins.musiclibrary

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement

class BaseContext : XmlContext() {
    init {
        this.rootXmlElement = XmlElement("Albums")
    }
}