package xmlparser.gui

interface IAction {
    val name: String
    val displayName: String

    /**
     * Executes action.
     */
    fun execute()

    /**
     * Undo the action
     */
    fun undo()
}