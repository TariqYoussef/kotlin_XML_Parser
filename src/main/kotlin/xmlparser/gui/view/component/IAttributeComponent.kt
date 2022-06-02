package xmlparser.gui.view.component

import xmlparser.gui.view.AttributeView
import javax.swing.JPanel

interface IAttributeComponent {
    /**
     * Condition to accept.
     */
    fun accept(attributeView: AttributeView): Boolean = true

    /**
     * Gets the component.
     */
    fun component(attributeView: AttributeView): JPanel
}