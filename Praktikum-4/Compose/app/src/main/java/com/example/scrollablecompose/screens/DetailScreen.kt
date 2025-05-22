package com.example.scrollablecompose.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scrollablecompose.R
import com.example.scrollablecompose.model.KamenRider
import com.example.scrollablecompose.model.KamenRiderRepository.getKamenRiderList
import com.example.scrollablecompose.ui.theme.ScrollableComposeTheme
import com.example.scrollablecompose.viewmodel.DetailViewModel
import com.example.scrollablecompose.viewmodel.DetailViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController? = null, id: Int) {
    val factory = remember { DetailViewModelFactory(id) }
    val viewModel: DetailViewModel = viewModel(factory = factory)
    val riderState = viewModel.rider.collectAsState()
    val rider = riderState.value
    val orientation = LocalConfiguration.current.orientation

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.detail)) },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (rider != null) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                DetailPortrait(rider, padding)
            } else {
                DetailLandscape(rider, padding)
            }
        } else {
            Text(
                text = "Data not found",
                fontSize = 25.sp,
                modifier = Modifier.padding(padding)
            )
        }
    }
}


@Composable
fun DetailPortrait(rider: KamenRider, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RiderImage(rider)
        Spacer(Modifier.height(15.dp))
        RiderTextInfo(rider)
    }
}


@Composable
fun DetailLandscape(rider: KamenRider, paddingValues: PaddingValues) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        RiderImage(rider, Modifier.align(Alignment.CenterVertically))
        Spacer(Modifier.width(12.dp))
        RiderTextInfo(rider)
    }
}

@Composable
fun RiderImage(rider: KamenRider, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = rider.posterRes),
        contentDescription = "Poster ${rider.name}",
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .size(width = 240.dp, height = 320.dp)
            .clip(RoundedCornerShape(20.dp))
    )
}

@Composable
fun RiderTextInfo(rider: KamenRider) {
    val orientation = LocalConfiguration.current.orientation
    val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = rider.name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = if (isPortrait) TextAlign.Center else TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.year, rider.year),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.description),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = rider.description,
            textAlign = TextAlign.Justify
        )
    }
}



@Preview(
    showBackground = true,
    widthDp = 390,
    heightDp = 800,
    name = "Redmi Note 13 Portrait"
)
@Composable
fun DetailScreenPortraitPreview() {
    ScrollableComposeTheme {
        DetailScreen(null, getKamenRiderList()[0].id)
    }
}

//@Preview(
//    showBackground = true,
//    widthDp = 800,
//    heightDp = 390,
//    name = "Redmi Note 13 Landscape"
//)
//@Composable
//fun DetailScreenLandscapePreview() {
//    ScrollableComposeTheme {
//        val navController = rememberNavController()
//        DetailScreen(navController, getKamenRiderList()[0].id)
//    }
//}