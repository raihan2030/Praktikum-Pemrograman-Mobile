package com.example.scrollablecompose.screens

import android.content.res.Configuration
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scrollablecompose.R
import com.example.scrollablecompose.Routes
import com.example.scrollablecompose.ui.theme.ScrollableComposeTheme
import com.example.scrollablecompose.viewmodel.RiderListViewModel
import com.example.scrollablecompose.viewmodel.RiderListViewModelFactory
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController? = null) {
    val title = stringResource(R.string.topappbarr_title)
    val factory = remember(title) { RiderListViewModelFactory(title) }
    val viewModel: RiderListViewModel = viewModel(factory = factory, key = title)

    val kamenRiderList by viewModel.riderList.collectAsState()
    val clickedRider by viewModel.onClickRider.collectAsState()

    val orientationMode = LocalConfiguration.current.orientation
    val context = LocalContext.current

    LaunchedEffect(clickedRider) {
        clickedRider?.let { rider ->
            navController?.navigate(Routes.detailScreen + "/${rider.id}")
            viewModel.clearRiderClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
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
            items(kamenRiderList) { rider ->
                val onClick = {
                    Timber.tag("ListScreen").i("Tombol Detail ditekan untuk: ${rider.name}")
                    viewModel.onRiderClick(rider)
                }

                if (orientationMode == Configuration.ORIENTATION_PORTRAIT) {
                    RiderCardPortrait(rider = rider, context = context, onClick = onClick)
                } else {
                    RiderCardLandscape(rider = rider, context = context, onClick = onClick)
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun ListScreenPortPreview() {
    ScrollableComposeTheme {
        ListScreen()
    }
}

//@Preview(
//    showBackground = true,
//    widthDp = 800,
//    heightDp = 360
//)
//@Composable
//fun ListScreenLandPreview() {
//    ScrollableComposeTheme {
//        ListScreen()
//    }
//}