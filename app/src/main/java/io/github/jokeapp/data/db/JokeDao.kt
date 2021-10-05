package io.github.jokeapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.jokeapp.data.Joke
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: Joke)

    @Query("SELECT * from joke")
    fun loadAllSavedJokes(): Flow<List<Joke>>
}