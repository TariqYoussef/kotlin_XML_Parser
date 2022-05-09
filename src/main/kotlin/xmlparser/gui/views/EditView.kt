package xmlparser.gui.views

import tornadofx.*

class EditView : View() {
    override val root = menubar {
        menu("Edit") {
            item("Undo").action {

            }
            item("Redo").action {

            }
        }
    }
}