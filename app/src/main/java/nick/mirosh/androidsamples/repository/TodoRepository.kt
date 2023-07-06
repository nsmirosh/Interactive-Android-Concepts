package nick.mirosh.androidsamples.repository

import kotlinx.coroutines.flow.Flow
import nick.mirosh.androidsamples.models.DatabaseTodo

interface TodoRepository {
    suspend fun getTodos(): Flow<List<DatabaseTodo>>
    suspend fun insert(todo: DatabaseTodo)
}