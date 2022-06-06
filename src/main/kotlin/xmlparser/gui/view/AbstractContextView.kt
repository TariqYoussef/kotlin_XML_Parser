package xmlparser.gui.view

import xmlparser.gui.view.menuitem.IMenuItem
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.SwingUtilities

abstract class AbstractContextView<T> : JPanel() {

    abstract val popupMenuName: String

    internal abstract fun createView()

    internal fun createPopupMenu(view: T,
                                 viewMenuItems: List<IMenuItem<T>>,
                                 viewPluginMenuItems: MutableList<IMenuItem<T>> )
    {
        val popupMenu = JPopupMenu(popupMenuName)

        fun populatePopupMenu(popupMenuActions: List<IMenuItem<T>>)
        {
            popupMenuActions.forEach {
                if(it.accept(view))
                {
                    popupMenu.add(it.menuItem(view))
                }
            }
        }

        populatePopupMenu(viewMenuItems)
        popupMenu.addSeparator()
        populatePopupMenu(viewPluginMenuItems)

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupMenu.show(this@AbstractContextView, e.x, e.y)
            }
        })
    }
}