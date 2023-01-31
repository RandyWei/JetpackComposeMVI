package icu.bughub.app.todo.uistate

import icu.bughub.app.todo.entity.Todo

sealed class ListUIState {

    //加载态
    object Loading : ListUIState()

    //错误态
    data class Error(val message: String) : ListUIState()

    //成功态
    data class Success(val list: List<Todo>) : ListUIState()

}