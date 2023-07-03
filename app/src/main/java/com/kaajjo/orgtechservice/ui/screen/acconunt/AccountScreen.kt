package com.kaajjo.orgtechservice.ui.screen.acconunt

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.CollapsingTitle
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.CollapsingTopAppBar
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.rememberTopAppBarScrollBehavior
import com.kaajjo.orgtechservice.ui.screen.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 12.dp),

        ) {
            item {
                FilledTonalButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.logout() }
                ) {
                    Icon(imageVector = Icons.Rounded.Logout, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Выход")
                }
            }
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