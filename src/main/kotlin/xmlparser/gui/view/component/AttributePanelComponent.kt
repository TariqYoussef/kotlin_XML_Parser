package xmlparser.gui.view.component

import xmlparser.gui.view.AttributeView
import javax.swing.JPanel

abstract class AttributePanelComponent : JPanel() {
    /**
     * Condition to accept.
     */
    open fun accept(attributeView: AttributeView): Boolean = true

    /**
     * Draws Component.
     */
    abstract fun draw(attributeView: AttributeView)
}