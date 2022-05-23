package xmlparser.gui.views

import javafx.scene.control.ListView
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.*
import xmlparser.gui.ActionStack
import xmlparser.gui.IAction

class LocalHistoryView : View() {

    private var historyList: ListView<IAction> by singleAssign()

    override val root = vbox {
        label{
            text = "Actions History"
            style{
                padding = box(10.px)
                fontSize = 14.px
                fontWeight = FontWeight.BOLD
            }
        }
        historyList = listview{
            items = ActionStack.getUndoStack().asObservable()
            cellFormat {
                text = it.name
            }
        }
    }

    fun updateHistoryList()
    {
        historyList.items = ActionStack.getUndoStack().asObservable()
    }
}