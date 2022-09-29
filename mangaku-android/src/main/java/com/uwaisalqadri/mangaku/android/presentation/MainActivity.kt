package com.uwaisalqadri.mangaku.android.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uwaisalqadri.mangaku.android.presentation.browse.BrowseScreen
import com.uwaisalqadri.mangaku.android.presentation.mymanga.MyMangaScreen
import com.uwaisalqadri.mangaku.android.presentation.theme.MangaTheme
import com.uwaisalqadri.mangaku.android.presentation.theme.composables.BottomBarDestination
import com.uwaisalqadri.mangaku.android.presentation.theme.composables.MangakuBottomBar

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MangaTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen(
    navigator: DestinationsNavigator
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            MangakuBottomBar(
                currentRoute = currentRoute,
                onSelect = {
                    navController.navigate(it) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        NavHost(navController, startDestination = BottomBarDestination.BROWSE.route) {
            composable(route = BottomBarDestination.BROWSE.route) {
                BrowseScreen(navigator)
            }
            composable(route = BottomBarDestination.MY_MANGA.route) {
                MyMangaScreen(navigator)
            }
        }
    }
}
