package com.kaajjo.orgtechservice.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.kaajjo.orgtechservice.ui.screen.NavGraphs
import com.kaajjo.orgtechservice.ui.theme.OrgtechserviceTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

val LocalUserApiKey = compositionLocalOf<String> { "" }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (!BuildConfig.DEBUG) {
//            GlobalExceptionHandler.initialize(
//                applicationContext = applicationContext,
//                activityToBeLaunched = CrashActivity::class.java
//            )
//        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            OrgtechserviceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Column {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = navController,
                            startRoute = NavGraphs.root.startRoute
                        )
                    }
                }
            }
        }
    }
}