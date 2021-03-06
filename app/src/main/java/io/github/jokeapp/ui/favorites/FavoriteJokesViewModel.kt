package io.github.jokeapp.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jokeapp.data.Joke
import io.github.jokeapp.data.JokeRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class FavoriteJokesViewModel @Inject constructor(
    private val repository: JokeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesViewState())
    val uiState: StateFlow<FavoritesViewState> get() = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = FavoritesViewState(loading = true)
            delay(5000)
            repository.loadSavedJokes().collect { jokes ->
                _uiState.value = FavoritesViewState(jokes)
            }
        }
    }

    fun removeJokeFromFavorites(joke: Joke) {
        viewModelScope.launch {
            repository.deleteJoke(joke)
        }
    }

}

data class FavoritesViewState(
    val jokes: List<Joke> = emptyList(),
    val loading: Boolean = false,
)