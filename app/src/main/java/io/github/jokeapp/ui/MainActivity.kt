package io.github.jokeapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.jokeapp.R
import io.github.jokeapp.data.Joke
import io.github.jokeapp.ui.favorites.FavoriteJokesViewModel
import io.github.jokeapp.ui.favorites.FavoritesScreen
import io.github.jokeapp.ui.joke.JokeScreen
import io.github.jokeapp.ui.joke.JokeViewModel
import io.github.jokeapp.ui.theme.JokeApplicationTheme
import io.github.jokeapp.ui.theme.LightGray
import io.github.jokeapp.ui.theme.Teal200
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val navItems = listOf(
        NavScreen.Joke,
        NavScreen.Favorites,
    )

    @ExperimentalAnimationApi
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokeApplicationTheme {
                val navController = rememberAnimatedNavController()
                Scaffold(
                    topBar = {
                        val appTitle = stringResource(id = R.string.app_name)
                        TopAppBar(
                            title = { Text(text = appTitle) },
                            backgroundColor = Teal200,
                        )
                    },
                    bottomBar = {
                        BottomNavBar(navController = navController, items = navItems)
                    },
                    backgroundColor = LightGray
                ) { innerPaddings ->
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = NavScreen.Joke.route,
                        modifier = Modifier.padding(innerPaddings)
                    ) {
                        composable(
                            route = NavScreen.Joke.route,
                            enterTransition = { initial, _ ->
                                when (initial.destination.route) {
                                    NavScreen.Favorites.route -> slideIntoContainer(
                                        towards = AnimatedContentScope.SlideDirection.Left,
                                        animationSpec = tween(500)
                                    )
                                    else -> null
                                }
                            },
                            exitTransition = { _, target ->
                                when (target.destination.route) {
                                    NavScreen.Favorites.route -> slideOutOfContainer(
                                        towards = AnimatedContentScope.SlideDirection.Left,
                                        animationSpec = tween(500)
                                    )
                                    else -> null
                                }
                            },
                            popEnterTransition = { initial, _ ->
                                when (initial.destination.route) {
                                    NavScreen.Favorites.route -> slideIntoContainer(
                                        towards = AnimatedContentScope.SlideDirection.Right,
                                        animationSpec = tween(500)
                                    )
                                    else -> null
                                }
                            },
                            popExitTransition = { _, target ->
                                when (target.destination.route) {
                                    NavScreen.Favorites.route -> slideOutOfContainer(
                                        towards = AnimatedContentScope.SlideDirection.Right,
                                        animationSpec = tween(500)
                                    )
                                    else -> null
                                }
                            },
                        ) {
                            val jokeViewModel = hiltViewModel<JokeViewModel>()
                            JokeScreen(jokeViewModel)
                        }
                        composable(
                            NavScreen.Favorites.route,
                            enterTransition = { initial, _ ->
                                when (initial.destination.route) {
                                    NavScreen.Joke.route -> slideIntoContainer(
                                        towards = AnimatedContentScope.SlideDirection.Left,
                                        animationSpec = tween(500)
                                    )
                                    else -> null
                                }
                            },
                            exitTransition = { _, target ->
                                when (target.destination.route) {
                                    NavScreen.Joke.route -> slideOutOfContainer(
                                        towards = AnimatedContentScope.SlideDirection.Left,
                                        animationSpec = tween(500)
                                    )
                                    else -> null
                                }
                            },
                            popEnterTransition = { initial, _ ->
                                when (initial.destination.route) {
                                    NavScreen.Joke.route -> slideIntoContainer(
                                        towards = AnimatedContentScope.SlideDirection.Right,
                                        animationSpec = tween(500)
                                    )
                                    else -> null
                                }
                            },
                            popExitTransition = { _, target ->
                                when (target.destination.route) {
                                    NavScreen.Joke.route -> slideOutOfContainer(
                                        towards = AnimatedContentScope.SlideDirection.Right,
                                        animationSpec = tween(500)
                                    )
                                    else -> null
                                }
                            },
                        ) {
                            val favoriteJokesViewModel = hiltViewModel<FavoriteJokesViewModel>()
                            FavoritesScreen(favoriteJokesViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController, items: List<NavScreen>) {
    BottomNavigation(
        backgroundColor = Teal200
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                label = {
                    Text(text = stringResource(id = screen.titleResId))
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.iconResId),
                        contentDescription = stringResource(id = screen.titleResId)
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JokeApplicationTheme {
    }
}

fun shareJoke(context: Context, joke: Joke) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, joke.joke);
    context.startActivity(intent)
}