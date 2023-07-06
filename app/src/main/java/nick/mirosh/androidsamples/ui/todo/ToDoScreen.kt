import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nick.mirosh.androidsamples.models.Todo
import nick.mirosh.androidsamples.ui.todo.TodoViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoScreen(todoViewModel: TodoViewModel) {
    val todoList by todoViewModel.todoList.collectAsStateWithLifecycle()
    if (todoList.isNotEmpty())
        LazyColumn {
            items(todoList.size) { index ->
                ToDoCard(todoList[index])
            }
        }
    else {
        Text(
            style = TextStyle(color = Color.Black),
            text = "No todos yet"
        )
    }
}

@Composable
fun ToDoCard(todo: Todo) {
    Text(
        style = TextStyle(color = Color.Black),
        text = todo.title,
    )
}