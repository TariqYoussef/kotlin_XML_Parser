package xmlparser.plugins

import xmlparser.gui.IAction
import xmlparser.gui.IActionPopupMenu
import xmlparser.gui.view.ElementView
import javax.swing.*


class Test {
    val name = "ce"
}

class AddPointPopupMenuAction : IActionPopupMenu
{
    override val displayName: String = "Add Point"
    override fun getAction(elementView: ElementView): IAction {
        val xField = JTextField(5)
        val yField = JTextField(5)

        val myPanel = JPanel()
        myPanel.add(JLabel("x:"))
        myPanel.add(xField)
        myPanel.add(Box.createHorizontalStrut(15)) // a spacer

        myPanel.add(JLabel("y:"))
        myPanel.add(yField)

        JOptionPane.showConfirmDialog(
            null, myPanel,
            "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION
        )
        return AddPointAction()
    }

    class AddPointAction() : IAction
    {
        override val name: String = "ActionTest1"

        override fun execute() {
            TODO("Not yet implemented")
        }

        override fun undo() {
            TODO("Not yet implemented")
        }
    }
}

class ActionPopupMenu2 : IActionPopupMenu
{
    override val displayName: String = "PluginActionTest2"
    override fun getAction(elementView: ElementView): IAction {
        return ActionTest2()
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
}

