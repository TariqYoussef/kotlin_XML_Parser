package xmlparser.core

interface IObservable<T> {
    val observers: MutableList<T>

    /**
     * Adds observer.
     */
    fun addObserver(observer: T) = observers.add(observer)

    /**
     * Removes observer.
     */
    fun removeObserver(observer: T) = observers.remove(observer)

    /**
     * Removes all observers.
     */
    fun removeAllObservers() = observers.clear()

    /**
     * Notifies observers.
     */
    fun notifyObservers(handler: (T) -> Unit) = observers.toList().forEach { handler(it) }
}