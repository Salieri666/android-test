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


@Preview(showBackground = true)
@Composable
fun PreviewUserDetailsComponent() {

    val user = UserUiModel(
        1, "1", "Test_name", "test@email.com", true,
        23, "Company_name", "+11111",
        "test_address", "about", "blue",
    "apple",
        "2022",
        emptyList(),
        "Coordinates_22"
    )

    UserDetailsComponent(user = user, modifier = Modifier.fillMaxSize())
}

@Composable
fun UserDetailsComponent(
    modifier: Modifier = Modifier,
    user: UserUiModel,
    onPnoheClick: () -> Unit = {},
    onEmailClick: () -> Unit = {},
    onCoordinatesClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {

        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = user.name, modifier = Modifier
                    .paddingFromBaseline(bottom = 48.dp, top = 16.dp)
                    .padding(start = 16.dp, end = 16.dp),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Text(
            text = "Info", modifier = Modifier
                .paddingFromBaseline(bottom = 12.dp, top = 12.dp)
                .padding(start = 16.dp, end = 16.dp),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = Color.Blue
        )

        CharacterItem(title = "Age", value = user.age.toString())
        CharacterItem(title = "Company", value = user.company)
        CharacterItem(title = "Email", value = user.email, iconId = R.drawable.ic_email)
        CharacterItem(title = "Phone", value = user.phone, iconId = R.drawable.ic_phone)
        CharacterItem(title = "Address", value = user.age.toString())
        CharacterItem(title = "About", value = user.age.toString())
        CharacterItem(value = "Eye color", iconId = R.drawable.ic_dot,
            tintColor = user.getColorByEye()
        )
        CharacterItem(value = "Favorite fruit", iconId = user.getDrawableByFruit())
        CharacterItem(title = "Coordinates", value = user.coordinates, iconId = R.drawable.gps_pin)

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
        .background(Color.White)
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
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    text = title, style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                )
            }

            iconId?.let {
                Icon(imageVector = ImageVector.vectorResource(id = it), tint = tintColor ?: Color.Unspecified, contentDescription = null)
            }
        }

    }
}