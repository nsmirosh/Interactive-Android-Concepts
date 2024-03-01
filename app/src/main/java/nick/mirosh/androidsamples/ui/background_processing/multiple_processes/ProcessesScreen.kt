package nick.mirosh.androidsamples.ui.background_processing.multiple_processes

import android.content.Intent
import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ProcessesScreen(
) {
    val localContext = LocalContext.current

    MaterialTheme {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
            ) {
                var sameProcessState by remember { mutableStateOf<ProcessState>(ProcessState.Idle) }
                var differentProcessState by remember { mutableStateOf<ProcessState>(ProcessState.Idle) }

                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        Color(
                            parseColor(
                                when (sameProcessState) {
                                    ProcessState.Idle -> "#00ab41"
                                    ProcessState.Started -> "#f96900"
                                    ProcessState.Crashed -> "#f90000"
                                }
                            )
                        )
                    ),
                    onClick = {
                        val intent = Intent(
                            localContext,
                            MySameProcessService::class.java
                        )
                        sameProcessState = when (sameProcessState) {
                            ProcessState.Idle ->
                                ProcessState.Started

                            ProcessState.Started -> {
                                intent.putExtra("cause_crash", true)
                                ProcessState.Crashed
                            }

                            ProcessState.Crashed ->
                                ProcessState.Idle

                        }
                        localContext.startService(intent)
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = when(sameProcessState) {
                            ProcessState.Idle -> "Start service in same process"
                            ProcessState.Started -> "Service is running. Click to crash."
                            ProcessState.Crashed -> "Service crashed"
                        }

                        , fontSize = 18.sp
                    )
                }

                Button(

                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),

                    colors = ButtonDefaults.buttonColors(
                        Color(
                            parseColor(
                                when (differentProcessState) {
                                    ProcessState.Idle -> "#00ab41"
                                    ProcessState.Started -> "#f96900"
                                    ProcessState.Crashed -> "#f90000"
                                }
                            )
                        )
                    ),
                    onClick = {
                        val intent = Intent(
                            localContext,
                            MySeparateProcessService::class.java
                        )
                        differentProcessState = when (differentProcessState) {
                            ProcessState.Idle ->
                                ProcessState.Started

                            ProcessState.Started -> {
                                intent.putExtra("cause_crash", true)
                                ProcessState.Crashed
                            }

                            ProcessState.Crashed ->
                                ProcessState.Idle

                        }
                        localContext.startService(intent)
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = when(differentProcessState) {
                            ProcessState.Idle -> "Start service in different process"
                            ProcessState.Started -> "Service is running. Click to crash."
                            ProcessState.Crashed -> "Service crashed"
                        },
                        fontSize = 18.sp
                    )
                }
            }
        }

    }
}

sealed class ProcessState {
    data object Idle : ProcessState()
    data object Started : ProcessState()
    data object Crashed : ProcessState()
}

@Preview
@Composable
fun ProcessesScreenPreview() {
    ProcessesScreen()
}