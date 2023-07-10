package nick.mirosh.androidsamples.ui.todo.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoDetailsScreen(
    onTodoAdded: (String, String) -> Unit
) {
    var titleText by remember { mutableStateOf("") }
    //create a field where the  user can enter the title of the
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
