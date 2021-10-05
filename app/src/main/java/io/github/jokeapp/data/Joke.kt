package io.github.jokeapp.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Joke(
    @PrimaryKey val id: String,
    val joke: String,
    @Ignore val status: Int
)