package xmlparser.gui

import xmlparser.gui.view.ElementView

interface IPluginActionPopupMenu {
    /**
     * Action display name.
     */
    val displayName: String

    /**
     * Gets
     */
    fun getAction(elementView: ElementView): IAction
}