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
import com.example.androidtest.ui.utils.getUserUiModelList

@Preview(showBackground = true)
@Composable
fun PreviewUserListComponent() {
    UserListComponent(userList = getUserUiModelList(1), modifier = Modifier.fillMaxSize())
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

