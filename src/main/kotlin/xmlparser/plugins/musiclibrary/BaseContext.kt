package xmlparser.plugins.musiclibrary

import xmlparser.core.XmlContext
import xmlparser.core.XmlElement

class BaseContext : XmlContext() {
    init {
        this.rootXmlElement = XmlElement("MusicLibrary")
    }
}