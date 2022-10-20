package com.example.androidtest.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtest.ui.model.UserUiModel


@Preview(showBackground = true)
@Composable
fun PreviewUserItemComponent() {

    val item =  UserUiModel(
        1,2, "1", "Test_name", "test@email.com", true,
        23, "Company_name", "+11111",
        "test_address", "about", "blue",
        "apple",
        "2022",
        emptyList(),
        "Coordinates_22", 0.0,0.0
    )

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
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
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
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }


    }
}