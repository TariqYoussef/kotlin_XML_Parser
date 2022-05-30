package xmlparser.gui.action.popupmenu

import xmlparser.gui.action.IAction
import xmlparser.gui.view.ElementView

interface IActionPopupMenu {
    /**
     * Action display name.
     */
    val displayName: String
    /**
     * Condition to accept.
     */
    fun accept(elementView: ElementView): Boolean = true
    /**
     * Gets the action.
     */
    fun getAction(elementView: ElementView): IAction?
}