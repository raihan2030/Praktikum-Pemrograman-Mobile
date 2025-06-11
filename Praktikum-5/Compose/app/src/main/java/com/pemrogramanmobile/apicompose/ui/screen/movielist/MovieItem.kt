package com.pemrogramanmobile.apicompose.ui.screen.movielist

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pemrogramanmobile.apicompose.R
import com.pemrogramanmobile.apicompose.domain.model.Movie

@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onDetailsClick: (movieId: Int) -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(if (movie.posterPath != null) "https://image.tmdb.org/t/p/w342${movie.posterPath}" else null)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_placeholder_image),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentDescription = "Poster ${movie.title}",
                modifier = Modifier
                    .size(width = 120.dp, height = 150.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.FillBounds,
            )
            Column(
                Modifier
                    .weight(1f)
                    .padding(start = 0.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.width(10.dp))
                    movie.releaseDate?.let {
                        if (it.isNotBlank()) {
                            Text(
                                text = it.split("-").firstOrNull() ?: it,
                                style = MaterialTheme.typography.labelMedium,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.overview),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Justify,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Rating ${String.format("%.1f", movie.voteAverage)} / 10",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                Row (
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            movie.homepage?.let { url ->
                                if (url.isNotBlank()) {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                                        setPackage("com.android.chrome")
                                    }
                                    try {
                                        context.startActivity(intent)
                                    } catch (e: ActivityNotFoundException) {
                                        val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                        try {
                                            context.startActivity(fallbackIntent)
                                        } catch (ex: ActivityNotFoundException) {
                                            Toast.makeText(context, "Tidak ada browser yang ditemukan!", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 15.dp),
                        enabled = !movie.homepage.isNullOrBlank()
                    ) {
                        Text(stringResource(R.string.website), fontSize = 13.sp)
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = { onDetailsClick(movie.id) },
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 15.dp),
                    ) {
                        Text(stringResource(R.string.details), fontSize = 13.sp)
                    }
                }
            }
        }
    }
}