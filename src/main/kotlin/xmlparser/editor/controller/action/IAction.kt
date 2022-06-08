package xmlparser.editor.controller.action

interface IAction {
    /**
     * Action name.
     */
    val name: String

    /**
     * Executes action.
     */
    fun execute()

    /**
     * Undo the action
     */
    fun undo()
}