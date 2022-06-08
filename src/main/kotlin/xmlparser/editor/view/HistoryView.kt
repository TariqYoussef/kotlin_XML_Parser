package xmlparser.editor.view

import xmlparser.editor.controller.ActionStack
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JList

class HistoryView(owner: JFrame, title: String, modal: Boolean = true) : JDialog(owner, title, modal) {

    init {
        size = Dimension(300, 300)
        layout = FlowLayout()
        if(ActionStack.isUndoStackEmpty())
        {
            add(JLabel("No Actions to show."))
        }
        else
        {
            val list = JList(ActionStack.undoStack)
            list.setCellRenderer { _, value, _, _, _ -> JLabel(value.name)  }
            add(list)
        }
    }

    fun open() {
        isVisible = true
    }
}