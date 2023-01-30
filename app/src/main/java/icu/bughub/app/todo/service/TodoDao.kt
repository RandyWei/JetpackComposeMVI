package icu.bughub.app.todo.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import icu.bughub.app.todo.entity.Todo

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    suspend fun getAll(): List<Todo>

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend  fun loadById(id: String): Todo

    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend  fun update(todo: Todo)

}