package unit.xmlparser.core

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import xmlparser.core.XmlContext
import kotlin.test.assertEquals

internal class XmlContextTest {
    var xmlContext: XmlContext = XmlContext()

    private data class Entity(private val id: Int, val name: String)
    private data class Point(val x: Int, val y: Int)

    private enum class Direction {
        NORTH, SOUTH, WEST, EAST
    }

    @BeforeEach
    internal fun setUp() {
        xmlContext = XmlContext()
    }

    @Test
    internal fun iterableBasicTypeSerialization() {
        val list = listOf(1, 2, 3)
        xmlContext.addXmlElement(list)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><iterable><item>1</item><item>2</item><item>3</item></iterable>"
        assertEquals(expected, xmlContext.dump())
    }

    @Test
    internal fun iterableAnotherTypeSerialization() {
        val list = listOf(Entity(0, "0"), Entity(1, "1"), Entity(2, "2"))
        xmlContext.addXmlElement(list)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><iterable><item><id>0</id><name>0</name></item><item><id>1</id><name>1</name></item><item><id>2</id><name>2</name></item></iterable>"
        assertEquals(expected, xmlContext.dump())
    }

    @Test
    internal fun mapBasicTypeSerialization() {
        val map = mapOf(Pair(1, 2), Pair(3, 4))
        xmlContext.addXmlElement(map)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><map><item><key>1</key><value>2</value></item><item><key>3</key><value>4</value></item></map>"
        assertEquals(expected, xmlContext.dump())
    }

    @Test
    internal fun mapAnotherTypeSerialization() {
        val map = mapOf(Pair(Entity(0, "0"), listOf(Point(0,0), Point(1,1))),
                        Pair(Entity(1, "1"), listOf(Point(1,1), Point(2,2))),
                        Pair(Entity(2, "2"), listOf(Point(2,2), Point(3,3))))
        xmlContext.addXmlElement(map)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><map><item><key><id>0</id><name>0</name></key><value><item><x>0</x><y>0</y></item><item><x>1</x><y>1</y></item></value></item><item><key><id>1</id><name>1</name></key><value><item><x>1</x><y>1</y></item><item><x>2</x><y>2</y></item></value></item><item><key><id>2</id><name>2</name></key><value><item><x>2</x><y>2</y></item><item><x>3</x><y>3</y></item></value></item></map>"
        assertEquals(expected, xmlContext.dump())
    }

    @Test
    internal fun basicTypeSerialization() {
        val int = 1
        val float = 1.0
        val string = "string"
        val boolean = true
        xmlContext.addXmlElement(int)
        xmlContext.addXmlElement(float)
        xmlContext.addXmlElement(string)
        xmlContext.addXmlElement(boolean)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Int>1</Int><Double>1.0</Double><String>string</String><Boolean>true</Boolean>"
        assertEquals(expected, xmlContext.dump())
    }

    @Test
    internal fun enumSerialization() {
        xmlContext.addXmlElement(Direction.NORTH)
        xmlContext.addXmlElement(Direction.EAST)
        xmlContext.addXmlElement(Direction.SOUTH)
        xmlContext.addXmlElement(Direction.WEST)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Direction>NORTH</Direction><Direction>EAST</Direction><Direction>SOUTH</Direction><Direction>WEST</Direction>"
        assertEquals(expected, xmlContext.dump())
    }
}