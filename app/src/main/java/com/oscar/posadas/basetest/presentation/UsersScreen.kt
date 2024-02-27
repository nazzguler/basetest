package com.oscar.posadas.basetest.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oscar.posadas.basetest.network.NameDto
import com.oscar.posadas.basetest.network.PictureDto
import com.oscar.posadas.basetest.network.UserDto

@Composable
fun UsersScreen(
    users: List<UserDto>
) {
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        users.forEach { user ->
            item {
                UserItem(user = user)
            }
        }
    }
}

@Composable
fun UserItem(
    user: UserDto
) {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        elevation = 5.dp
    ) {
        user.email?.let { email ->
            Text(
                modifier = Modifier,
                text = user.name.getDisplayedName()
            )
            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = email
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val user1 = UserDto(
        gender = "",
        name = NameDto(title = "Mr", first = "Beast", last = "User"),
        email = "test1@test1.com",
        picture = PictureDto(medium = "", large = "", thumbnail = "")
    )
    val user2 = UserDto(
        gender = "",
        name = NameDto(title = "Sr", first = "User2", last = "User2"),
        email = "test1@test1.com",
        picture = PictureDto(medium = "", large = "", thumbnail = "")
    )
    UsersScreen(users = listOf(user1, user2))
}