package io.github.jokeapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.jokeapp.data.Joke

@Database(entities = [Joke::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}