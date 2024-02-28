package com.oscar.posadas.basetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oscar.posadas.basetest.ui.theme.BaseTestTheme
import com.pingidentity.pingidsdkv2.PingOne

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mobilePayload = PingOne.generateMobilePayload(this)
        setContent {
            BaseTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PayloadView(mobilePayload)
                }
            }
        }
    }
}

@Composable
fun PayloadView(payload: String) {
    val clipboardManager = LocalClipboardManager.current
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(30.dp)
            .wrapContentSize(Alignment.Center)

    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    clipboardManager.setText(AnnotatedString(payload))

                },
            textAlign = TextAlign.Center,
            text = payload
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BaseTestTheme {
        PayloadView("Android")
    }
}