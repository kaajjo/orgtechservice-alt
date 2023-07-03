package com.kaajjo.orgtechservice.ui.screen.acconunt

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaajjo.orgtechservice.data.remote.dto.Session
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.CollapsingTitle
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.CollapsingTopAppBar
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.rememberTopAppBarScrollBehavior
import com.kaajjo.orgtechservice.ui.screen.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import korlibs.time.DateTime

@Destination
@Composable
fun AccountScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val scrollBehavior = rememberTopAppBarScrollBehavior()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CollapsingTopAppBar(
                collapsingTitle = CollapsingTitle.medium(titleText = "Аккаунт"),
                navigationIcon = {
                    IconButton(onClick = { destinationsNavigator.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->

        val activeSessions by viewModel.activeSessions.collectAsState()
        var confirmLogoutDialog by rememberSaveable {
            mutableStateOf(false)
        }

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                var isExpanded by rememberSaveable {
                    mutableStateOf(false)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
                        .clickable(onClick = { isExpanded = !isExpanded })
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Привязанные устройства",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        val iconRotation by animateFloatAsState(if (!isExpanded) 0f else 180f)
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.rotate(iconRotation)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    AnimatedVisibility(visible = isExpanded) {
                        Column {
                            activeSessions.forEach {
                                ActiveSessionItem(
                                    session = it,
                                    currentKey = viewModel.userApiKey,
                                    onLogoutClick = { viewModel.logout(it.value) }
                                )
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
            item {
                FilledTonalButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { confirmLogoutDialog = true }
                ) {
                    Icon(imageVector = Icons.Rounded.Logout, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Выход")
                }
            }
        }
        if (confirmLogoutDialog) {
            AlertDialog(
                title = {
                    Text("Выход")
                },
                onDismissRequest = { confirmLogoutDialog = false },
                dismissButton = {
                    TextButton(onClick = { confirmLogoutDialog = false }) {
                        Text("Нет")
                    }
                },
                confirmButton = {
                    TextButton(onClick = { viewModel.logout(viewModel.userApiKey) }) {
                        Text("Выйти")
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Logout,
                        contentDescription = null
                    )
                },
                text = {
                    Text("Вы точно хотите выйти из своего аккаунта?")
                }
            )
        }

    }

    LaunchedEffect(viewModel.isLoggedOut) {
        if (viewModel.isLoggedOut) {
            destinationsNavigator.popBackStack()
            destinationsNavigator.navigate(LoginScreenDestination)
            viewModel.isLoggedOut = false
        }
    }
}

@Composable
fun ActiveSessionItem(
    session: Session,
    currentKey: String,
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = session.device,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Дата входа: ${DateTime(session.created * 1000.0).format("dd.MM.yyyy")}",
                style = MaterialTheme.typography.bodyMedium,
                color = LocalContentColor.current.copy(alpha = 0.7f)
            )
        }
        if (session.value == currentKey) {
            Text(
                text = "Это устройство",
                style = MaterialTheme.typography.bodySmall
            )
        } else {
            IconButton(onClick = onLogoutClick) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null
                )
            }
        }
    }
}