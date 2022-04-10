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

    val point2: Point2 = Point2(Point(0,0), Point(5,5))
}

data class Point(val x: Int, val y: Int)

@XmlElementName("Beg_End")
data class Point2(val point1: Point, val point2: Point)
{
    val content: String = "content"
}

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

