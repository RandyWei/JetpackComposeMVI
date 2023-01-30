package icu.bughub.app.todo.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import icu.bughub.app.todo.service.AppDatabase
import icu.bughub.app.todo.viewmodel.EditorViewModel
import icu.bughub.app.todo.viewmodel.ListViewModel

@Suppress("UNCHECKED_CAST")
class TodoViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(AppDatabase.getInstance(context).dao()) as T
        } else if (modelClass.isAssignableFrom(EditorViewModel::class.java)) {
            return EditorViewModel(AppDatabase.getInstance(context).dao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}