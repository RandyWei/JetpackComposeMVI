package icu.bughub.app.todo.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import icu.bughub.app.todo.actions.ListAction
import icu.bughub.app.todo.ui.components.TodoItem
import icu.bughub.app.todo.uistate.ListUIState
import icu.bughub.app.todo.uistate.ToastEffect
import icu.bughub.app.todo.viewmodel.ListViewModel
import icu.bughub.app.todo.viewmodel.factory.TodoViewModelFactory
import kotlinx.coroutines.flow.collect

@Composable
fun ListScreen(onNavigateToEditor: (String) -> Unit) {

    val context = LocalContext.current

    //ViewModel 需要用到 context ,如果通过直接实例化 ViewModel传入 LocalContext.current 的话，会导致 state 失效
    //解决办法：
    // 1、使用 AndroidViewModel 中的 application.applicationContext
    // 2、通过 ViewModelProvider.Factory 构造 ViewModel
    // 3、通过使用 Hilt 依赖注入
    val listViewModel: ListViewModel = viewModel(factory = TodoViewModelFactory(context))

    val uiState = listViewModel.uiState

    LaunchedEffect(key1 = Unit) {
        //获取列表数据
        listViewModel.dispatch(ListAction.FetchList)
    }


    val toast = listViewModel.toast
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = toast, key2 = lifecycleOwner) {
        //如果使用mutableStateOf,因为有初始值，所以启动的时候也会有提示，在这里可以判断如果是空字符串的话就不弹 Toast
        //MutableStateFlow跟上面是一样的问题

        //设置只有在STARTED生命周期之后才执行
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            toast.collect {
                when (it) {
                    is ToastEffect.Message -> {
                        Toast.makeText(context, it.content, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text("List")
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            onNavigateToEditor("0")
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) {

        when (uiState) {
            is ListUIState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "暂无待办事项")
                }
            }
            ListUIState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ListUIState.Success -> {
                LazyColumn() {
                    items(uiState.list) { item ->
                        TodoItem(todo = item, onCheckedChange = {
                            listViewModel.dispatch(ListAction.Done(item, it))
                        }, modifier = Modifier.clickable {
                            onNavigateToEditor(item.id)
                        })

                    }
                }
            }
        }
    }

}