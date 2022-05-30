package xmlparser.gui

import xmlparser.gui.view.ElementView

interface IPluginAction {
    val displayName: String
    fun getAction(elementView: ElementView): IAction
}