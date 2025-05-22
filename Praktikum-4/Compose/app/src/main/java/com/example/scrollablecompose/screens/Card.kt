package com.example.scrollablecompose.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scrollablecompose.R
import com.example.scrollablecompose.model.KamenRider
import timber.log.Timber

@Composable
fun RiderCardPortrait(
    rider: KamenRider,
    context: Context,
    onClick: () -> Unit
){
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
            modifier = Modifier.padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = rider.posterRes),
                contentDescription = "Poster ${rider.name}",
                modifier = Modifier
                    .size(width = 120.dp, height = 150.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.FillBounds,
            )
            Column(Modifier.padding(8.dp)) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = rider.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(1f)
                    )
                    Spacer(Modifier.width(20.dp))
                    Text(
                        text = rider.year.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = rider.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Justify
                )
                Spacer(Modifier.height(8.dp))

                Row (
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 15.dp),
                        onClick = {
                            Timber.tag("ListScreen").i("Tombol Explicit Intent ditekan untuk: ${rider.name}, url: ${rider.imdbUrl}")
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(rider.imdbUrl)
                            intent.setPackage("com.android.chrome")
                            context.startActivity(intent)
                        }
                    ) {
                        Text(stringResource(R.string.imdb), fontSize = 13.sp)
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 15.dp),
                        onClick = onClick
                    ) {
                        Text(stringResource(R.string.detail), fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun RiderCardLandscape(
    rider: KamenRider,
    context: Context,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(7.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = rider.posterRes),
                contentDescription = "Poster ${rider.name}",
                modifier = Modifier
                    .size(width = 192.dp, height = 240.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.FillBounds,
            )
            Spacer(Modifier.width(5.dp))
            Column(Modifier.padding(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = rider.name,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(1f)
                    )
                    Spacer(Modifier.width(20.dp))
                    Text(
                        text = rider.year.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                Spacer(Modifier.height(15.dp))
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = rider.description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Justify
                )

                Spacer(Modifier.height(50.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(vertical = 15.dp, horizontal = 20.dp),
                        onClick = {
                            Timber.tag("ListScreen").i("Tombol Explicit Intent ditekan untuk: ${rider.name}, url: ${rider.imdbUrl}")
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(rider.imdbUrl)
                            intent.setPackage("com.android.chrome")
                            context.startActivity(intent)
                        }
                    ) {
                        Text(stringResource(R.string.imdb), fontSize = 18.sp)
                    }

                    Spacer(Modifier.width(10.dp))

                    Button(
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(vertical = 15.dp, horizontal = 20.dp),
                        onClick = onClick
                    ) {
                        Text(stringResource(R.string.detail), fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
