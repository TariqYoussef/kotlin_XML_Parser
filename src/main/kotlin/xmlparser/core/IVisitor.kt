package xmlparser.core

interface IVisitor {
    /**
     * Visit.
     */
    fun visit(xmlContext: XmlContext): Boolean = true
    /**
     * Visit.
     */
    fun visit(xmlElement: XmlElement): Boolean = true
    /**
     * End Visit.
     */
    fun endVisit(xmlContext: XmlContext){}
    /**
     * End Visit.
     */
    fun endVisit(xmlElement: XmlElement){}
}