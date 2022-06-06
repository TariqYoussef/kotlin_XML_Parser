package xmlparser.plugins.musiclibrary.component

import xmlparser.editor.view.ElementView
import xmlparser.editor.view.component.IComponent
import javax.swing.JPanel

class AlbumsElementValueComponent : IComponent<ElementView> {

    override fun accept(view: ElementView): Boolean = view.xmlElement.name == "Albums"

    override fun component(view: ElementView): JPanel? {
        return null
    }
}