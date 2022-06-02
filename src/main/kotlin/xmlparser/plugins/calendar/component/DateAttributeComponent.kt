package xmlparser.plugins.calendar.component

import xmlparser.gui.ActionStack
import xmlparser.gui.action.EditAttributeValueAction
import xmlparser.gui.view.AttributeView
import xmlparser.gui.view.component.IComponent
import java.awt.GridLayout
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.*

class DateAttributeComponent: IComponent<AttributeView>
{

    override fun accept(view: AttributeView): Boolean {
        return view.xmlElementAttribute.name == "Date" &&
                view.xmlElement.name == "Event"
    }

    override fun component(view: AttributeView): JPanel {
        val panel = JPanel()
        panel.layout = GridLayout(0,2)
        val label = JLabel(view.xmlElementAttribute.name)
        label.horizontalAlignment = SwingConstants.RIGHT
        panel.add(label)

        val date = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(view.xmlElementAttribute.value)
        val jSpinner = JSpinner(SpinnerDateModel(date,
            null, null, Calendar.MINUTE))
        jSpinner.addChangeListener {
            val value = jSpinner.value.toString()
            ActionStack.doAction(
                EditAttributeValueAction(
                    view.xmlElementAttribute,
                    value
                )
            )
        }
        panel.add(jSpinner)
        return panel
    }

}