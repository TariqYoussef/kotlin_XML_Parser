package xml

data class XmlHeader(val version: String, val encoding: String, val standalone: String) {
    fun dump(): String = "<?xml version=\"$version\" encoding=\"$encoding\" standalone=\"$standalone\"?>"
}