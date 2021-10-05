package io.github.jokeapp.ui.joke

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.jokeapp.R
import io.github.jokeapp.data.Joke
import io.github.jokeapp.ui.shareJoke

@Composable
fun JokeScreen(
    viewModel: JokeViewModel,
) {
    val viewState: JokeViewState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {
        if (viewState.loading.not()) {
            viewState.joke?.let {
                JokeCard(
                    joke = it,
                    onJokeLikeClick = {
                        viewModel.saveJokeAsFavorite()
                        Toast.makeText(context, R.string.toast_joke_saved, Toast.LENGTH_LONG).show()
                    },
                    onShareClick = {
                        shareJoke(context = context, it)
                    }
                )
            }
            viewState.error?.let {
                Text(
                    text = stringResource(id = R.string.error_fetching_title),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            Box(modifier = Modifier.align(Alignment.Center)) {
                CircularProgressIndicator()
            }
        }

        Button(
            onClick = { viewModel.getNewJoke() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(15.dp)
        ) {
            Text(text = stringResource(id = R.string.button_refresh_title))
        }
    }

}

@Composable
fun JokeCard(joke: Joke, onJokeLikeClick: () -> Unit, onShareClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column {
            Text(
                text = joke.joke,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(10.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))
            Divider(color = Color.LightGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(5.dp))

            Row {
                Button(
                    onClick = onJokeLikeClick,
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.button_favorite_title))
                }

                Button(
                    onClick = onShareClick,
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
