package com.kaajjo.orgtechservice.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.kaajjo.orgtechservice.core.utils.GlobalExceptionHandler
import com.kaajjo.orgtechservice.ui.screen.NavGraphs
import com.kaajjo.orgtechservice.ui.screen.crash.CrashActivity
import com.kaajjo.orgtechservice.ui.screen.login.LoginViewModel
import com.kaajjo.orgtechservice.ui.theme.OrgtechserviceTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalExceptionHandler.initialize(applicationContext, CrashActivity::class.java)

        setContent {
            OrgtechserviceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Column {
                        val viewModel by viewModels<LoginViewModel>()
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

class MainViewModel : ViewModel() {

}