package io.github.jokeapp.data

import io.github.jokeapp.data.api.JokeService
import io.github.jokeapp.data.db.JokeDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JokeRepository @Inject constructor(
    private val jokeService: JokeService,
    private val jokeDao: JokeDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getRandomJoke(): Joke? {
        return jokeService.fetchRandomJoke()
    }

    suspend fun saveJoke(joke: Joke) {
        withContext(ioDispatcher) {
            jokeDao.insertJoke(joke)
        }
    }

    fun loadSavedJokes(): Flow<List<Joke>> {
        return jokeDao.loadAllSavedJokes()
    }
}