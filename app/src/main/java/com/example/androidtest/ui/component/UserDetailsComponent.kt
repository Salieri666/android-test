package com.example.androidtest.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtest.R
import com.example.androidtest.ui.model.UserUiModel
import com.example.androidtest.ui.model.getColorByEye
import com.example.androidtest.ui.model.getDrawableByFruit
import com.example.androidtest.ui.utils.getUserUiModel


@Preview(showBackground = true)
@Composable
fun PreviewUserDetailsComponent() {

    UserDetailsComponent(user = getUserUiModel(1), modifier = Modifier.fillMaxSize())
}

@Composable
fun UserDetailsComponent(
    modifier: Modifier = Modifier,
    user: UserUiModel,
    onPhoneClick: (String) -> Unit = {},
    onEmailClick: (String) -> Unit = {},
    onCoordinatesClick: (Double, Double) -> Unit = {_, _ ->}
) {
    Column(
        modifier = modifier
    ) {

        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 16.dp)

        ) {
            Text(
                text = user.name, modifier = Modifier
                    .paddingFromBaseline(bottom = 48.dp, top = 16.dp)
                    .padding(start = 16.dp, end = 16.dp),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        Text(
            text = stringResource(R.string.info), modifier = Modifier
                .paddingFromBaseline(bottom = 12.dp, top = 42.dp)
                .padding(start = 16.dp, end = 16.dp),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = Color.Blue
        )

        CharacterItem(title = stringResource(R.string.age), value = user.age.toString())
        CharacterItem(title = stringResource(R.string.company), value = user.company)
        CharacterItem(title = stringResource(R.string.email), value = user.email, iconId = R.drawable.ic_email, onClick = {onEmailClick(user.email)})
        CharacterItem(title = stringResource(R.string.phone), value = user.phone, iconId = R.drawable.ic_phone, onClick = {onPhoneClick(user.phone)})
        CharacterItem(title = stringResource(R.string.address), value = user.address)
        CharacterItem(title = stringResource(R.string.about), value = user.about)
        CharacterItem(title = stringResource(R.string.registered), value = user.registered)
        CharacterItem(value = stringResource(R.string.eye_color), iconId = R.drawable.ic_dot,
            tintColor = user.getColorByEye()
        )
        CharacterItem(value = stringResource(R.string.favorite_fruit), iconId = user.getDrawableByFruit())
        CharacterItem(title = stringResource(R.string.coordinated), value = user.coordinates, iconId = R.drawable.gps_pin, onClick = {
            onCoordinatesClick(user.latitude, user.longitude)
        })

    }
}

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    title: String = "",
    value: String = "",
    tintColor: Color? = null,
    @DrawableRes iconId: Int? = null,
    onClick: () -> Unit = {}
) {
    Box(modifier = modifier
        .clickable { onClick.invoke() }
        .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = value,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = title, style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            iconId?.let {
                Icon(imageVector = ImageVector.vectorResource(id = it), tint = tintColor ?: MaterialTheme.colorScheme.onBackground, contentDescription = null)
            }
        }

    }
}
