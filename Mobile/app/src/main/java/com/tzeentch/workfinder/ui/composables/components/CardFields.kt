package com.tzeentch.workfinder.ui.composables.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardFields(
    textField: String,
    inputHolder: MutableState<String>,
    buttonHolder: MutableState<String>,
    buttonTitle: String,
    buttonText1: String,
    buttonText2: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomOutlinedTextField(
            defText = inputHolder.value,
            defTitle = textField,
            hint = "",
            onValueChange = {
                inputHolder.value = it
            })

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = buttonTitle, modifier = Modifier.fillMaxWidth())
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { buttonHolder.value = buttonText1 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (buttonText1 == buttonHolder.value) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    contentColor = Color.White
                ), modifier = Modifier
                    .weight(1F)
                    .height(45.dp)
            ) {
                Text(text = buttonText1)
            }
            Button(
                onClick = { buttonHolder.value = buttonText2 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (buttonText2 == buttonHolder.value) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1F).height(45.dp)
            ) {
                Text(text = buttonText2)
            }
        }
    }
}