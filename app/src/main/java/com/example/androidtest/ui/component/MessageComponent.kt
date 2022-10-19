package com.example.androidtest.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun PreviewMessageComponent() {
    MessageComponent(modifier = Modifier.fillMaxSize(), message = "Test")
}


@Composable
fun MessageComponent(modifier: Modifier = Modifier, message: String) {

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = message, style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }

}