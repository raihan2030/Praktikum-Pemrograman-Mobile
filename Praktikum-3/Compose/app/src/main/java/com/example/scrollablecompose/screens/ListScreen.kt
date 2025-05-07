package com.example.scrollablecompose.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.scrollablecompose.R
import com.example.scrollablecompose.data.getKamenRiderList
import com.example.scrollablecompose.ui.theme.ScrollableComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController) {
    val config = LocalConfiguration.current
    val orientationMode by remember { mutableIntStateOf(config.orientation) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.topappbarr_title))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(getKamenRiderList()) { rider ->
                RiderCard(rider, navController, orientationMode)
            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "Redmi Note 13",
    widthDp = 390,
    heightDp = 800
)
@Composable
fun ListScreenPortraitPreview() {
    ScrollableComposeTheme {
        val navController = rememberNavController()
        ListScreen(navController = navController)
    }
}

@Preview(
    showBackground = true,
    name = "Redmi Note 13",
    widthDp = 800,
    heightDp = 390
)
@Composable
fun ListScreenLandscapePreview() {
    ScrollableComposeTheme {
        val navController = rememberNavController()
        ListScreen(navController = navController)
    }
}