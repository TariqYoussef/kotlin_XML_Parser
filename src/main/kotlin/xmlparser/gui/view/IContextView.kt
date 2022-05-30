package xmlparser.gui.view

import xmlparser.gui.ActionStack
import xmlparser.gui.action.popupmenu.IActionPopupMenu
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.SwingUtilities

abstract class IContextView<T> : JPanel() {

    abstract val popupMenuName: String

    internal abstract fun createView()

    internal fun createPopupMenu(view: T,
                                 viewPopupMenuActions: List<IActionPopupMenu<T>>,
                                 viewPluginPopupMenuActions: MutableList<IActionPopupMenu<T>> )
    {
        val popupMenu = JPopupMenu(popupMenuName)

        viewPopupMenuActions.forEach {
            if(it.accept(view))
            {
                val jMenuItem = JMenuItem(it.displayName)
                jMenuItem.addActionListener {_ ->
                    val action = it.action(view)
                    if(action != null)
                        ActionStack.doAction(action)
                }
                popupMenu.add(jMenuItem)
            }
        }
        popupMenu.addSeparator()
        viewPluginPopupMenuActions.forEach {
            if(it.accept(view))
            {
                val jMenuItem = JMenuItem(it.displayName)
                jMenuItem.addActionListener {_ ->
                    val action = it.action(view)
                    if(action != null)
                        ActionStack.doAction(action)
                }
                popupMenu.add(jMenuItem)
            }
        }

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupMenu.show(this@IContextView, e.x, e.y)
            }
        })
    }
}