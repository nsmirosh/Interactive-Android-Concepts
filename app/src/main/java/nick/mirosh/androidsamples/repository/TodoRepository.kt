package nick.mirosh.androidsamples.repository

import nick.mirosh.androidsamples.models.DatabaseTodo

interface TodoRepository {
    suspend fun getTodos(): List<DatabaseTodo>
    suspend fun insert(todo: DatabaseTodo)
}