package xmlparser.guilegacy.views

import javafx.scene.control.ListView
import javafx.scene.text.FontWeight
import tornadofx.*
import xmlparser.guilegacy.ActionStack
import xmlparser.guilegacy.IAction

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
            items = ActionStack.undoStack.asObservable()
            cellFormat {
                text = it.name
            }
        }
    }

    fun updateHistoryList()
    {
        historyList.items = ActionStack.undoStack.asObservable()
    }
}