package xmlparser.plugins.musiclibrary.component

import xmlparser.editor.view.ElementView
import xmlparser.editor.view.component.IComponent
import java.awt.GridLayout
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel


class AlbumElementValueComponent : IComponent<ElementView> {

    override fun accept(view: ElementView): Boolean = view.xmlElement.name == "Album"

    override fun component(view: ElementView): JPanel? {
        if(view.xmlElement.value == "")
            return null
        val panel = JPanel()
        panel.layout = GridLayout(0,1)
        val img = ImageIO.read(File(view.xmlElement.value))
        val pic = JLabel(ImageIcon(img))
        panel.add(pic)
        return panel
    }

}