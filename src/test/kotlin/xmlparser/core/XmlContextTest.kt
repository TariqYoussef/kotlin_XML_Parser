package xmlparser.core

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import xmlparser.core.element.XmlElement
import xmlparser.core.visitors.FilterVisitor
import xmlparser.core.visitors.FindVisitor
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
        xmlContext.setPrincipalXmlElement(list)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><iterable><item>1</item><item>2</item><item>3</item></iterable>"
        assertEquals(expected, xmlContext.dump())
    }

    @Test
    internal fun iterableAnotherTypeSerialization() {
        val list = listOf(Entity(0, "0"), Entity(1, "1"), Entity(2, "2"))
        xmlContext.setPrincipalXmlElement(list)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><iterable><item><id>0</id><name>0</name></item><item><id>1</id><name>1</name></item><item><id>2</id><name>2</name></item></iterable>"
        assertEquals(expected, xmlContext.dump())
    }

    @Test
    internal fun mapBasicTypeSerialization() {
        val map = mapOf(Pair(1, 2), Pair(3, 4))
        xmlContext.setPrincipalXmlElement(map)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><map><item><key>1</key><value>2</value></item><item><key>3</key><value>4</value></item></map>"
        assertEquals(expected, xmlContext.dump())
    }

    @Test
    internal fun mapAnotherTypeSerialization() {
        val map = mapOf(Pair(Entity(0, "0"), listOf(Point(0,0), Point(1,1))),
                        Pair(Entity(1, "1"), listOf(Point(1,1), Point(2,2))),
                        Pair(Entity(2, "2"), listOf(Point(2,2), Point(3,3))))
        xmlContext.setPrincipalXmlElement(map)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><map><item><key><id>0</id><name>0</name></key><value><item><x>0</x><y>0</y></item><item><x>1</x><y>1</y></item></value></item><item><key><id>1</id><name>1</name></key><value><item><x>1</x><y>1</y></item><item><x>2</x><y>2</y></item></value></item><item><key><id>2</id><name>2</name></key><value><item><x>2</x><y>2</y></item><item><x>3</x><y>3</y></item></value></item></map>"
        assertEquals(expected, xmlContext.dump())
    }

    @Test
    internal fun basicTypeSerialization() {
        xmlContext.setPrincipalXmlElement(XmlElement("container"))
        val int = 1
        val double = 1.0
        val string = "string"
        val boolean = true
        xmlContext.principalXmlElement()!!.addChild("Int", int)
        xmlContext.principalXmlElement()!!.addChild("Double", double)
        xmlContext.principalXmlElement()!!.addChild("String", string)
        xmlContext.principalXmlElement()!!.addChild("Boolean", boolean)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><container><Int>1</Int><Double>1.0</Double><String>string</String><Boolean>true</Boolean></container>"
        assertEquals(expected, xmlContext.dump())
    }

    @Test
    internal fun enumSerialization() {
        xmlContext.setPrincipalXmlElement(Direction.NORTH)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Direction>NORTH</Direction>"
        assertEquals(expected, xmlContext.dump())
    }

    @XmlElementName("ComplexEntity")
    private class Complex()
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

        private val entity: Entity = Entity(1, "1")
        private val point: Point = Point(1,1)
        private val id: Int = 1
        @XmlElementName("maps")
        val map: Map<Int, Point> = mapOf(Pair(0, Point(0, 0)), Pair(1, Point(1, 1)))
    }

    @Test
    internal fun anotherTypeSerialization()
    {
        val complex = Complex()
        xmlContext.setPrincipalXmlElement(complex)
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><ComplexEntity attribute1=\"Attribute content\" SpecialAttribute=\"Attribute content\">Data Example<entity><id>1</id><name>1</name></entity><id>1</id><maps><item><key>0</key><value><x>0</x><y>0</y></value></item><item><key>1</key><value><x>1</x><y>1</y></value></item></maps><point><x>1</x><y>1</y></point></ComplexEntity>"
        assertEquals(expected, xmlContext.dump())
    }

    @Test
    internal fun filterVisitor() {
        val complex = Complex()
        xmlContext.setPrincipalXmlElement(complex)

        val filterEntity: (xmlElement: XmlElement) -> Boolean = {
            it.name == "ComplexEntity" || it.name == "entity" ||
                    (it.father()!!.name == "entity" && (it.name == "id" || it.name == "name"))
        }
        val filterVisitorEntity = FilterVisitor(filterEntity)
        xmlContext.accept(filterVisitorEntity)

        val filterMap: (xmlElement: XmlElement) -> Boolean = {
            it.name == "ComplexEntity" || it.name == "maps" ||
                    it.father()!!.name == "maps" ||
                    it.father()!!.name == "item"
        }
        val filterVisitorMap = FilterVisitor(filterMap)
        xmlContext.accept(filterVisitorMap)

        val expectedOriginal = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><ComplexEntity attribute1=\"Attribute content\" SpecialAttribute=\"Attribute content\">Data Example<entity><id>1</id><name>1</name></entity><id>1</id><maps><item><key>0</key><value><x>0</x><y>0</y></value></item><item><key>1</key><value><x>1</x><y>1</y></value></item></maps><point><x>1</x><y>1</y></point></ComplexEntity>"
        assertEquals(expectedOriginal, xmlContext.dump())

        val expectedFilteredEntity = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><ComplexEntity attribute1=\"Attribute content\" SpecialAttribute=\"Attribute content\">Data Example<entity><id>1</id><name>1</name></entity></ComplexEntity>"
        assertEquals(expectedFilteredEntity, filterVisitorEntity.xmlContext()!!.dump())

        val expectedFilteredMap = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><ComplexEntity attribute1=\"Attribute content\" SpecialAttribute=\"Attribute content\">Data Example<maps><item><key>0</key><value></value></item><item><key>1</key><value></value></item></maps></ComplexEntity>"
        assertEquals(expectedFilteredMap, filterVisitorMap.xmlContext()!!.dump())
    }

    @Test
    internal fun findVisitor() {
        val complex = Complex()
        xmlContext.setPrincipalXmlElement(complex)

        val filterPoint: (xmlElement: XmlElement) -> Boolean = {
            it.name == "ComplexEntity" || it.name == "entity" ||
                    (it.father()!!.name == "point" && (it.name == "x" || it.name == "y"))
        }
        val filterFindPoint = FindVisitor(filterPoint)
        xmlContext.accept(filterFindPoint)

        val expectedOriginal = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><ComplexEntity attribute1=\"Attribute content\" SpecialAttribute=\"Attribute content\">Data Example<entity><id>1</id><name>1</name></entity><id>1</id><maps><item><key>0</key><value><x>0</x><y>0</y></value></item><item><key>1</key><value><x>1</x><y>1</y></value></item></maps><point><x>1</x><y>1</y></point></ComplexEntity>"
        assertEquals(expectedOriginal, xmlContext.dump())

        val expectedFindPoint = "[<ComplexEntity attribute1=\"Attribute content\" SpecialAttribute=\"Attribute content\">Data Example</ComplexEntity>\n" +
                ", <entity></entity>\n" +
                ", <x>1</x>\n" +
                ", <y>1</y>\n" +
                "]"
        assertEquals(expectedFindPoint,filterFindPoint.filteredXmlElements().toString())
    }
}