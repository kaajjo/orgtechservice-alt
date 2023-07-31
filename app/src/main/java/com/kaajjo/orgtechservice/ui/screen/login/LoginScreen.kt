package com.kaajjo.orgtechservice.ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SettingsSuggest
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaajjo.orgtechservice.R
import com.kaajjo.orgtechservice.ui.component.autofill
import com.kaajjo.orgtechservice.ui.screen.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalComposeUiApi::class)
@Destination
@RootNavGraph(start = true)
@Composable
fun LoginScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var showPassword by remember { mutableStateOf(false) }
    var additionalLoginParametersDialog by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(viewModel.userApiKey) {
        viewModel.checkAuth(viewModel.userApiKey)
    }
    LaunchedEffect(viewModel.isAuthenticated) {
        if (viewModel.isAuthenticated) {
            destinationsNavigator.popBackStack()
            destinationsNavigator.navigate(HomeScreenDestination)
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
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
                    maxLines = 1,
                    modifier = Modifier.autofill(
                        autofillTypes = listOf(AutofillType.Username),
                        onFill = { viewModel.login = it }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
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
                    maxLines = 1,
                    modifier = Modifier.autofill(
                        autofillTypes = listOf(AutofillType.Password),
                        onFill = { viewModel.password = it }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.defaultMinSize(TextFieldDefaults.MinWidth)
                ) {
                    IconButton(onClick = { additionalLoginParametersDialog = true }) {
                        Icon(
                            imageVector = Icons.Outlined.SettingsSuggest,
                            contentDescription = null
                        )
                    }
                    TextButton(
                        onClick = { }
                    ) {
                        Text(stringResource(R.string.forgot_password))
                    }
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

    if (additionalLoginParametersDialog) {
        var deviceNameTextError by rememberSaveable {
            mutableStateOf(false)
        }
        AlertDialog(
            title = {
                Text(stringResource(R.string.login_advanced))
            },
            onDismissRequest = { additionalLoginParametersDialog = false },
            dismissButton = {
                TextButton(onClick = { additionalLoginParametersDialog = false }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        additionalLoginParametersDialog = false
                    },
                    enabled = !deviceNameTextError
                ) {
                    Text(stringResource(R.string.action_apply))
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.SettingsSuggest,
                    contentDescription = null
                )
            },
            text = {
                OutlinedTextField(
                    value = viewModel.deviceName,
                    onValueChange = {
                        viewModel.deviceName = it
                        deviceNameTextError = it.isNullOrBlank()
                    },
                    label = { Text(stringResource(R.string.login_advanced_device_name)) },
                    isError = deviceNameTextError
                )
            }
        )
    } else {
        if (viewModel.isAuthenticating) {
            AlertDialog(
                title = { Text("Выполняем вход...") },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Login,
                        contentDescription = null
                    )
                },
                onDismissRequest = { },
                dismissButton = { },
                confirmButton = {

                },
                text = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            )
        }
    }
}