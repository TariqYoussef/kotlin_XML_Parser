package xmlparser.gui

import xmlparser.gui.view.MainView
import java.awt.Dimension
import javax.swing.JFrame

class Application : JFrame("XML Editor") {
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(300, 300)

        add(MainView("root"))
    }

    fun open() {
        isVisible = true
    }
}

fun main() {
    val w = Application()
    w.open()
}