package xmlparser.core

interface IObservable<T> {
    val observers: MutableList<T>

    fun addObserver(observer: T) = observers.add(observer)

    fun removeObserver(observer: T) = observers.remove(observer)

    fun removeAllObservers() = observers.clear()

    fun notifyObservers(handler: (T) -> Unit) = observers.toList().forEach { handler(it) }
}