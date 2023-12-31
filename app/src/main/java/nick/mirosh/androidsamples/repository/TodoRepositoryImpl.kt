package nick.mirosh.androidsamples.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import nick.mirosh.androidsamples.database.TodoDao
import nick.mirosh.androidsamples.di.IoDispatcher
import nick.mirosh.androidsamples.models.DatabaseTodo
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val todoDao: TodoDao
) : TodoRepository {

    override suspend fun getTodos(): Flow<List<DatabaseTodo>> {
        return withContext(dispatcher) {
            todoDao.getAllTodos()
        }
    }

    override suspend fun insert(todo: DatabaseTodo) {
        withContext(dispatcher) {
            todoDao.insert(todo)
        }
    }

    override suspend fun delete(todoId: Int) {
        withContext(dispatcher) {
            todoDao.deleteById(todoId)
        }
    }
}