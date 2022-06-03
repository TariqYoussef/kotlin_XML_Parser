# XML Parser

This project has 2 main modules:
1. **xmlparser.core** - Standalone library that creates and edits xml documents with packages/classes/methods well documented.
2. **xmlparser.gui** - A graphical user interface that helps to edit a xml document. It uses the xmlparse.core library and its highly extendable using plugins.  

***

## xmlparser.core
### Basic usage
First we need to create a xml context, we can do that with the following code:
```kotlin
val xmlContext: XmlContext = XmlContext()
```
A xml file must have a root/principal node, we can create a xml element and set it as the root element.
We can do this by:
```kotlin
val rootElement: XmlElement = XmlElement(name = "Root")
xmlContext.setRootXmlElement(rootElement)
```
To dump a xml context we can do the following:
```kotlin
xmlContext.dump() // or toString()
```