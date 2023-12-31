package com.example.masterand

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.masterand.ui.theme.MasterAndTheme
import com.example.masterand.views.MainGame
import com.example.masterand.views.ProfileScreenInitial
import com.example.masterand.views.ResultScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MasterAndTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "ProfileScreen") {
                        composable(
                            route = "ProfileScreen",
                            enterTransition = {
                                slideInHorizontally(
                                    animationSpec = tween(1000, easing = EaseIn),
                                    initialOffsetX = { it }
                                )
                            },
                            exitTransition = {
                                slideOutHorizontally(
                                    animationSpec = tween(1000, easing = EaseOut),
                                    targetOffsetX = { it }
                                )
                            }
                        ) {
                            ProfileScreenInitial(
                                onStartGame = {
                                    navController.navigate("MainGame")
                                }
                            )
                        }
                        composable(
                            route = "MainGame",
                            enterTransition = {
                                slideInHorizontally(
                                    animationSpec = tween(1000, easing = EaseIn),
                                    initialOffsetX = { it }
                                )
                            },
                            exitTransition = {
                                slideOutHorizontally(
                                    animationSpec = tween(1000, easing = EaseOut),
                                    targetOffsetX = { it }
                                )
                            }
                        ) {
                            MainGame(
                                onScoreScreen = { score ->
                                    navController.navigate("ResultScreen/$score")
                                },
                                onLogout = {
                                    navController.navigate("ProfileScreen")
                                })

                        }
                        composable(
                            route = "ResultScreen/{score}",
                            enterTransition = {
                                slideInHorizontally(
                                    animationSpec = tween(1000, easing = EaseIn),
                                    initialOffsetX = { it }
                                )
                            },
                            exitTransition = {
                                slideOutHorizontally(
                                    animationSpec = tween(1000, easing = EaseOut),
                                    targetOffsetX = { it }
                                )
                            }
                        ) {
                            ResultScreen(
                                score = it.arguments?.getString("score") ?: "0",
                                onRestartGame = {
                                    navController.navigate("MainGame")
                                },
                                onLogout = {
                                    navController.navigate("ProfileScreen")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MasterAndPreview() {
    MasterAndTheme {}
}