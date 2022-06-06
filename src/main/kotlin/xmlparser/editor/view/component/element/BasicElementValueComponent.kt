package xmlparser.editor.view.component.element

import xmlparser.editor.ActionStack
import xmlparser.editor.action.EditElementValueAction
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.component.IComponent
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