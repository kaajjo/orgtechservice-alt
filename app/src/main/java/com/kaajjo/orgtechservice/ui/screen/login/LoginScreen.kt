package com.kaajjo.orgtechservice.ui.screen.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaajjo.orgtechservice.R
import com.kaajjo.orgtechservice.ui.screen.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@Destination
@RootNavGraph(start = true)
@Composable
fun LoginScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var showPassword by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.userApiKey) {
        viewModel.checkAuth(viewModel.userApiKey)
    }
    LaunchedEffect(viewModel.isAuthenticated) {
        if (viewModel.isAuthenticated) {
            destinationsNavigator.popBackStack()
            destinationsNavigator.navigate(HomeScreenDestination)
        }
    }

    Column {
        Text("key: ${viewModel.key?.key?.value ?: "null"}")
        Text("saved_key: ${viewModel.userApiKey}")
        Text("created: ${viewModel.key?.key?.created ?: "null"}")
        Text("expries: ${viewModel.key?.key?.expires ?: "null"}")
        Text("device: ${viewModel.key?.key?.device ?: "null"}")
        Text("device_id: ${viewModel.key?.key?.device_id ?: "null"}")
        Text("status: ${viewModel.key?.status}")
        Text("auth_error: ${viewModel.authError}")
        Text("is_authenticated: ${viewModel.isAuthenticated}")
        Text("is_auth_checked: ${viewModel.isAuthChecked}")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            OutlinedTextField(
                value = viewModel.login,
                onValueChange = { viewModel.login = it },
                label = {
                    Text(
                        stringResource(
                            R.string.login_label
                        )
                    )
                },
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                label = {
                    Text(
                        stringResource(
                            R.string.password_label
                        )
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { showPassword = !showPassword }
                    ) {
                        Icon(
                            imageVector = if (showPassword) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = { },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.forgot_password))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier.defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth
                ),
                enabled = !viewModel.login.isNullOrBlank() && !viewModel.password.isNullOrBlank(),
                onClick = { viewModel.auth() }
            ) {
                Text(stringResource(R.string.login))
            }
        }
    }
}