package testbed

import xml.*

@XmlElementName("Entity")
data class Entity(val id: Int, val name: String)
{
    @XmlElementContent
    val data: String = "Data Example"
    @XmlElementIgnore
    val ignore: String = "Element to ignore"
    @XmlElementAttribute
    val attribute1: String = "Attribute content"
    @XmlElementAttribute
    @XmlElementName("SpecialAttribute")
    val attribute2: String = "Attribute content"

    val position: Point = Point(5, 7)

    val long: Long = 45

    val direction: Direction = Direction.NORTH

    @XmlElementName("itinerary")
    val point2: Point2 = Point2(Point(0,0), Point(5,5))

    val array: Array<Int> = arrayOf(1,2,3)
    val set: Set<Int> = setOf(1,2,3)
    val listPoints: List<Point> = listOf(Point(0,0), Point(1,2), Point(2,3))
    val listLists: List<Array<Point>> = listOf(arrayOf(Point(0,0), Point(1,2), Point(2,3)))
    val map: Map<Int, Point> = mapOf(Pair(0, Point(0,0)), Pair(1, Point(1,1)))
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

