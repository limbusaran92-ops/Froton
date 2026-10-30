package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FunoraButton
import com.example.ui.components.FunoraGhostButton
import com.example.ui.components.FunoraTagPill
import com.example.ui.theme.FunoraDarkBorder
import com.example.ui.theme.FunoraDarkSurface
import com.example.ui.theme.FunoraDarkSurfaceHigh
import com.example.ui.theme.FunoraLimeOnPrimary
import com.example.ui.theme.FunoraLimePrimary
import com.example.ui.theme.FunoraLimeSecondary
import com.example.ui.theme.FunoraNeonAmber
import com.example.ui.theme.FunoraNeonCyan
import com.example.ui.theme.FunoraTextHigh
import com.example.ui.theme.FunoraTextMedium
import com.example.ui.theme.FunoraTextMuted
import com.example.ui.viewmodel.FunoraScreen
import com.example.ui.viewmodel.FunoraViewModel

@Composable
fun HomeScreen(
    viewModel: FunoraViewModel,
    modifier: Modifier = Modifier
) {
    val stats by viewModel.stats.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("home_screen")
    ) {
        // Hero Section
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = FunoraDarkSurface,
            border = BorderStroke(1.dp, FunoraDarkBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                FunoraDarkSurfaceHigh.copy(alpha = 0.5f),
                                FunoraDarkSurface
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FunoraTagPill(text = "DAILY DOSE OF FUN")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(FunoraLimePrimary)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "LIVE",
                            color = FunoraLimePrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Bored?",
                    color = FunoraTextHigh,
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = "Let's fix that.",
                    color = FunoraLimePrimary,
                    style = MaterialTheme.typography.displayMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Play quick mini-games, challenge your brain and discover something surprising. Your next favorite break starts here.",
                    color = FunoraTextMedium,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FunoraButton(
                        text = "Explore games",
                        onClick = { viewModel.navigateTo(FunoraScreen.Games) },
                        modifier = Modifier.weight(1f),
                        tag = "hero_explore_games_btn"
                    )
                    FunoraGhostButton(
                        text = "Take a quiz",
                        onClick = { viewModel.navigateTo(FunoraScreen.Quizzes) },
                        modifier = Modifier.weight(1f),
                        tag = "hero_take_quiz_btn"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mood Indicator
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = FunoraDarkSurfaceHigh,
                    border = BorderStroke(1.dp, FunoraDarkBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "✳", color = FunoraLimePrimary, fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "TODAY'S MOOD",
                                    color = FunoraTextMuted,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Curiosity mode",
                                    color = FunoraTextHigh,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = FunoraLimePrimary
                        ) {
                            Text(
                                text = "ON",
                                color = FunoraLimeOnPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Stats Strip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatPill(
                title = if (stats.bestReactionMs > 0) "${stats.bestReactionMs}ms" else "Ready",
                subtitle = "Best reaction",
                icon = Icons.Default.Timer,
                modifier = Modifier.weight(1f)
            )
            StatPill(
                title = "${stats.memoryWins}",
                subtitle = "Memory wins",
                icon = Icons.Default.EmojiEvents,
                modifier = Modifier.weight(1f)
            )
            StatPill(
                title = "${stats.quizzesCompletedCount}",
                subtitle = "Quizzes done",
                icon = Icons.Default.Psychology,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Daily Challenge Banner
        DailyBannerCard(
            questionText = viewModel.todayQuestion.question,
            isCompleted = stats.dailyCompletedDate == viewModel.todayDateString,
            onClick = { viewModel.navigateTo(FunoraScreen.Daily) }
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Section: Pick Your Playground
        Text(
            text = "PICK YOUR PLAYGROUND",
            color = FunoraLimePrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Find your kind of fun",
            color = FunoraTextHigh,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "A little challenge, a little discovery, a lot of good breaks.",
            color = FunoraTextMedium,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Playground 2x2 Grid Cards
        PlaygroundCard(
            tag = "PLAY",
            title = "Mini Games",
            description = "Quick games testing memory, reaction speed and number logic.",
            icon = Icons.Default.SportsEsports,
            accentColor = FunoraLimePrimary,
            onClick = { viewModel.navigateTo(FunoraScreen.Games) },
            testTag = "playground_games_card"
        )

        Spacer(modifier = Modifier.height(12.dp))

        PlaygroundCard(
            tag = "THINK",
            title = "Trivia Quizzes",
            description = "Explore fun space, nature and logic topics to see what you know.",
            icon = Icons.Default.Psychology,
            accentColor = FunoraNeonCyan,
            onClick = { viewModel.navigateTo(FunoraScreen.Quizzes) },
            testTag = "playground_quizzes_card"
        )

        Spacer(modifier = Modifier.height(12.dp))

        PlaygroundCard(
            tag = "DISCOVER",
            title = "Personality Tests",
            description = "Lighthearted questions revealing your weekend and creative archetype.",
            icon = Icons.Default.Whatshot,
            accentColor = FunoraLimeSecondary,
            onClick = { viewModel.navigateTo(FunoraScreen.Quizzes) },
            testTag = "playground_personality_card"
        )

        Spacer(modifier = Modifier.height(12.dp))

        PlaygroundCard(
            tag = "EXPLORE",
            title = "Amazing Facts",
            description = "Bite-sized, mind-blowing facts for relentlessly curious minds.",
            icon = Icons.Default.Lightbulb,
            accentColor = FunoraNeonAmber,
            onClick = { viewModel.navigateTo(FunoraScreen.Facts) },
            testTag = "playground_facts_card"
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Section: Start in Seconds (Quick games jump)
        Text(
            text = "START IN SECONDS",
            color = FunoraLimePrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Popular ways to play",
            color = FunoraTextHigh,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(14.dp))

        QuickGameRow(
            symbol = "▦",
            title = "Memory Match",
            subtitle = "Train your recall & pair symbols",
            onClick = { viewModel.navigateTo(FunoraScreen.MemoryGame) },
            tag = "quick_memory_game"
        )
        Spacer(modifier = Modifier.height(10.dp))
        QuickGameRow(
            symbol = "ϟ",
            title = "Reaction Test",
            subtitle = "Test your lightning-fast reflexes",
            onClick = { viewModel.navigateTo(FunoraScreen.ReactionGame) },
            tag = "quick_reaction_game"
        )
        Spacer(modifier = Modifier.height(10.dp))
        QuickGameRow(
            symbol = "#",
            title = "Number Puzzle",
            subtitle = "Spot the missing pattern sequence",
            onClick = { viewModel.navigateTo(FunoraScreen.NumberGame) },
            tag = "quick_number_game"
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun StatPill(
    title: String,
    subtitle: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = FunoraDarkSurface,
        border = BorderStroke(1.dp, FunoraDarkBorder),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = FunoraLimePrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                color = FunoraTextHigh,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Text(
                text = subtitle,
                color = FunoraTextMuted,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun DailyBannerCard(
    questionText: String,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = FunoraDarkSurface,
        border = BorderStroke(1.dp, FunoraLimePrimary.copy(alpha = 0.5f)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("daily_banner_card")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            FunoraLimePrimary.copy(alpha = 0.08f),
                            Color.Transparent
                        )
                    )
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(FunoraLimePrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = FunoraLimeOnPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                FunoraTagPill(
                    text = if (isCompleted) "COMPLETED TODAY" else "DAILY CHALLENGE",
                    color = if (isCompleted) FunoraNeonCyan else FunoraLimePrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Can you crack today's question?",
                    color = FunoraTextHigh,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    text = questionText,
                    color = FunoraTextMedium,
                    fontSize = 12.sp,
                    maxLines = 1
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = FunoraLimePrimary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun PlaygroundCard(
    tag: String,
    title: String,
    description: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = FunoraDarkSurface),
        border = BorderStroke(1.dp, FunoraDarkBorder),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
                FunoraTagPill(text = tag, color = accentColor)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = title,
                color = FunoraTextHigh,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                color = FunoraTextMedium,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Explore",
                    color = accentColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Composable
fun QuickGameRow(
    symbol: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    tag: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = FunoraDarkSurface,
        border = BorderStroke(1.dp, FunoraDarkBorder),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(tag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(FunoraDarkSurfaceHigh),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = symbol,
                    color = FunoraLimePrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = FunoraTextHigh,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
                Text(
                    text = subtitle,
                    color = FunoraTextMedium,
                    fontSize = 12.sp
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = FunoraLimePrimary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
