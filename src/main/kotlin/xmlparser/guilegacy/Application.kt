package xmlparser.guilegacy

import tornadofx.App
import tornadofx.launch
import xmlparser.guilegacy.views.MainView

class Application : App(MainView::class)

fun main(args: Array<String>) {
    launch<Application>(args)
}