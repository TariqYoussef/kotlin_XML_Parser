package xmlparser.gui.views

import javafx.scene.control.ListView
import tornadofx.*
import xmlparser.gui.ActionStack
import xmlparser.gui.IAction

class LocalHistoryView : View() {

    private var historyList: ListView<IAction> by singleAssign()

    override val root = vbox {
        label{
            text = "Actions History"

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