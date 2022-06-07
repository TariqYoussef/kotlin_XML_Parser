package xmlparser.plugins

import xmlparser.core.XmlElement
import xmlparser.editor.ActionStack
import xmlparser.editor.action.AddChildAction
import xmlparser.editor.action.EditAttributeValueAction
import xmlparser.editor.view.AttributeView
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.component.IComponent
import xmlparser.editor.view.menuitem.IMenuItem
import java.awt.GridLayout
import javax.swing.*


class AddPointMenuItem : IMenuItem<ElementView>
{

    override fun accept(view: ElementView): Boolean = view.xmlElement.name != "Point"

    override fun menuItem(view: ElementView): JMenuItem {
        val jMenuItem = JMenuItem("Add Point")
        jMenuItem.addActionListener {
            val xField = JTextField(5)
            val yField = JTextField(5)

            val jPanel = JPanel()

            jPanel.add(JLabel("x:"))
            jPanel.add(xField)
            jPanel.add(Box.createHorizontalStrut(15))

            jPanel.add(JLabel("y:"))
            jPanel.add(yField)
            val result = JOptionPane.showConfirmDialog(
                null, jPanel,
                "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION
            )
            if (result == JOptionPane.OK_OPTION) {
                if(xField.text == "")
                {
                    JOptionPane.showMessageDialog(view, "Invalid x value")
                    return@addActionListener
                }
                if(yField.text == "")
                {
                    JOptionPane.showMessageDialog(view, "Invalid y value")
                    return@addActionListener
                }

                try
                {
                    Integer.parseInt(xField.text)
                }
                catch (exception: Exception)
                {
                    JOptionPane.showMessageDialog(view, "Invalid x value")
                    return@addActionListener
                }
                try
                {
                    Integer.parseInt(yField.text)
                }
                catch (exception: Exception)
                {
                    JOptionPane.showMessageDialog(view, "Invalid y value")
                    return@addActionListener
                }
                val xmlElement = XmlElement("Point")
                xmlElement.addChild("x", xField.text)
                xmlElement.addChild("y", yField.text)
                ActionStack.doAction(AddChildAction(view.xmlElement, xmlElement))
            }
        }
        return jMenuItem
    }

}

class BoolAttributeComponent: IComponent<AttributeView>
{
    private var selected = false
    override fun accept(view: AttributeView): Boolean = view.xmlAttribute.name == "bool"
    override fun component(view: AttributeView): JPanel {
        val panel = JPanel()
        panel.layout = GridLayout(0,2)
        panel.add(JLabel(view.xmlAttribute.name))
        val jCheckBox = JCheckBox()
        jCheckBox.isSelected = selected
        jCheckBox.addActionListener {
            val value = jCheckBox.isSelected.toString()
            selected = jCheckBox.isSelected
            ActionStack.doAction(
                EditAttributeValueAction(
                    view.xmlAttribute,
                    value
                )
            )
        }
        panel.add(jCheckBox)
        return panel
    }
}

