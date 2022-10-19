package com.example.androidtest.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtest.ui.model.UserUiModel


@Preview(showBackground = true)
@Composable
fun PreviewUserItemComponent() {

    val item = UserUiModel(1, "1", "Test_name", "test@email.com", true)

    UserItemComponent(user = item)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItemComponent(
    modifier: Modifier = Modifier,
    user: UserUiModel,
    onClick: () -> Unit = {}
) {

    ElevatedCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = user.name,
                        modifier = Modifier
                            .paddingFromBaseline(bottom = 4.dp),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = user.email, modifier = Modifier
                            .paddingFromBaseline(bottom = 4.dp)
                            .padding(start = 4.dp)
                        ,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    )
                }
                Text(
                    text = if (user.isActive) "active" else "disable",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        //color = if (user.isActive) Color.Green else Color.Red
                    )
                )
            }

        }


    }
}