package icu.bughub.app.todo.uistate

sealed class ToastEffect {
    data class Message(val content: String) : ToastEffect()
}
