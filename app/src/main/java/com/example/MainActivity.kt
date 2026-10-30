package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FunoraTopBar
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.DailyChallengeScreen
import com.example.ui.screens.FactsScreen
import com.example.ui.screens.GamesHubScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MemoryMatchScreen
import com.example.ui.screens.NumberPuzzleScreen
import com.example.ui.screens.PersonalityPlayScreen
import com.example.ui.screens.QuizPlayScreen
import com.example.ui.screens.QuizzesHubScreen
import com.example.ui.screens.ReactionGameScreen
import com.example.ui.theme.FunoraDarkBorder
import com.example.ui.theme.FunoraDarkSurface
import com.example.ui.theme.FunoraLimeOnPrimary
import com.example.ui.theme.FunoraLimePrimary
import com.example.ui.theme.FunoraTextMedium
import com.example.ui.theme.FunoraTextMuted
import com.example.ui.theme.FunoraTheme
import com.example.ui.viewmodel.FunoraScreen
import com.example.ui.viewmodel.FunoraViewModel

enum class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val targetScreen: FunoraScreen,
    val testTag: String
) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home, FunoraScreen.Home, "nav_item_home"),
    GAMES("Games", Icons.Filled.SportsEsports, Icons.Outlined.SportsEsports, FunoraScreen.Games, "nav_item_games"),
    QUIZZES("Quizzes", Icons.Filled.Psychology, Icons.Outlined.Psychology, FunoraScreen.Quizzes, "nav_item_quizzes"),
    DAILY("Daily", Icons.Filled.Bolt, Icons.Outlined.Bolt, FunoraScreen.Daily, "nav_item_daily"),
    FACTS("Facts", Icons.Filled.Lightbulb, Icons.Outlined.Lightbulb, FunoraScreen.Facts, "nav_item_facts")
}

class MainActivity : ComponentActivity() {

    private val viewModel: FunoraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FunoraTheme(darkTheme = true) {
                FunoraApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun FunoraApp(viewModel: FunoraViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    val isRootScreen = currentScreen is FunoraScreen.Home ||
            currentScreen is FunoraScreen.Games ||
            currentScreen is FunoraScreen.Quizzes ||
            currentScreen is FunoraScreen.Daily ||
            currentScreen is FunoraScreen.Facts

    BackHandler(enabled = !isRootScreen) {
        viewModel.navigateBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FunoraTopBar(
                onBackClick = if (!isRootScreen) {
                    { viewModel.navigateBack() }
                } else null,
                onAboutClick = { viewModel.navigateTo(FunoraScreen.About) }
            )
        },
        bottomBar = {
            FunoraBottomNavigationBar(
                currentScreen = currentScreen,
                onNavigate = { screen -> viewModel.navigateTo(screen) }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is FunoraScreen.Home -> HomeScreen(viewModel = viewModel)
                is FunoraScreen.Games -> GamesHubScreen(viewModel = viewModel)
                is FunoraScreen.MemoryGame -> MemoryMatchScreen(viewModel = viewModel)
                is FunoraScreen.ReactionGame -> ReactionGameScreen(viewModel = viewModel)
                is FunoraScreen.NumberGame -> NumberPuzzleScreen(viewModel = viewModel)
                is FunoraScreen.Quizzes -> QuizzesHubScreen(viewModel = viewModel)
                is FunoraScreen.QuizPlay -> QuizPlayScreen(viewModel = viewModel)
                is FunoraScreen.PersonalityPlay -> PersonalityPlayScreen(viewModel = viewModel)
                is FunoraScreen.Daily -> DailyChallengeScreen(viewModel = viewModel)
                is FunoraScreen.Facts -> FactsScreen(viewModel = viewModel)
                is FunoraScreen.About -> AboutScreen()
            }
        }
    }
}

@Composable
fun FunoraBottomNavigationBar(
    currentScreen: FunoraScreen,
    onNavigate: (FunoraScreen) -> Unit
) {
    val currentNav = when (currentScreen) {
        FunoraScreen.Home -> NavItem.HOME
        FunoraScreen.Games,
        FunoraScreen.MemoryGame,
        FunoraScreen.ReactionGame,
        FunoraScreen.NumberGame -> NavItem.GAMES
        FunoraScreen.Quizzes,
        is FunoraScreen.QuizPlay,
        is FunoraScreen.PersonalityPlay -> NavItem.QUIZZES
        FunoraScreen.Daily -> NavItem.DAILY
        FunoraScreen.Facts -> NavItem.FACTS
        else -> null
    }

    NavigationBar(
        containerColor = FunoraDarkSurface,
        tonalElevation = 0.dp,
        windowInsets = WindowInsets.navigationBars,
        modifier = Modifier.testTag("funora_bottom_nav")
    ) {
        NavItem.values().forEach { item ->
            val isSelected = currentNav == item

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.targetScreen) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = FunoraLimeOnPrimary,
                    selectedTextColor = FunoraLimePrimary,
                    indicatorColor = FunoraLimePrimary,
                    unselectedIconColor = FunoraTextMuted,
                    unselectedTextColor = FunoraTextMuted
                ),
                modifier = Modifier.testTag(item.testTag)
            )
        }
    }
}
