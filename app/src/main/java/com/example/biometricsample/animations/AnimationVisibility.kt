package com.example.biometricsample.animations

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.splineBasedDecay
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@Composable
fun AnimationVisibilityTest() {

    var visible by remember {
        mutableStateOf(true)
    }


    Column(modifier=Modifier.fillMaxSize(),verticalArrangement = Arrangement.spacedBy(16.dp)) {
//        AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
//                Box(modifier = Modifier
//                    .background(Color.Red))
//
//
//        }
        AnimatedVisibility(visible = visible, modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), enter = fadeIn(), exit = fadeOut()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Red))
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Blue)
            .clickable { visible = !visible })

    }
}

@Composable
fun AnimationVisibilityAlphaTest(){
    var visible by remember {
         mutableStateOf(true)
    }
      val animatedAlhpa by animateFloatAsState(targetValue = if (visible) 1f else 0f)
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .graphicsLayer { this.alpha = animatedAlhpa }
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Green)
            .align(Alignment.TopCenter))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .graphicsLayer { this.alpha = animatedAlhpa }
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Red)
            .align(Alignment.Center)
            .clickable { visible = !visible })
    }

}

@Composable
fun AnimationIntOffsetTest(){
    var moved by remember {
        mutableStateOf(true)
    }
    val pxToMove = with(LocalDensity.current){
        100.dp.toPx().roundToInt()
    }
     val animateOffset by animateIntOffsetAsState(targetValue = if (moved) IntOffset(pxToMove,pxToMove) else IntOffset.Zero)
   Box(modifier = Modifier.fillMaxSize()) {
       Box(modifier = Modifier
           .offset { animateOffset }
           .background(Color.Blue)
           .size(200.dp)
           .clickable { moved = !moved }) {
       }
   }

}
@Composable
fun paddingAnimationTest() {
    var contentpaddingTogle  by remember {
        mutableStateOf(true)
    }

    val animatedPadding by animateDpAsState(targetValue = if (contentpaddingTogle) 16.dp else 0.dp)

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(animatedPadding)
        .background(Color.Blue)
        .clickable { contentpaddingTogle = !contentpaddingTogle }    ) {
    }
}

@Composable
fun BoxWithinColumnOffsetTest(){
    var toggle by remember {
        mutableStateOf(false)
    }
    val offsetTarget = if (toggle) IntOffset(150,150) else IntOffset.Zero
    val animateIntOffset by animateIntOffsetAsState(targetValue =
    offsetTarget)
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(modifier = Modifier
            .size(200.dp)
            .background(Color.Blue)) {

        }
        Box(modifier = Modifier
            .layout { measurable, constraints ->
                val offsetValue = if (isLookingAhead) offsetTarget else animateIntOffset
                val placeable = measurable.measure(constraints)
                layout(
                    width = placeable.width + offsetValue.x,
                    height = placeable.height + offsetValue.y
                ) {
                    placeable.placeRelative(offsetValue)
                }
            }
            .size(200.dp)
            .background(Color.Blue)
            .clickable { toggle = !toggle }) {
        }
        Box(modifier = Modifier
            .size(200.dp)
            .background(Color.Blue)) {

        }
    }

}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ElevationAnimationTest(){
    val mutableInteractionSource = remember {
        MutableInteractionSource()
    }
    val x = 100.dp.value.toInt()
  val pressed =  mutableInteractionSource .collectIsPressedAsState()
  val elevation =  animateDpAsState(targetValue = if (pressed.value) 32.dp else 0.dp)
    Box(modifier = Modifier.fillMaxSize()) {
        

        Box(modifier = Modifier
            .size(200.dp)
            .align(Alignment.Center)
            .graphicsLayer { this.shadowElevation = elevation.value.toPx() }
            .clickable(interactionSource = mutableInteractionSource, indication = null) {

            }
            .background(Color.Green))
    }
}

@Composable
fun ScaleTextAnimation(){
           val infiniteTransition = rememberInfiniteTransition()
               val scale by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 8f,
                    animationSpec = infiniteRepeatable(tween(1000))
                )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Hello", modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin.Center
            }
            .align(Alignment.Center),
            style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated)
        )
    }

}
@Composable
fun AnimateContent(viewModel : ScreenViewmodel= ScreenViewmodel()){
                val networkstate = viewModel.networkState.observeAsState()
    AnimatedContent(targetState = networkstate.value,transitionSpec = {
        fadeIn(
            animationSpec = tween(3000)
        ) togetherWith fadeOut(animationSpec = tween(3000))
    }) {
       when(it) {
           Loading ->{ UTProgress()}
           Error -> { Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
               Text(text = "Error ", modifier = Modifier
                   .wrapContentSize()
                   .align(Alignment.Center), style = TextStyle(color = Color.Red))
           } }
           is Success -> { Text(text = "Home", modifier=Modifier.fillMaxSize(), style = TextStyle(color = Color.Blue, fontSize = TextUnit(12f,
               TextUnitType.Sp)
           ))}

           null -> { }
       }
    }
}

@Composable
fun UTProgress() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment =  Alignment.Center) {
       CircularProgressIndicator()
    }

}

@Composable
fun StartAnimationOnLaunch()
{
  val alphAnimation =  remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(Unit) {
        alphAnimation.animateTo(1f)
    }
    Box(modifier = Modifier
        .background(Color.Blue)
        .graphicsLayer { this.alpha = alphAnimation.value }) {

    }
}

@Composable
fun CrossfadeAnimation(){
    var visible by remember {
        mutableStateOf(true)
    }

    var selected by remember { mutableStateOf(false) }
// Animates changes when `selected` is changed.
    Column(modifier=Modifier.fillMaxSize(),verticalArrangement = Arrangement.spacedBy(16.dp)) {
//        AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
//                Box(modifier = Modifier
//                    .background(Color.Red))
//
//
//        }
        Crossfade(targetState = visible, modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)) { it ->
           if (it){
               Box(modifier = Modifier
                   .fillMaxWidth()
                   .height(200.dp)
                   .background(Color.Red))

           } else
           {
               Box(modifier = Modifier
                   .fillMaxWidth()
                   .height(200.dp)
                   .background(Color.Gray))

           }

        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Blue)
            .clickable { visible = !visible })

    }
}
@Composable
fun Gesture() {
    val offset = remember {
        androidx.compose.animation.core.Animatable(
            Offset(0f, 0f),
            Offset.VectorConverter
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                coroutineScope {
                    while (true) {
                        // Detect a tap event and obtain its position.
                        awaitPointerEventScope {
                            val position = awaitFirstDown().position

                            launch {
                                // Animate to the tap position.
                                offset.animateTo(position)
                            }
                        }
                    }
                }
            }
    ) {
        Circle(modifier = Modifier.offset { offset.value.toIntOffset() })
    }
}

private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())
@Composable
fun updateTransition(){
    var selected by remember { mutableStateOf(false) }
// Animates changes when `selected` is changed.
    val transition = updateTransition(selected, label = "selected state")
}

@Composable
fun Circle(modifier: Modifier){
    val value by animateFloatAsState(
        targetValue = 1f,
        animationSpec = keyframes {
            durationMillis = 375
            0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
            0.2f at 15 with FastOutLinearInEasing // for 15-75 ms
            0.4f at 75 // ms
            0.4f at 225 // ms
        }
    )
    Canvas(modifier = modifier.size(100.dp)) {
            drawCircle(Color.Blue, radius = 100.dp.toPx(),center= Offset(100.dp.toPx(),100.dp.toPx()))

    }
}
@Composable
    fun Modifier.swipeToDismiss(
        onDismissed: () -> Unit
    ): Modifier = composed {
        val offsetX = remember { androidx.compose.animation.core.Animatable(0f) }
        pointerInput(Unit) {
            // Used to calculate fling decay.
            val decay = splineBasedDecay<Float>(this)
            // Use suspend functions for touch events and the Animatable.
            coroutineScope {
                while (true) {
                    val velocityTracker = VelocityTracker()
                    // Stop any ongoing animation.
                    offsetX.stop()
                    awaitPointerEventScope {
                        // Detect a touch down event.
                        val pointerId = awaitFirstDown().id

                        horizontalDrag(pointerId) { change ->
                            // Update the animation value with touch events.
                            launch {
                                offsetX.snapTo(
                                    offsetX.value + change.positionChange().x
                                )
                            }
                            velocityTracker.addPosition(
                                change.uptimeMillis,
                                change.position
                            )
                        }
                    }
                    // No longer receiving touch events. Prepare the animation.
                    val velocity = velocityTracker.calculateVelocity().x
                    val targetOffsetX = decay.calculateTargetValue(
                        offsetX.value,
                        velocity
                    )
                    // The animation stops when it reaches the bounds.
                    offsetX.updateBounds(
                        lowerBound = -size.width.toFloat(),
                        upperBound = size.width.toFloat()
                    )
                    launch {
                        if (targetOffsetX.absoluteValue <= size.width) {
                            // Not enough velocity; Slide back.
                            offsetX.animateTo(
                                targetValue = 0f,
                                initialVelocity = velocity
                            )
                        } else {
                            // The element was swiped away.
                            offsetX.animateDecay(velocity, decay)
                            onDismissed()
                        }
                    }
                }
            }
        }
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
    }

@Preview
@Composable
fun AnimationVisibilityAlpha_Preview(){
    AnimationVisibilityAlphaTest()
}

@Preview
@Composable
fun UTProgress_preview(){
    UTProgress()
}