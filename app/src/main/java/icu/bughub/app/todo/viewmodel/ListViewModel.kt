package icu.bughub.app.todo.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icu.bughub.app.todo.entity.Todo
import icu.bughub.app.todo.service.TodoDao
import icu.bughub.app.todo.service.TodoService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ListViewModel(dao: TodoDao) : ViewModel() {

    //数据列表
    var list by mutableStateOf<List<Todo>>(listOf())
        private set

    //toast 内容
    private val toastContent = MutableSharedFlow<String>()
    val toast = toastContent.asSharedFlow()

    //loading 状态
    var loading by mutableStateOf(true)

    private val service = TodoService(dao)

    fun fetchList() {
        viewModelScope.launch {
            loading = true
            list = service.getAll()
            loading = false
        }
    }

    /**
     * 设置待办状态
     *
     * @param item 待办对象
     * @param it 状态
     */
    fun done(item: Todo, it: Boolean) {
        viewModelScope.launch {
            list = list.map { todo ->
                var new = todo
                if (todo.id == item.id) new = todo.copy(done = it)
                new
            }
            toastContent.emit("${item.content.substring(0, minOf(8, item.content.length))}事项完成")
        }
    }
}