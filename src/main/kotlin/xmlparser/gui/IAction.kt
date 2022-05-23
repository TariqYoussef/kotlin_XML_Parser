package xmlparser.gui

interface IAction {
    val name: String
    fun execute()
    fun undo()
}