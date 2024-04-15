package nick.mirosh.androidsamples.ui.jetpack_compose.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun SearchScreen(
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel()
) {
    val searchReply by searchScreenViewModel.data.collectAsState(initial = "")

    SearchScreenContent(
        searchReply = searchReply,
        onSearchQueryChanged = searchScreenViewModel::onSearchQueryChanged
    )

}

@Composable
fun SearchScreenContent(
    searchReply: String,
    onSearchQueryChanged: (String) -> Unit,
) {
    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        TextField(
            value = input,
            onValueChange = {
                onSearchQueryChanged(it)
                input = it
            }
        )
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = searchReply
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreenContent(
        searchReply = "Search reply",
        onSearchQueryChanged = {}
    )
}