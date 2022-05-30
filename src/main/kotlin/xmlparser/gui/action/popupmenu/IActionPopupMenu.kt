package xmlparser.gui.action.popupmenu

import xmlparser.gui.action.IAction

interface IActionPopupMenu<T> {
    /**
     * Action display name.
     */
    val displayName: String
    /**
     * Condition to accept.
     */
    fun accept(view: T): Boolean = true
    /**
     * Gets the action.
     */
    fun action(view: T): IAction?
}