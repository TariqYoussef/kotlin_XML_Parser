package xmlparser.gui.view.component

import javax.swing.JPanel

interface IComponent<T> {
    /**
     * Condition to accept.
     */
    fun accept(view: T): Boolean = true

    /**
     * Gets the component.
     */
    fun component(view: T): JPanel?
}