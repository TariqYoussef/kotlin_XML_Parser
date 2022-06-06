package xmlparser.gui.view.menuitem

import javax.swing.JMenuItem


interface IMenuItem<T> {
    /**
     * Condition to accept.
     */
    fun accept(view: T): Boolean = true
    /**
     * Gets the menu item.
     */
    fun menuItem(view: T): JMenuItem?
}