package xmlparser.gui.legacy

import tornadofx.App
import tornadofx.launch
import xmlparser.gui.legacy.views.MainView

class Application : App(MainView::class)

fun main(args: Array<String>) {
    launch<Application>(args)
}