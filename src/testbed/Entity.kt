package testbed

import xml.XmlElementName

@XmlElementName("Entity")
data class Entity(val id: Int, val name: String)