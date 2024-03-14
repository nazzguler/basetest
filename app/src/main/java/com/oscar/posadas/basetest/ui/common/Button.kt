package com.oscar.posadas.basetest.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Button(
    onClick: () -> Unit,
    text: String
) {
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF106FA9),
            contentColor = Color.White,
            disabledBackgroundColor = Color(0xFF106FA9).copy(alpha = 0.4f),
            disabledContentColor = Color.White,
        )
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}