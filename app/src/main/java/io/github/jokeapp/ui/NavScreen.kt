package io.github.jokeapp.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.github.jokeapp.R

sealed class NavScreen(val route: String,
                       @StringRes val titleResId: Int,
                       @DrawableRes val iconResId: Int) {
    object Joke : NavScreen("joke", R.string.nav_title_joke, R.drawable.ic_joke)
    object Favorites : NavScreen("favorites", R.string.nav_title_favorites, R.drawable.ic_favorite)
}