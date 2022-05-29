package xmlparser.plugins

import xmlparser.gui.Application
import xmlparser.gui.plugin.IPluginAction

class Test {
    val name = "ce"
}

class ActionTest1() : IPluginAction
{
    override val name: String = "ActionTest1"
    override val displayName: String = ""
    override fun execute(application: Application) {
        TODO("Not yet implemented")
    }

    override fun undo() {
        TODO("Not yet implemented")
    }
}

class ActionTest2() : IPluginAction
{
    override val name: String = "ActionTest2"
    override val displayName: String = ""
    override fun execute(application: Application) {
        TODO("Not yet implemented")
    }

    override fun undo() {
        TODO("Not yet implemented")
    }
}