package icu.bughub.app.todo.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import icu.bughub.app.todo.entity.Todo

@Database(entities = [Todo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): TodoDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) { //避免数据库造成死锁
                var instance = INSTANCE

                if (instance == null) {
                    instance =
                        Room.databaseBuilder(context, AppDatabase::class.java, "todo_db").build()
                }

                return instance
            }
        }

    }

}