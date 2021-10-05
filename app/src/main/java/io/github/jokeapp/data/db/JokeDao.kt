package io.github.jokeapp.data.db

import androidx.room.*
import io.github.jokeapp.data.Joke
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: Joke)

    @Query("SELECT * from joke")
    fun loadAllSavedJokes(): Flow<List<Joke>>

    @Delete
    suspend fun deleteJoke(joke: Joke)
}