package xmlparser.editor

import xmlparser.core.XmlContext
import xmlparser.editor.view.AttributeView
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.HistoryView
import xmlparser.editor.view.component.IComponent
import xmlparser.editor.view.menuitem.IMenuItem
import xmlparser.editor.view.menuitem.attribute.RemoveAttributeMenuItem
import xmlparser.editor.view.menuitem.attribute.RenameAttributeMenuItem
import xmlparser.editor.view.menuitem.element.AddAttributeMenuItem
import xmlparser.editor.view.menuitem.element.AddChildMenuItem
import xmlparser.editor.view.menuitem.element.RemoveElementMenuItem
import xmlparser.editor.view.menuitem.element.RenameElementMenuItem
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.io.PrintWriter
import javax.swing.*
import kotlin.system.exitProcess


class Application : JFrame("XML Editor") {

    @Inject
    private lateinit var context: XmlContext

    @InjectAdd
    val elementViewPluginMenuItems = mutableListOf<IMenuItem<ElementView>>()
    @InjectAdd
    val attributeViewPluginMenuItems = mutableListOf<IMenuItem<AttributeView>>()
    @InjectAdd
    val attributeViewPluginComponents = mutableListOf<IComponent<AttributeView, Component>>()
    @InjectAdd
    val elementValueViewPluginComponents = mutableListOf<IComponent<ElementView, Component>>()

    val elementViewMenuItems: List<IMenuItem<ElementView>> = listOf(
        AddChildMenuItem(),
        RenameElementMenuItem(),
        AddAttributeMenuItem(),
        RemoveElementMenuItem()
    )
    val attributeViewMenuItems: List<IMenuItem<AttributeView>> = listOf(
        RemoveAttributeMenuItem(),
        RenameAttributeMenuItem()
    )

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(300, 300)

        contentPane.layout = BorderLayout()

        createMenuBar()
    }

    private fun createMenuBar() {

        val menuBar = JMenuBar()

        val file = JMenu("File")

        val saveMenuItem = JMenuItem("Save")
        saveMenuItem.addActionListener {
            println(context)
            val fileChooser = JFileChooser()
            val option = fileChooser.showSaveDialog(this@Application)
            if (option == JFileChooser.APPROVE_OPTION) {
                val printWriter = PrintWriter(fileChooser.selectedFile)
                printWriter.println(context)
                printWriter.close()
            }
        }
        file.add(saveMenuItem)

        val historyMenuItem = JMenuItem("History")
        historyMenuItem.addActionListener {
            val historyView = HistoryView(this@Application, "History")
            historyView.open()
        }
        file.add(historyMenuItem)

        val exitMenuItem = JMenuItem("Exit")
        exitMenuItem.addActionListener { exitProcess(0) }
        file.add(exitMenuItem)

        val edit = JMenu("Edit")

        val undoMenuItem = JMenuItem("Undo")
        undoMenuItem.addActionListener {
            if(ActionStack.isUndoStackEmpty())
                JOptionPane.showMessageDialog(this@Application, "No Actions to Undo")
            else
                ActionStack.undoAction()
        }
        edit.add(undoMenuItem)

        val redoMenuItem = JMenuItem("Redo")
        redoMenuItem.addActionListener {
            if(ActionStack.isRedoStackEmpty())
                JOptionPane.showMessageDialog(this@Application, "No Actions to Redo")
            else
                ActionStack.redoAction()
        }
        edit.add(redoMenuItem)

        menuBar.add(file)
        menuBar.add(edit)

        jMenuBar = menuBar
    }

    fun open() {
        if(!this::context.isInitialized)
        {
            context = DefaultContext()
        }

        add(ElementView(this, context.rootXmlElement!!), BorderLayout.CENTER)
        isVisible = true
    }
}

fun main() {
    val w = Injector.create(Application::class) as Application
    w.open()
}