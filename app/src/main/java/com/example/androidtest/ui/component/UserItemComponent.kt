package com.example.androidtest.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtest.R
import com.example.androidtest.ui.model.UserUiModel
import com.example.androidtest.ui.utils.getUserUiModel


@Preview(showBackground = true)
@Composable
fun PreviewUserItemComponent() {

    UserItemComponent(user = getUserUiModel(1))
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
                .background(MaterialTheme.colorScheme.secondary)
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
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    )
                    Text(
                        text = user.email, modifier = Modifier
                            .paddingFromBaseline(bottom = 4.dp)
                            .padding(start = 4.dp)
                        ,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    )
                }
                Text(
                    text = if (user.isActive) stringResource(R.string.active) else stringResource(R.string.disable),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                )
            }

        }


    }
}