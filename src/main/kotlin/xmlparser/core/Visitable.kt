package xmlparser.core

interface Visitable {
    fun accept(visitor: Visitor)
}