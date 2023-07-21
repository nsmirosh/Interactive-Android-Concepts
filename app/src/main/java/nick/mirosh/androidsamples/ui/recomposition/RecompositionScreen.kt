package nick.mirosh.androidsamples.ui.recomposition

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


/*

This screen is intended to demonstrate Recomposition
Here we can observer that the only Composable function that's
running after the initial composition is the LoginError function
 */

@Composable
fun SimpleRecomposition(showError: Boolean) {
    if (showError) {
        LoginError()
    }
    LoginInput() // This call site affects where LoginInput is placed in Composition
}


/*
Even though showError state is on the same level as
Recomposition Screen and Button - the only thing that
recomposes when it's recomposed is the LoginError


The reason for that is that the "call site" - or location
of the source code in the composition doesn't change
and were not passing any new parameters
 */

@Composable
fun RecompositionScreenRunner() {
    var showError by remember {
        mutableStateOf(false)
    }
    Column {
        SimpleRecomposition(showError)
        Button(onClick = {
            showError = !showError
        }) {
            Log.d("RecompositionScreen", "Button")
            Text(text = "Click me to see recomposition")
        }
    }
}


@Composable
fun LoginInput() {
    Text("Login")
    Log.d("RecompositionScreen", "LoginInput: ")
}

@Composable
fun LoginError() {
    Text("Error")
    Log.d("RecompositionScreen", "LoginError ")
}