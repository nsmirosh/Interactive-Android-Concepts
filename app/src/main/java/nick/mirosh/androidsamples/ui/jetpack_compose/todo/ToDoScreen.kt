import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nick.mirosh.androidsamples.R
import nick.mirosh.androidsamples.models.Todo
import nick.mirosh.androidsamples.ui.jetpack_compose.todo.TodoViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoListScreen(viewModel: TodoViewModel, onNewTodoClicked: () -> Unit) {
    val todoList by viewModel.todoList.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (todoList.isNotEmpty())
            TodoList(todoList = todoList, onDeleteClicked = viewModel::delete)
        else {
            Text(
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(fontSize = 24.sp, color = Color.Black),
                text = "No todos yet"
            )
        }
        FloatingActionButton(
            onClick = onNewTodoClicked,
            shape = RectangleShape,
            backgroundColor = colorResource(id = R.color.todo_screen_accent),
            modifier = Modifier
                .padding(24.dp)
                .align(
                    Alignment.BottomEnd
                )
                .clip(RoundedCornerShape(16.dp))
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
fun TodoList(todoList: List<Todo>, onDeleteClicked: (Int) -> Unit) {
    LazyColumn {
        items(todoList.size) { index ->
            ToDoCard(todo = todoList[index], onDeleteClicked = onDeleteClicked)
        }
    }
}

@Composable
fun ToDoCard(todo: Todo, onDeleteClicked: (Int) -> Unit) {
    var titleText by remember {
        mutableStateOf(todo.title)
    }
    var isInEditMode by remember {
        mutableStateOf(false)
    }
    Row {
        if (isInEditMode) {
            TextField(
                value = titleText,
                onValueChange = {
                    titleText = it
                },
                label = { Text("Title") }
            )

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Confirm",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .padding(2.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { isInEditMode = false }

            )
        } else {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { isInEditMode = true },
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                ),
                text = todo.title,
            )
        }
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = Color.Black,
            modifier = Modifier
                .size(24.dp)
                .padding(2.dp)
                .align(Alignment.CenterVertically)
                .clickable { onDeleteClicked(todo.id) }

        )
    }
}

@Composable
@Preview
fun TodoListPreview() {
    val card1 =
        Todo(
            id = 0,
            title = "Title",
            description = "Description",
            completed = false,
        )
    val card2 =
        Todo(
            id = 0,
            title = "Title",
            description = "Description",
            completed = false,
        )
    val card3 =
        Todo(
            id = 0,
            title = "Title",
            description = "Description",
            completed = false,

            )
    val list = listOf(card1, card2, card3)
    LazyColumn {
        items(3) {
            ToDoCard(list[it], {})
        }
    }
}