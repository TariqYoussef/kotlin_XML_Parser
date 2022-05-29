package xmlparser.gui.plugin

import xmlparser.gui.Application

interface IPluginAction {
    val name: String
    val displayName: String
    /**
     * Executes action.
     */
    fun execute(application: Application)

    /**
     * Undo the action
     */
    fun undo()
}