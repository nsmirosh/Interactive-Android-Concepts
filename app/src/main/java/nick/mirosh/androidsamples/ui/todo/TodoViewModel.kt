package nick.mirosh.androidsamples.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.androidsamples.models.Todo
import nick.mirosh.androidsamples.models.asDomainModel
import nick.mirosh.androidsamples.repository.TodoRepository
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val todoRepository: TodoRepository) :
    ViewModel() {

    private val todoItems = MutableStateFlow(listOf<Todo>())
    val todoList = todoItems.asStateFlow()

    init {
        viewModelScope.launch {
            todoRepository.getTodos().collect {
                todoItems.value = it.map { databaseTodo ->
                    databaseTodo.asDomainModel()
                }
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            todoRepository.delete(id)
        }
    }
}

