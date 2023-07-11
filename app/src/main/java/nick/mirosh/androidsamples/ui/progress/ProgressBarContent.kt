package nick.mirosh.androidsamples.ui.progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarContent(
    viewModel: ProgressBarViewModel,
    modifier: Modifier = Modifier
) {
    DrawCanvas()
    /*    var isLinearProgress by remember { mutableStateOf(false) }
        val indicatorProgress by viewModel.progress.collectAsState()
        val progressAnimDuration = 300
        val progressAnimation by animateFloatAsState(
            targetValue = indicatorProgress.toFloat() / 100f,
            animationSpec = tween(
                durationMillis = progressAnimDuration,
                easing = FastOutSlowInEasing
            ),
            label = ""
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedCounter(count = indicatorProgress)
                Text(
                    text = "Start",
                    fontSize = 30.sp,
                    modifier = Modifier
                        .clickable {
                            viewModel.onStartPressed()
                        }
                        .padding(24.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Switch(
                    checked = isLinearProgress,
                    onCheckedChange = { isLinearProgress = !isLinearProgress }
                )
                if (isLinearProgress) {
                    LinearProgressIndicator(
                        progress = progressAnimation,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(32.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                } else {
                    CircularProgressIndicator(
                        progress = progressAnimation,
                        strokeWidth = 16.dp,
                        trackColor = androidx.compose.ui.graphics.Color.LightGray,
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                    )
                }
            }
        }*/
}

@Composable
fun IncrementDecrement() {
    var counter by remember { mutableIntStateOf(0) }

    Button(onClick = {
        counter += 8
    }) {
        Text("increment")
    }
    Button(onClick = {
        counter -= 8
    }) {
        Text("decrement")
    }
}


@Composable
fun DrawCanvas() {
    Canvas(modifier = Modifier
        .height(100.dp)
        .width(100.dp)) {
        //Draw a half circle line
        drawArc(
            color = Color.Red,
            topLeft = Offset(100f, 100f),
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = 10f)
        )
    }
}