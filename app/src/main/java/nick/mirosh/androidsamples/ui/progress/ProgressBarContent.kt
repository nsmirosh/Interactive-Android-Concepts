package nick.mirosh.androidsamples.ui.progress

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressBarContent2(
    viewModel: ProgressBarViewModel,
    modifier: Modifier = Modifier
) {
    var isLinearProgress by remember { mutableStateOf(false) }
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
    }
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

