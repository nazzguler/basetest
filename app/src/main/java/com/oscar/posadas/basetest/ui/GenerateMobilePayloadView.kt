package com.oscar.posadas.basetest.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oscar.posadas.basetest.R
import com.oscar.posadas.basetest.ui.common.Button

@Composable
fun GenerateMobilePayloadView(
    mobilePayload: String? = null,
    getMobilePayload: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    Button(
        onClick = getMobilePayload,
        text = stringResource(id = R.string.app_generate_mobile_payload)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        backgroundColor = Color(0xFFA8DBE5)
    ) {
        Column(
            modifier = Modifier
                .height(70.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        clipboardManager.setText(AnnotatedString(mobilePayload.orEmpty()))
                    }, textAlign = TextAlign.Center, text = mobilePayload.orEmpty()
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    GenerateMobilePayloadView(mobilePayload = "mobile payload") {

    }
}