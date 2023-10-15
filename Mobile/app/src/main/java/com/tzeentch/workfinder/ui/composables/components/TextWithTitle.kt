package com.tzeentch.workfinder.ui.composables.components

import android.widget.Space
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp

@Composable
fun TextWithTitle(title: String, field: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text("$title:", color = Color(0xFFD4D4D5))

        Spacer(modifier = Modifier.width(5.dp))

        Text(text = field)
    }
}