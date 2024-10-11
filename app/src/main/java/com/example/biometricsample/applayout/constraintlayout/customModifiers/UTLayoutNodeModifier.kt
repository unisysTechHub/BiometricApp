package com.example.biometricsample.applayout.constraintlayout.customModifiers

import android.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement

class UTCircleNode(var color : androidx.compose.ui.graphics.Color) : DrawModifierNode, Modifier.Node() {
    override fun ContentDrawScope.draw() {
        drawCircle(color=color)
    }
}

private data class CircleElement(val color: androidx.compose.ui.graphics.Color) :
    ModifierNodeElement<UTCircleNode>() {
    override fun create() =
        UTCircleNode(color = color)


    override fun update(node: UTCircleNode) {
        node.color = color
    }
}

fun Modifier.circle(color : androidx.compose.ui.graphics.Color) = this then CircleElement(color)