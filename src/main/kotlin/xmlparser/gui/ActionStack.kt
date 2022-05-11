package xmlparser.gui

import java.util.*

object ActionStack {
    private val stack = Stack<IAction>()

    fun doAction(action: IAction)
    {
        action.execute()
        stack.push(action)
    }

    fun undoAction(): IAction
    {
        val action = stack.pop()
        action.undo()
        return action
    }

    fun removeAction(action: IAction)
    {
        stack.remove(action)
    }
}