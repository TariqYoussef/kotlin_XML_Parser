package xmlparser.gui.view.component

import xmlparser.gui.view.ElementView

interface IAttributeComponent {
    /**
     * Condition to accept.
     */
    fun accept(elementView: ElementView): Boolean = true

    /**
     * Draws UI.
     */
    fun draw(elementView: ElementView)
}