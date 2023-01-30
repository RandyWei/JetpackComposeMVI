package icu.bughub.app.todo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import icu.bughub.app.todo.entity.Todo

@Composable
fun TodoItem(modifier: Modifier = Modifier, todo: Todo, onCheckedChange: (Boolean) -> Unit) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(text = todo.content, maxLines = 2, fontSize = 16.sp)
            Text(text = todo.date, fontSize = 12.sp, maxLines = 1)
        }

        Checkbox(checked = todo.done, onCheckedChange = onCheckedChange)

    }

}