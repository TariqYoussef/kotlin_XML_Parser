package xmlparser.editor.view

import xmlparser.editor.ActionStack
import xmlparser.editor.DefaultContext
import xmlparser.editor.controller.MainController
import java.awt.BorderLayout
import java.awt.Dimension
import java.io.PrintWriter
import javax.swing.*
import kotlin.system.exitProcess


class Frame(private val mainController: MainController) : JFrame("XML Editor") {

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
            println(mainController.context)
            val fileChooser = JFileChooser()
            val option = fileChooser.showSaveDialog(this@Frame)
            if (option == JFileChooser.APPROVE_OPTION) {
                val printWriter = PrintWriter(fileChooser.selectedFile)
                printWriter.println(mainController.context)
                printWriter.close()
            }
        }
        file.add(saveMenuItem)

        val historyMenuItem = JMenuItem("History")
        historyMenuItem.addActionListener {
            val historyView = HistoryView(this@Frame, "History")
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
                JOptionPane.showMessageDialog(this@Frame, "No Actions to Undo")
            else
                ActionStack.undoAction()
        }
        edit.add(undoMenuItem)

        val redoMenuItem = JMenuItem("Redo")
        redoMenuItem.addActionListener {
            if(ActionStack.isRedoStackEmpty())
                JOptionPane.showMessageDialog(this@Frame, "No Actions to Redo")
            else
                ActionStack.redoAction()
        }
        edit.add(redoMenuItem)

        menuBar.add(file)
        menuBar.add(edit)

        jMenuBar = menuBar
    }

    fun open() {
        if(!mainController.isContextInitialized())
        {
            mainController.context = DefaultContext()
        }

        add(ElementView(mainController, mainController.context.rootXmlElement!!), BorderLayout.CENTER)
        isVisible = true
    }
}
