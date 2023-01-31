package icu.bughub.app.todo.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icu.bughub.app.todo.actions.ListAction
import icu.bughub.app.todo.entity.Todo
import icu.bughub.app.todo.service.TodoDao
import icu.bughub.app.todo.service.TodoService
import icu.bughub.app.todo.uistate.ListUIState
import icu.bughub.app.todo.uistate.ToastEffect
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ListViewModel(dao: TodoDao) : ViewModel() {

    //MVI
    //Model： ViewModel + Service + 状态
    //View: UI
    //Intent: 意图（动作），其实这里应该叫 Action，

    var uiState by mutableStateOf<ListUIState>(ListUIState.Loading)
        private set

    //toast 内容
    private val _toast = MutableSharedFlow<ToastEffect>()
    val toast = _toast.asSharedFlow()

    private val service = TodoService(dao)


    /**
     * UI 所有意图都通过调度方法调用 ViewModel逻辑
     * 其中教程里面，有可能这里使用了 Channel 通道进行通信
     * @param action
     */
    fun dispatch(action: ListAction) {
        when (action) {
            is ListAction.Done -> done(action.todo, action.done)
            ListAction.FetchList -> fetchList()
        }
    }

    private fun fetchList() {
        viewModelScope.launch {
            val list = service.getAll()
            uiState = if (list.isEmpty()) {
                ListUIState.Error("暂无待办事项")
            } else {
                ListUIState.Success(list)
            }
        }
    }

    /**
     * 设置待办状态
     *
     * @param item 待办对象
     * @param it 状态
     */
    private fun done(item: Todo, it: Boolean) {
        viewModelScope.launch {

            val list = (uiState as ListUIState.Success).list.map { todo ->
                var new = todo
                if (todo.id == item.id) new = todo.copy(done = it)
                new
            }

            uiState = ListUIState.Success(list)

            if (it) {
                _toast.emit(
                    ToastEffect.Message(
                        "${
                            item.content.substring(
                                0,
                                minOf(8, item.content.length)
                            )
                        }事项完成"
                    )
                )
            }
        }
    }
}