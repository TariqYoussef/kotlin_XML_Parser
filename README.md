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
### Xml Elements and Attributes
A xml element is a something commonly known as a xml tag, this is a xml element:
````xml
<element></element>
````
A xml element can have a value:
````xml
<element>Value Xml Element</element>
````
And multiple attributes:
````xml
<element attribute1="valueAttribute2" attribute2="valueAttribute2"></element>
````
A xml element can also have a child or multiple children:
````xml
<element>
    <child></child>
</element>
````
To create a xml element we need to instantiate the XmlElement class:
```kotlin
val xmlElement: XmlElement = XmlElement("name", "value")
```
We can add an attribute to a xmlElement like this:
```kotlin
val xmlElementAttribute: XmlElementAttribute = XmlElementAttribute("attributeName", "attributeValue")
xmlElement.addAttribute(xmlElementAttribute)
```
We can a child to a xml element this way:
```kotlin
val xmlElementChild: XmlElement = XmlElement("child", "valueChild")
xmlElement.addChild(xmlElementChild) // or xmlElement.addChild("child", "valueChild")
```
### Visitor and observation
A xml context is visitable and observable.

### Serializing classes - Reflexion