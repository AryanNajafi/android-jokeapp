package io.github.jokeapp.ui.favorites

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.jokeapp.R
import io.github.jokeapp.data.Joke
import io.github.jokeapp.ui.shareJoke
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun FavoritesScreen(
    viewModel: FavoriteJokesViewModel,
) {
    val viewState: FavoritesViewState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(viewState.jokes) { joke ->
            FavoriteJokeCard(
                joke = joke,
                onJokeDelete = {
                    viewModel.removeJokeFromFavorites(it)
                    Toast.makeText(context, R.string.toast_joke_removed, Toast.LENGTH_SHORT).show()
                },
                onJokeShare = {
                    shareJoke(context, it)
                }
            )
        }
    }
}

@Composable
fun FavoriteJokeCard(joke: Joke, onJokeDelete: (Joke) -> Unit, onJokeShare: (Joke) -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 5.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column {
            Text(
                text = joke.joke,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(10.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))
            Divider(color = Color.LightGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(5.dp))

            Row {
                Button(
                    onClick = { onJokeDelete(joke) },
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.button_remove_favorite_title))
                }
                Button(
                    onClick = { onJokeShare(joke) },
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.button_share_title))
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}