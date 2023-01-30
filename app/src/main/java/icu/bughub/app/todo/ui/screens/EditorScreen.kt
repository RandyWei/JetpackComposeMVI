package icu.bughub.app.todo.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import icu.bughub.app.todo.viewmodel.EditorViewModel
import icu.bughub.app.todo.viewmodel.factory.TodoViewModelFactory

@Composable
fun EditorScreen(
    id: String,
    editorViewModel: EditorViewModel = viewModel(
        factory = TodoViewModelFactory(
            LocalContext.current
        )
    ), onPop: () -> Unit
) {

    val toast = editorViewModel.toast
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        editorViewModel.fetch(id)
    }

    LaunchedEffect(key1 = toast, key2 = lifecycleOwner) {
        //如果使用mutableStateOf,因为有初始值，所以启动的时候也会有提示，在这里可以判断如果是空字符串的话就不弹 Toast
        //MutableStateFlow跟上面是一样的问题

        //设置只有在STARTED生命周期之后才执行
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            toast.collect {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Editor")
            }, navigationIcon = {
                IconButton(onClick = { onPop() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }, actions = {
                TextButton(onClick = {
                    editorViewModel.onSave()
                }) {
                    Text(text = "Save", color = Color.White)
                }
            })
        },
    ) {

        TextField(value = editorViewModel.todo.content, onValueChange = {
            editorViewModel.onValueChange(it)
        }, modifier = Modifier.fillMaxSize())

    }
}