package io.github.jokeapp.ui.joke

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jokeapp.data.Joke
import io.github.jokeapp.data.JokeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val repository: JokeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JokeViewState())
    val uiState: StateFlow<JokeViewState> get() = _uiState

    init {
        loadNewRandomJoke()
    }

    private fun loadNewRandomJoke() {
        viewModelScope.launch {
            _uiState.value = JokeViewState(loading = true)

            // Add a fake delay to see the loading progress indicator
            delay(1000)

            try {
                val joke = repository.getRandomJoke()
                _uiState.value = JokeViewState(joke = joke)
            } catch (e: Exception) {
                _uiState.value = JokeViewState(error = e)
            }
        }
    }

    fun getNewJoke() {
        loadNewRandomJoke()
    }

    fun saveJokeAsFavorite() {
        _uiState.value.joke?.let {
            viewModelScope.launch {
                repository.saveJoke(it)
            }
        }
    }
}

data class JokeViewState(
    val joke: Joke? = null,
    val loading: Boolean = false,
    val error: Exception? = null
)