package icu.bughub.app.todo.service

import icu.bughub.app.todo.entity.Todo

class TodoService(private val dao: TodoDao) {

    /**
     * 获取所有数据
     *
     * @return
     */
    suspend fun getAll(): List<Todo> {
        return dao.getAll()
    }

    /**
     * 通过 id 查找待办
     *
     * @param id
     * @return
     */
    suspend fun loadById(id: String): Todo {
        return dao.loadById(id)
    }

    /**
     * 增加待办
     *
     * @param todo
     */
    suspend fun insert(todo: Todo) {
        dao.insert(todo)
    }

    /**
     * 更新待办
     *
     * @param todo
     */
    suspend fun update(todo: Todo) {
        dao.update(todo)
    }

}