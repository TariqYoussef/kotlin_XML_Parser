package xmlparser.gui

import tornadofx.App
import tornadofx.launch
import xmlparser.gui.views.MainView

class Application : App(MainView::class)

fun main(args: Array<String>) {
    launch<Application>(args)
}