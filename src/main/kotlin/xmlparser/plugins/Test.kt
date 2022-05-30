package xmlparser.plugins

import xmlparser.gui.IAction
import xmlparser.gui.IPluginActionPopupMenu
import xmlparser.gui.view.ElementView

class Test {
    val name = "ce"
}

class PluginActionPopupMenu1 : IPluginActionPopupMenu
{
    override val displayName: String = "PluginActionTest1"
    override fun getAction(elementView: ElementView): IAction {
        return ActionTest1()
    }
}

class PluginActionPopupMenu2 : IPluginActionPopupMenu
{
    override val displayName: String = "PluginActionTest2"
    override fun getAction(elementView: ElementView): IAction {
        return ActionTest2()
    }
}

class ActionTest1() : IAction
{
    override val name: String = "ActionTest1"

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
    override fun execute() {
        TODO("Not yet implemented")
    }

    override fun undo() {
        TODO("Not yet implemented")
    }
}