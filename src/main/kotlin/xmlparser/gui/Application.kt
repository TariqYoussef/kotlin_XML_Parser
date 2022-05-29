package xmlparser.gui

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement
import xmlparser.gui.view.ElementView
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*
import kotlin.system.exitProcess

class Application : JFrame("XML Editor") {

    private val context = XmlContext()

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(300, 300)

        context.setRootXmlElement(XmlElement("Root"))

        contentPane.layout = BorderLayout()
        add(ElementView(context.rootXmlElement!!), BorderLayout.CENTER)

        createMenuBar()
    }

    private fun createMenuBar() {

        val menuBar = JMenuBar()

        val file = JMenu("File")

        val saveMenuItem = JMenuItem("Save")
        saveMenuItem.addActionListener { exitProcess(0) }
        file.add(saveMenuItem)

        val exitMenuItem = JMenuItem("Exit")
        exitMenuItem.addActionListener { exitProcess(0) }
        file.add(exitMenuItem)

        val edit = JMenu("Edit")

        val undoMenuItem = JMenuItem("Undo")
        undoMenuItem.addActionListener {
            if(ActionStack.isUndoStackEmpty())
                JOptionPane.showMessageDialog(this@Application, "No Actions to Undo");
            else
                ActionStack.undoAction()
        }
        edit.add(undoMenuItem)

        val redoMenuItem = JMenuItem("Redo")
        redoMenuItem.addActionListener {
            if(ActionStack.isRedoStackEmpty())
                JOptionPane.showMessageDialog(this@Application, "No Actions to Redo");
            else
            ActionStack.redoAction()
        }
        edit.add(redoMenuItem)

        menuBar.add(file)
        menuBar.add(edit)

        jMenuBar = menuBar
    }

    fun open() {
        isVisible = true
    }
}

fun main() {
    val w = Application()
    w.open()
}