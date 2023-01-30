package icu.bughub.app.todo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icu.bughub.app.todo.entity.Todo
import icu.bughub.app.todo.service.TodoDao
import icu.bughub.app.todo.service.TodoService
import icu.bughub.app.todo.utils.RandomUtil
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EditorViewModel(dao: TodoDao) : ViewModel() {


    var todo by mutableStateOf(Todo())
        private set

    //toast 内容
    private val toastContent = MutableSharedFlow<String>()
    val toast = toastContent.asSharedFlow()

    private val service = TodoService(dao)

    //时间格式
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.CHINA)


    fun onValueChange(it: String) {
        todo = todo.copy(content = it)
    }

    fun onSave() {
        viewModelScope.launch {

            if (todo.id.trim() == "") {
                todo.date = dateFormatter.format(Date())
                todo.id = RandomUtil.randomString()
                service.insert(todo)
            } else {
                service.update(todo)
            }
            toastContent.emit("保存成功")
        }
    }

    fun fetch(id: String) {
        viewModelScope.launch {
            if (id != "") {
                todo = service.loadById(id)
            }
        }
    }
}