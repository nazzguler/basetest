package com.oscar.posadas.basetest.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oscar.posadas.basetest.R
import com.oscar.posadas.basetest.ui.common.Button

@Composable
fun ProcessIdTokenView(
    processIdToken: () -> Unit,
    onIdTokenChange: (String) -> Unit,
    idToken: String? = null
) {
    Button(
        onClick = processIdToken, text = stringResource(id = R.string.app_process_id_token)
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
            TextField(
                value = idToken.orEmpty(),
                placeholder = { Text(stringResource(R.string.app_paste_id_token)) },
                onValueChange = { onIdTokenChange(it) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { processIdToken() })
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    ProcessIdTokenView(
        processIdToken = {},
        onIdTokenChange = {},
        idToken = "ID Token"
    )
}