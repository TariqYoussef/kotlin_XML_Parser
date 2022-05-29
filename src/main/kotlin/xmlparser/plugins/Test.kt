package xmlparser.plugins

import xmlparser.gui.Application
import xmlparser.gui.IAction

class Test {
    val name = "ce"
}

class ActionTest1() : IAction
{
    override val name: String = "ActionTest1"
    override val displayName: String = ""
    override fun execute() {
        TODO("Not yet implemented")
    }

    override fun undo() {
        TODO("Not yet implemented")
    }
}

class ActionTest2() : IAction
{
    override val name: String = "ActionTest2"
    override val displayName: String = ""
    override fun execute() {
        TODO("Not yet implemented")
    }

    override fun undo() {
        TODO("Not yet implemented")
    }
}