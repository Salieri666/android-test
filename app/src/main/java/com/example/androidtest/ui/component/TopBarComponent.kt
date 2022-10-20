package com.example.androidtest.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.androidtest.R
import com.example.androidtest.ui.model.TopBarModel


@Preview
@Composable
fun PreviewTopBarComponent() {
    val model = TopBarModel("Test_title", true)
    TopBarComponent(model = model)
}

@Preview
@Composable
fun PreviewTopBarComponentWithoutArrow() {
    val model = TopBarModel("Test_title", false)
    TopBarComponent(model = model)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    modifier: Modifier = Modifier,
    model: TopBarModel,
    onBackClick: () -> Unit = {}
) {

    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
        ),
        modifier = modifier,
        title = { Text(
            text = model.title,
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        ) },
        navigationIcon = {
            if (model.showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, stringResource(R.string.action_back) , tint = Color.White)
                }
            }
        }
    )
}