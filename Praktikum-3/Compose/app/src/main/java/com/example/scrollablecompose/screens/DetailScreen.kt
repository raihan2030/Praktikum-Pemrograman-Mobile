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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.scrollablecompose.R
import com.example.scrollablecompose.data.getKamenRiderList
import com.example.scrollablecompose.model.KamenRider
import com.example.scrollablecompose.ui.theme.ScrollableComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, id: Int) {
    val rider = getKamenRiderList().find { it.id == id }
    val config = LocalConfiguration.current
    val orientationMode by remember { mutableIntStateOf(config.orientation) }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.detail))
                },
                navigationIcon = {
                    IconButton( onClick = { navController.navigateUp() } ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
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
            if(orientationMode == Configuration.ORIENTATION_PORTRAIT) {
                DetailPortraitLayout(rider, padding)
            }
            else{
                DetailLandscapeLayout(rider, padding)
            }
        }
        else{
            Text(
                text = "Data not found",
                fontSize = 25.sp
            )
        }
    }
}

@Composable
fun DetailPortraitLayout(rider : KamenRider, paddingValues: PaddingValues) {
    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier
            .padding(paddingValues)
            .padding(vertical = 15.dp, horizontal = 20.dp)
            .verticalScroll(scrollState)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(rider.posterRes),
            contentDescription = "Poster ${rider.name}",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(width = 300.dp, height = 400.dp)
                .clip(RoundedCornerShape(25.dp))
        )
        Spacer(Modifier.height(15.dp))
        Text(
            text = rider.name,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(20.dp))
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.year, rider.year),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(15.dp))

            Text(
                text = stringResource(R.string.description),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(3.dp))
            Text(
                text = rider.description,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
fun DetailLandscapeLayout(rider: KamenRider, paddingValues: PaddingValues) {
    val scrollState = rememberScrollState()

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(paddingValues)
            .padding(vertical = 15.dp, horizontal = 20.dp)
    ) {
        Image(
            painter = painterResource(id = rider.posterRes),
            contentDescription = "Poster ${rider.name}",
            modifier = Modifier
                .size(width = 240.dp, height = 300.dp)
                .padding(8.dp)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.FillBounds,
        )
        Spacer(Modifier.width(5.dp))
        Column(Modifier.padding(8.dp)) {

            Text(
                text = rider.name,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(Modifier.height(15.dp))
            Text(
                text = stringResource(R.string.year, rider.year),
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(15.dp))
            Text(
                text = stringResource(R.string.description),
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = rider.description,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Justify
            )
        }
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
        val navController = rememberNavController()
        DetailScreen(navController, getKamenRiderList()[0].id)
    }
}

@Preview(
    showBackground = true,
    widthDp = 800,
    heightDp = 390,
    name = "Redmi Note 13 Landscape"
)
@Composable
fun DetailScreenLandscapePreview() {
    ScrollableComposeTheme {
        val navController = rememberNavController()
        DetailScreen(navController, getKamenRiderList()[0].id)
    }
}