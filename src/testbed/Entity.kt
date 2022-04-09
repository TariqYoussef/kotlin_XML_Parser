package testbed

import xml.XmlElementContent
import xml.XmlElementName
import xml.XmlIgnore

@XmlElementName("Entity")
data class Entity(val id: Int, val name: String)
{
    @XmlElementContent
    val data: String = "Data Example"
    @XmlIgnore
    val ignore: String = "Element to ignore"

    val position: Point = Point(5, 7)
}

data class Point(val x: Int, val y: Int)