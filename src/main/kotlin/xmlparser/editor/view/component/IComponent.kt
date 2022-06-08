package xmlparser.editor.view.component

import xmlparser.editor.view.AbstractContextView
import java.awt.Component

interface IComponent<T1 : AbstractContextView<T1>, T2 : Component> {
    /**
     * Condition to accept.
     */
    fun accept(view: T1): Boolean = true

    /**
     * Gets the component.
     */
    fun component(view: T1): T2?
}