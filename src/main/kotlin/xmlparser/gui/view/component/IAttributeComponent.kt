package xmlparser.gui.view.component

import xmlparser.gui.view.AttributeView

interface IAttributeComponent {
    /**
     * Condition to accept.
     */
    fun accept(attributeView: AttributeView): Boolean = true

    /**
     * Draws UI.
     */
    fun draw(attributeView: AttributeView)
}