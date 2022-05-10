package xmlparser.core

interface IVisitable {
    fun accept(visitor: IVisitor)
}