package icu.bughub.app.todo.actions

import icu.bughub.app.todo.entity.Todo

sealed class ListAction {
    object FetchList : ListAction()

    data class Done(val todo: Todo,val done:Boolean) : ListAction()
}
