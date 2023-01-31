package icu.bughub.app.todo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icu.bughub.app.todo.actions.EditorAction
import icu.bughub.app.todo.entity.Todo
import icu.bughub.app.todo.service.TodoDao
import icu.bughub.app.todo.service.TodoService
import icu.bughub.app.todo.uistate.EditorUIState
import icu.bughub.app.todo.uistate.ToastEffect
import icu.bughub.app.todo.utils.RandomUtil
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EditorViewModel(dao: TodoDao) : ViewModel() {


    var uiState by mutableStateOf<EditorUIState>(EditorUIState.Loading)
        private set


    //toast 内容
    private val _toast = MutableSharedFlow<ToastEffect>()
    val toast = _toast.asSharedFlow()

    private val service = TodoService(dao)

    //时间格式
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.CHINA)

    fun dispatch(action: EditorAction) {
        when (action) {
            is EditorAction.FetchDetail -> fetch(action.id)
            is EditorAction.OnTextChanged -> onValueChange(action.content)
            EditorAction.Save -> onSave()
        }
    }

    private fun onValueChange(it: String) {
        val successState = uiState as EditorUIState.Success
        uiState = successState.copy(todo = successState.todo.copy(content = it))
    }

    private fun onSave() {
        viewModelScope.launch {
            val todo = (uiState as EditorUIState.Success).todo
            if (todo.id.trim() == "") {
                todo.date = dateFormatter.format(Date())
                todo.id = RandomUtil.randomString()
                service.insert(todo)
            } else {
                service.update(todo)
            }
            _toast.emit(ToastEffect.Message("保存成功"))
        }
    }

    private fun fetch(id: String) {
        viewModelScope.launch {
            uiState = if (id != "0") {
                val todo = service.loadById(id)
                EditorUIState.Success(todo)
            } else {
                EditorUIState.Success(Todo())
            }
        }
    }
}