package icu.bughub.app.todo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 待办事项
 *
 * @property id
 * @property content 待办内容
 * @property date 加入时间
 * @property done 是否完成
 */
@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey var id: String ="",
    val content: String="",
    var date: String="",
    var done: Boolean = false
)
