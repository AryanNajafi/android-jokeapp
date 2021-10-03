package io.github.jokeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.jokeapp.ui.NavScreen
import io.github.jokeapp.ui.favorites.FavoritesScreen
import io.github.jokeapp.ui.joke.JokeScreen
import io.github.jokeapp.ui.theme.JokeApplicationTheme
import io.github.jokeapp.ui.theme.Teal200

class MainActivity : ComponentActivity() {

    private val navItems = listOf(
        NavScreen.Joke,
        NavScreen.Favorites,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokeApplicationTheme {
                val navController = rememberNavController()
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
                    }
                ) { innerPaddings ->
                    NavHost(
                        navController = navController,
                        startDestination = NavScreen.Joke.route,
                        modifier = Modifier.padding(innerPaddings)
                    ) {
                        composable(NavScreen.Joke.route) { JokeScreen() }
                        composable(NavScreen.Favorites.route) { FavoritesScreen() }
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