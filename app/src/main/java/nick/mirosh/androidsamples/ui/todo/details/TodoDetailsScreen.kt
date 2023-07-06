package nick.mirosh.androidsamples.ui.todo.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoDetailsScreen(todoDetailsViewModel: TodoDetailsViewModel, onTodoAdded:(String, String) -> Unit) {
    //create a field where the  user can enter the title of the
    var titleText by rememberSaveable { mutableStateOf("") }
    var descriptionText by rememberSaveable { mutableStateOf("") }
    Column {
        TextField(
            value = titleText,
            onValueChange = {
                titleText = it
            },
            label = { Text("Title") }
        )
        TextField(
            value = descriptionText,
            onValueChange = {
                descriptionText = it
            },
            label = { Text("Title") }
        )
        Button(onClick = {
        onTodoAdded(titleText, descriptionText)
        }) {
            Text("Insert Todo")
        }
    }
}
