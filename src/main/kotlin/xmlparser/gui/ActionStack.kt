package xmlparser.gui

import java.util.*

object ActionStack {
    private val undoStack = Stack<IAction>()

    fun doAction(action: IAction)
    {
        action.execute()
        undoStack.push(action)
    }

    fun undoAction()
    {
        val action = undoStack.pop()
        action.undo()
    }

    fun removeAction(action: IAction)
    {
        undoStack.remove(action)
    }
}