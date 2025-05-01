package com.example.scrollablecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scrollablecompose.ui.theme.ScrollableComposeTheme

data class KamenRider (
    val id: Int,
    val name: String,
    val description: String,
    val year: Int,
    val posterRes : Int
)

val kamenRiderList = listOf(
    KamenRider(
        id = 1,
        name = "Kamen Rider Build",
        description = "Seorang fisikawan jenius berubah menjadi Build untuk mengungkap kebenaran di balik Kotak Pandora.",
        year = 2018,
        posterRes = R.drawable.kr_build
    ),
    KamenRider(
        id = 2,
        name = "Kamen Rider Geats",
        description = "Para peserta bertarung dalam Desire Grand Prix untuk menjadi pahlawan ideal di dunia seperti permainan.",
        year = 2023,
        posterRes = R.drawable.kr_geats
    ),
    KamenRider(
        id = 3,
        name = "Kamen Rider Zero One",
        description = "Di dunia yang dipenuhi AI dan robot, Aruto bertarung sebagai Zero-One demi melindungi manusia dan Humagear.",
        year = 2020,
        posterRes = R.drawable.kr_zero_one
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScrollableComposeTheme {
                KamenRiderListApp()
            }
        }
    }
}

@Composable
fun KamenRiderListApp() {
    LazyColumn (
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(kamenRiderList) { rider ->
            RiderCard(rider)
        }
    }
}

@Composable
fun RiderCard(rider: KamenRider) {
    Card(
        modifier = Modifier
            .padding(7.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(3.dp)
        ) {
            Image(
                painter = painterResource(id = rider.posterRes),
                contentDescription = "Poster ${rider.name}",
                modifier = Modifier
                    .size(width = 120.dp, height = 150.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Fit,
            )
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = rider.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Tahun: ${rider.year}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Deskripsi: ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = rider.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(8.dp))

                Row (
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 15.dp),
                        onClick = {/*TODO*/}
                    ) {
                        Text("Fandom", fontSize = 13.sp)
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 15.dp),
                        onClick = {/*TODO*/}
                    ) {
                        Text("Detail", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "Redmi Note 13",
    widthDp = 360,
    heightDp = 800
)
@Composable
fun ScrollablePreview() {
    ScrollableComposeTheme {
        KamenRiderListApp()
    }
}

@Preview
@Composable
fun RiderCardView() {
    ScrollableComposeTheme {
        RiderCard(kamenRiderList[0])
    }
}