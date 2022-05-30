package xmlparser.gui.view

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.SwingUtilities

abstract class IContextView : JPanel() {

    abstract val popupMenuName: String

    internal abstract fun createView()

    internal abstract fun populatePopupMenu(popupMenu: JPopupMenu)

    internal fun createPopupMenu()
    {
        val popupMenu = JPopupMenu(popupMenuName)

        populatePopupMenu(popupMenu)

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupMenu.show(this@IContextView, e.x, e.y)
            }
        })
    }
}