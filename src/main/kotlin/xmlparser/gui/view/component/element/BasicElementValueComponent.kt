package xmlparser.gui.view.component.element

import xmlparser.gui.ActionStack
import xmlparser.gui.action.EditElementValueAction
import xmlparser.gui.view.ElementView
import xmlparser.gui.view.component.IComponent
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.JTextField

class BasicElementValueComponent : IComponent<ElementView> {

    override fun component(view: ElementView): JPanel {
        val panel = JPanel()
        panel.layout = GridLayout(0,1)
        val textField = JTextField(view.xmlElement.value.toString())
        textField.addActionListener{
            ActionStack.doAction(EditElementValueAction(view.xmlElement, textField.text))
        }
        panel.add(textField)
        return panel
    }

}