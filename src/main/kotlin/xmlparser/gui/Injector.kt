package xmlparser.gui

import xmlparser.plugins.Test
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation

@Target(AnnotationTarget.PROPERTY)
annotation class Inject

@Target(AnnotationTarget.PROPERTY)
annotation class InjectAdd

object Injector {
    fun create(kClass: KClass<*>): Any {
        //val propertiesMap: Map<String, String> = FileReader.readFileAsMap("di.properties")
        val createdInstance = kClass.createInstance()
        kClass.declaredMemberProperties.filter {
            it.hasAnnotation<Inject>()
        }.forEach {
            println(it)
            val injectedProperty = Test::class.createInstance()
                //(Class.forName(propertiesMap["${kClass.simpleName}.${it.name}"]).kotlin).createInstance()
            (it as KMutableProperty<*>).setter.call(createdInstance, injectedProperty)
        }
        /*
        kClass.declaredMemberProperties.filter {
            it.hasAnnotation<InjectAdd>()
        }.forEach { it: KProperty1<out Any, *> ->
            println(it)
            val prop = it.call(createdInstance) as MutableCollection<Any>


            val injectedPropertiesStrings: String? = propertiesMap["${kClass.simpleName}.${it.name}"]
            injectedPropertiesStrings?.split(",")?.forEach { it2: String ->
                println("Injector::InjectADD::$it2")
                prop.add((Class.forName(it2).kotlin).createInstance())
            }                    //(it as KMutableProperty<*>).setter.call(createdInstance, DefaultSetup())
        }
        */

        return createdInstance
    }
}