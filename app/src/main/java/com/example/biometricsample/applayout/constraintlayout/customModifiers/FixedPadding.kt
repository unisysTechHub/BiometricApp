package com.example.biometricsample.applayout.constraintlayout.customModifiers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset

fun Modifier.fixedPadding() = this then FixedPaddingElement

data object FixedPaddingElement : ModifierNodeElement<FixedPaddingNode>() {
    override fun create() = FixedPaddingNode()
    override fun update(node: FixedPaddingNode) {}
}

class FixedPaddingNode : LayoutModifierNode, Modifier.Node() {
    private val PADDING = 16.dp

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val paddingPx = PADDING.roundToPx()
        val horizontal = paddingPx * 2
        val vertical = paddingPx * 2

        val placeable = measurable.measure(constraints)

        val width = placeable.width
        val height = placeable.height
        return layout(width, height) {
            placeable.place(paddingPx, paddingPx)
        }
    }
}

@Preview
@Composable
fun fixedPadding_preview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Test", modifier = Modifier.fixedPadding())
    }

}