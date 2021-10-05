package io.github.jokeapp.data.api

import io.github.jokeapp.data.Joke
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokeService {
    companion object {
        const val baseUrl = "https://icanhazdadjoke.com"
    }

    @Headers("Accept: application/json")
    @GET("/")
    suspend fun fetchRandomJoke(): Joke?
}