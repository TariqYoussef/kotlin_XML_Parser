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

    val long: Long = 45

    val direction: Direction = Direction.NORTH
}

data class Point(val x: Int, val y: Int)

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}