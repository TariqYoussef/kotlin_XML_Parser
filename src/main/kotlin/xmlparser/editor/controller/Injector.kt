package xmlparser.editor.controller

import java.io.FileInputStream
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.isAccessible

@Target(AnnotationTarget.PROPERTY)
annotation class Inject

@Target(AnnotationTarget.PROPERTY)
annotation class InjectAdd

object Injector {
    private fun getPropertiesMap(): Map<String, List<String>>
    {
        val propertiesMap = mutableMapOf<String, List<String>>()
        val fileInputStream = FileInputStream("di.properties")
        val properties = Properties()
        properties.load(fileInputStream)
        properties.propertyNames().asIterator().forEach {
            propertiesMap[it as String] = properties.getProperty(it)
                .filter { c: Char -> !c.isWhitespace() }.split(",")
        }
        return propertiesMap
    }

    fun create(kClass: KClass<*>): Any {
        val propertiesMap = getPropertiesMap()
        val createdInstance = kClass.createInstance()
        kClass.declaredMemberProperties.filter {
            it.hasAnnotation<Inject>()
        }.forEach {
            it.isAccessible = true
            propertiesMap["${kClass.simpleName}.${it.name}"]?.forEach { property ->
                val injectedProperty = (Class.forName(property).kotlin).createInstance()
                (it as KMutableProperty<*>).setter.call(createdInstance, injectedProperty)
            }
        }

        kClass.declaredMemberProperties.filter {
            it.hasAnnotation<InjectAdd>()
        }.forEach {
            it.isAccessible = true
            val prop = it.call(createdInstance) as MutableCollection<Any>
            propertiesMap["${kClass.simpleName}.${it.name}"]?.forEach { property ->
                val injectedProperty = (Class.forName(property).kotlin).createInstance() as Any
                prop.add(injectedProperty)
            }
        }

        return createdInstance
    }
}