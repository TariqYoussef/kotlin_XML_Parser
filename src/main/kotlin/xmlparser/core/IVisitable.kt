package xmlparser.core

interface IVisitable {
    /**
     * Accepts visitor.
     */
    fun accept(visitor: IVisitor)
}