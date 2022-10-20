package com.example.androidtest.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtest.ui.model.UserUiModel

@Preview(showBackground = true)
@Composable
fun PreviewUserListComponent() {
    val item = UserUiModel(
        1, 2, "1", "Test_name", "test@email.com", true,
        23, "Company_name", "+11111",
        "test_address", "about", "blue",
        "apple",
        "2022",
        emptyList(),
        "Coordinates_22"
    )
    val list = List(1) { item }

    UserListComponent(userList = list, modifier = Modifier.fillMaxSize())
}


@Composable
fun UserListComponent(
    modifier: Modifier = Modifier,
    userList: List<UserUiModel>,
    onClick: (UserUiModel) -> Unit = {}
) {

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(userList,
            key = {
                it.id
            }
        ) {

            UserItemComponent(user = it, onClick = { onClick(it) })

        }
    }

}

