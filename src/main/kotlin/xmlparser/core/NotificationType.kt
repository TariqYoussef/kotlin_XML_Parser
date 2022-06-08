package xmlparser.core

enum class NotificationTypeAttribute {
    CHANGE_NAME,
    CHANGE_VALUE
}

enum class NotificationTypeElement {
    CHANGE_NAME,
    CHANGE_VALUE,
    ADD_CHILD,
    ADD_ATTRIBUTE,
    REMOVE_CHILD,
    REMOVE_ATTRIBUTE
}

enum class NotificationTypeContext {
    CHANGE_ROOT
}