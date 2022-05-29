package xmlparser.gui

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement
import xmlparser.gui.view.ElementView
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JFrame

class Application : JFrame("XML Editor") {

    private val context = XmlContext()

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(300, 300)

        context.setRootXmlElement(XmlElement("Root"))

        add(ElementView(context.rootXmlElement!!))
        //add(JButton("undo"))
        //add(JButton("redo"))
    }

    fun open() {
        isVisible = true
    }
}

fun main() {
    val w = Application()
    w.open()
}