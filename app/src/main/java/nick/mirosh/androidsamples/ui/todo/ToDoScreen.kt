import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nick.mirosh.androidsamples.models.Todo
import nick.mirosh.androidsamples.ui.todo.TodoViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoScreen(todoViewModel: TodoViewModel, onNewTodoClicked: () -> Unit) {
    val todoList by todoViewModel.todoList.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()) {

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
        FloatingActionButton(
            onClick = onNewTodoClicked,
            modifier = Modifier
                .padding(16.dp)
                .align(
                    Alignment.BottomEnd
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun ToDoCard(todo: Todo) {
    Text(
        style = TextStyle(color = Color.Black),
        text = todo.title,
    )
}