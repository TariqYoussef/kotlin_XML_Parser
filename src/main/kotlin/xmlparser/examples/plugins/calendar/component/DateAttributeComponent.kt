package xmlparser.examples.plugins.calendar.component

import xmlparser.editor.ActionStack
import xmlparser.editor.action.EditAttributeValueAction
import xmlparser.editor.view.AttributeView
import xmlparser.editor.view.component.IComponent
import java.awt.GridLayout
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.*

class DateAttributeComponent: IComponent<AttributeView, JPanel>
{

    override fun accept(view: AttributeView): Boolean {
        return view.xmlAttribute.name == "Date" &&
                view.xmlElement.name == "Event"
    }

    override fun component(view: AttributeView): JPanel {
        val panel = JPanel()
        panel.layout = GridLayout(0,2)
        val label = JLabel(view.xmlAttribute.name)
        label.horizontalAlignment = SwingConstants.RIGHT
        panel.add(label)

        val date = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(view.xmlAttribute.value)
        val jSpinner = JSpinner(SpinnerDateModel(date,
            null, null, Calendar.MINUTE))
        jSpinner.addChangeListener {
            val value = jSpinner.value.toString()
            ActionStack.doAction(
                EditAttributeValueAction(
                    view.xmlAttribute,
                    value
                )
            )
        }
        panel.add(jSpinner)
        return panel
    }

}