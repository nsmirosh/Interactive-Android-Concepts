package nick.mirosh.androidsamples.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import nick.mirosh.androidsamples.database.TodoDao
import nick.mirosh.androidsamples.models.DatabaseTodo
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val todoDao: TodoDao
) : TodoRepository {
    override suspend fun getTodos(): List<DatabaseTodo> {
        return withContext(dispatcher) {
            todoDao.getAllTodos()
        }
    }
}