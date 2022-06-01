package xmlparser.plugins.calendar

import xmlparser.gui.ActionStack
import xmlparser.gui.action.EditAttributeValueAction
import xmlparser.gui.view.AttributeView
import xmlparser.gui.view.component.IAttributeComponent
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.*

class DateAttributeComponent: IAttributeComponent
{

    override fun accept(attributeView: AttributeView): Boolean {
        return attributeView.xmlElementAttribute.name == "Date" &&
                attributeView.xmlElement.name == "Event"
    }

    override fun draw(attributeView: AttributeView) {
        val label = JLabel(attributeView.xmlElementAttribute.name)
        label.horizontalAlignment = SwingConstants.RIGHT
        attributeView.add(label)

        val date = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(attributeView.xmlElementAttribute.value)
        val jSpinner = JSpinner(SpinnerDateModel(date,
            null, null, Calendar.MINUTE))
        jSpinner.addChangeListener {
            val value = jSpinner.value.toString()
            ActionStack.doAction(
                EditAttributeValueAction(
                    attributeView.xmlElementAttribute,
                    value
                )
            )
        }
        attributeView.add(jSpinner)
    }

}