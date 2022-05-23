package xmlparser.gui

import java.util.*

object ActionStack {
    private val undoStack = Stack<IAction>()
    private val redoStack = Stack<IAction>()

    fun doAction(action: IAction)
    {
        action.execute()
        undoStack.push(action)
    }

    fun undoAction()
    {
        val action = undoStack.pop()
        action.undo()
        redoStack.push(action)
    }

    fun redoAction()
    {
        val action = redoStack.pop()
        doAction(action)
    }

    fun removeAction(action: IAction)
    {
        undoStack.remove(action)
        redoStack.remove(action)
    }

    fun getPeekActionUndo(): IAction
    {
        return undoStack.peek()
    }

    fun getPeekActionRedo(): IAction
    {
        return redoStack.peek()
    }
}