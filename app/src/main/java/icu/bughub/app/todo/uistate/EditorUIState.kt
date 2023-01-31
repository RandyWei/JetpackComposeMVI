package icu.bughub.app.todo.uistate

import icu.bughub.app.todo.entity.Todo

sealed class EditorUIState {
    //加载态
    object Loading : EditorUIState()

    //成功态
    data class Success(val todo: Todo) : EditorUIState()
}
