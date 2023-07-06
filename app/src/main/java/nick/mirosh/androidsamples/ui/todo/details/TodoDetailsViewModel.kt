package nick.mirosh.androidsamples.ui.todo.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nick.mirosh.androidsamples.models.DatabaseTodo
import nick.mirosh.androidsamples.repository.TodoRepository
import javax.inject.Inject

@HiltViewModel
class TodoDetailsViewModel @Inject constructor(private val todoRepository: TodoRepository) :
    ViewModel() {

    fun insertTodo(title: String, description: String) {
        viewModelScope.launch {
            todoRepository.insert(
                DatabaseTodo(
                    title = title,
                    description = description
                )
            )
        }
    }
}