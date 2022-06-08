package xmlparser.editor.view.menuitem

import xmlparser.editor.view.AbstractContextView
import javax.swing.JMenuItem


interface IMenuItem<T1 : AbstractContextView<T1>> {
    /**
     * Condition to accept.
     */
    fun accept(view: T1): Boolean = true
    /**
     * Gets the menu item.
     */
    fun menuItem(view: T1): JMenuItem
}