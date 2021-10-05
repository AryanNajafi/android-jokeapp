package io.github.jokeapp.data

import io.github.jokeapp.data.api.JokeService
import javax.inject.Inject

class JokeRepository @Inject constructor(private val jokeService: JokeService) {

    suspend fun getRandomJoke(): Joke? {
        return jokeService.fetchRandomJoke()
    }
}