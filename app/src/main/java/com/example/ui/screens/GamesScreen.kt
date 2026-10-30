package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FunoraData
import com.example.data.ReactionState
import com.example.ui.components.FunoraButton
import com.example.ui.components.FunoraGhostButton
import com.example.ui.components.FunoraPageIntro
import com.example.ui.components.FunoraTagPill
import com.example.ui.theme.FunoraDarkBorder
import com.example.ui.theme.FunoraDarkSurface
import com.example.ui.theme.FunoraDarkSurfaceHigh
import com.example.ui.theme.FunoraError
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
fun GamesHubScreen(
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
            .testTag("games_hub_screen")
    ) {
        FunoraPageIntro(
            eyebrow = "THE GAME ROOM",
            title = "Play a little.",
            accent = "Think a lot.",
            description = "Three quick arcade brain games. No downloads, no accounts, just tap and play."
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Game 1: Memory Match
        GameEntryCard(
            symbol = "▦",
            tag = "MEMORY",
            title = "Memory Match",
            description = "Match pairs of hidden symbols before you run out of moves.",
            statLabel = if (stats.bestMemoryMoves > 0) "Best: ${stats.bestMemoryMoves} moves" else "Unplayed",
            accentColor = FunoraLimePrimary,
            onClick = { viewModel.navigateTo(FunoraScreen.MemoryGame) },
            testTag = "card_memory_game"
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Game 2: Reaction Test
        GameEntryCard(
            symbol = "ϟ",
            tag = "SPEED",
            title = "Reaction Test",
            description = "Wait for the green signal, then tap as quickly as humanly possible.",
            statLabel = if (stats.bestReactionMs > 0) "Record: ${stats.bestReactionMs} ms" else "Unplayed",
            accentColor = FunoraNeonCyan,
            onClick = { viewModel.navigateTo(FunoraScreen.ReactionGame) },
            testTag = "card_reaction_game"
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Game 3: Number Puzzle
        GameEntryCard(
            symbol = "#",
            tag = "LOGIC",
            title = "Number Puzzle",
            description = "Find the missing number in clever math and pattern sequences.",
            statLabel = if (stats.bestNumberPuzzleScore > 0) "Best: ${stats.bestNumberPuzzleScore}/${FunoraData.numberPuzzles.size}" else "Unplayed",
            accentColor = FunoraNeonAmber,
            onClick = { viewModel.navigateTo(FunoraScreen.NumberGame) },
            testTag = "card_number_game"
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun GameEntryCard(
    symbol: String,
    tag: String,
    title: String,
    description: String,
    statLabel: String,
    accentColor: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = FunoraDarkSurface,
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(accentColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = symbol,
                            color = accentColor,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        FunoraTagPill(text = tag, color = accentColor)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = title,
                            color = FunoraTextHigh,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = FunoraDarkSurfaceHigh
                ) {
                    Text(
                        text = statLabel,
                        color = FunoraTextMedium,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description,
                color = FunoraTextMedium,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Play game",
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

// ==========================================
// 1. MEMORY MATCH GAME SCREEN
// ==========================================
@Composable
fun MemoryMatchScreen(
    viewModel: FunoraViewModel,
    modifier: Modifier = Modifier
) {
    val cards by viewModel.memoryCards.collectAsState()
    val moves by viewModel.memoryMoves.collectAsState()
    val isWon by viewModel.memoryWon.collectAsState()
    val stats by viewModel.stats.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("memory_match_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                FunoraTagPill(text = "MEMORY MATCH", color = FunoraLimePrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Find all 6 pairs",
                    color = FunoraTextHigh,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = FunoraDarkSurfaceHigh,
                border = BorderStroke(1.dp, FunoraDarkBorder)
            ) {
                Text(
                    text = "Moves: $moves",
                    color = FunoraLimePrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 4 columns x 3 rows grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            itemsIndexed(cards) { index, card ->
                val isRevealed = card.isFaceUp || card.isMatched
                val cardBg = when {
                    card.isMatched -> FunoraLimePrimary.copy(alpha = 0.2f)
                    card.isFaceUp -> FunoraDarkSurfaceHigh
                    else -> FunoraDarkSurface
                }
                val borderColor = when {
                    card.isMatched -> FunoraLimePrimary
                    card.isFaceUp -> FunoraLimeSecondary
                    else -> FunoraDarkBorder
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = cardBg,
                    border = BorderStroke(1.5.dp, borderColor),
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.onMemoryCardClicked(index) }
                        .testTag("memory_card_$index")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = if (isRevealed) card.symbol else "?",
                            fontSize = if (isRevealed) 32.sp else 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = when {
                                card.isMatched -> FunoraLimePrimary
                                card.isFaceUp -> FunoraLimeSecondary
                                else -> FunoraTextMuted
                            }
                        )
                    }
                }
            }
        }

        if (isWon) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = FunoraDarkSurface,
                border = BorderStroke(1.dp, FunoraLimePrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = FunoraLimePrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Complete! Solved in $moves moves! 🎉",
                        color = FunoraLimePrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FunoraGhostButton(
                text = "Restart game",
                icon = Icons.Default.Refresh,
                onClick = { viewModel.initMemoryGame() },
                modifier = Modifier.fillMaxWidth(),
                tag = "memory_restart_btn"
            )
        }
    }
}

// ==========================================
// 2. REACTION TEST GAME SCREEN
// ==========================================
@Composable
fun ReactionGameScreen(
    viewModel: FunoraViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.reactionState.collectAsState()
    val reactionMs by viewModel.reactionTimeMs.collectAsState()
    val stats by viewModel.stats.collectAsState()

    val padColor by animateColorAsState(
        targetValue = when (state) {
            ReactionState.IDLE -> FunoraDarkSurface
            ReactionState.WAITING -> FunoraNeonAmber
            ReactionState.GO -> FunoraLimePrimary
            ReactionState.EARLY -> FunoraError
            ReactionState.DONE -> FunoraDarkSurfaceHigh
        },
        animationSpec = tween(durationMillis = 150),
        label = "padColor"
    )

    val textColor = when (state) {
        ReactionState.WAITING, ReactionState.GO, ReactionState.EARLY -> FunoraLimeOnPrimary
        else -> FunoraTextHigh
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("reaction_game_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                FunoraTagPill(text = "SPEED CHALLENGE", color = FunoraNeonCyan)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Reaction Test",
                    color = FunoraTextHigh,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = FunoraDarkSurfaceHigh,
                border = BorderStroke(1.dp, FunoraDarkBorder)
            ) {
                Text(
                    text = if (stats.bestReactionMs > 0) "Best: ${stats.bestReactionMs} ms" else "No record",
                    color = FunoraNeonCyan,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Wait until the pad turns neon green, then tap as fast as you can!",
            color = FunoraTextMedium,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Big Interactive Reaction Pad
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = padColor,
            border = BorderStroke(2.dp, if (state == ReactionState.GO) FunoraLimePrimary else FunoraDarkBorder),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
                .clickable { viewModel.onReactionPadTapped() }
                .testTag("reaction_pad_button")
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    when (state) {
                        ReactionState.IDLE -> {
                            Text(text = "ϟ", fontSize = 48.sp, color = FunoraLimePrimary)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "TAP TO START",
                                color = textColor,
                                fontWeight = FontWeight.Black,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Test your reflex response time",
                                color = FunoraTextMedium,
                                fontSize = 14.sp
                            )
                        }
                        ReactionState.WAITING -> {
                            Text(text = "⏳", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "WAIT FOR GREEN...",
                                color = textColor,
                                fontWeight = FontWeight.Black,
                                fontSize = 22.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Don't tap yet!",
                                color = textColor.copy(alpha = 0.8f),
                                fontSize = 14.sp
                            )
                        }
                        ReactionState.GO -> {
                            Text(text = "⚡", fontSize = 56.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "TAP NOW!",
                                color = textColor,
                                fontWeight = FontWeight.Black,
                                fontSize = 36.sp
                            )
                        }
                        ReactionState.EARLY -> {
                            Text(text = "⚠", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "TOO EARLY!",
                                color = textColor,
                                fontWeight = FontWeight.Black,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Tap again to retry",
                                color = textColor.copy(alpha = 0.8f),
                                fontSize = 14.sp
                            )
                        }
                        ReactionState.DONE -> {
                            Text(
                                text = "${reactionMs ?: 0}",
                                color = FunoraLimePrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 54.sp
                            )
                            Text(
                                text = "MILLISECONDS",
                                color = FunoraTextMuted,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            val speedRating = when {
                                (reactionMs ?: 999) < 220 -> "⚡ Inhuman Godlike Reflexes!"
                                (reactionMs ?: 999) < 260 -> "🚀 Lightning Fast!"
                                (reactionMs ?: 999) < 320 -> "👏 Sharp & Speedy!"
                                else -> "👍 Good effort! Can you beat it?"
                            }
                            Text(
                                text = speedRating,
                                color = FunoraTextHigh,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Tap pad to try again",
                                color = FunoraLimePrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        FunoraGhostButton(
            text = "Reset test",
            onClick = { viewModel.startReactionTest() },
            modifier = Modifier.fillMaxWidth(),
            tag = "reaction_reset_btn"
        )
    }
}

// ==========================================
// 3. NUMBER PUZZLE GAME SCREEN
// ==========================================
@Composable
fun NumberPuzzleScreen(
    viewModel: FunoraViewModel,
    modifier: Modifier = Modifier
) {
    val currentIndex by viewModel.numberPuzzleIndex.collectAsState()
    val selectedAnswer by viewModel.numberSelectedAnswer.collectAsState()
    val score by viewModel.numberScore.collectAsState()
    val isFinished by viewModel.numberFinished.collectAsState()
    val puzzles = viewModel.numberPuzzles

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("number_puzzle_screen")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                FunoraTagPill(text = "LOGIC PUZZLE", color = FunoraNeonAmber)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Spot the Pattern",
                    color = FunoraTextHigh,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = FunoraDarkSurfaceHigh,
                border = BorderStroke(1.dp, FunoraDarkBorder)
            ) {
                Text(
                    text = if (isFinished) "Score: $score/${puzzles.size}" else "${currentIndex + 1} of ${puzzles.size}",
                    color = FunoraNeonAmber,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isFinished) {
            // Finished Screen
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = FunoraDarkSurface,
                border = BorderStroke(1.dp, FunoraDarkBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = FunoraLimePrimary,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Challenge Complete!",
                        color = FunoraTextHigh,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "You scored $score out of ${puzzles.size}",
                        color = FunoraLimePrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = if (score == puzzles.size) "Perfect pattern recognition! Master of sequences." else "Great mental workout! Every pattern sharpens your logic.",
                        color = FunoraTextMedium,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    FunoraButton(
                        text = "Play again",
                        onClick = { viewModel.restartNumberPuzzle() },
                        modifier = Modifier.fillMaxWidth(),
                        tag = "number_replay_btn"
                    )
                }
            }
        } else {
            val puzzle = puzzles[currentIndex]

            // Sequence Banner
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = FunoraDarkSurface,
                border = BorderStroke(1.5.dp, FunoraDarkBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "WHAT COMES NEXT?",
                        color = FunoraTextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = puzzle.sequence,
                        color = FunoraLimePrimary,
                        fontWeight = FontWeight.Black,
                        fontSize = 26.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Options List
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                puzzle.options.forEachIndexed { optIndex, optionText ->
                    val isChosen = selectedAnswer == optIndex
                    val isCorrect = optIndex == puzzle.answerIndex
                    val hasAnswered = selectedAnswer != null

                    val bgColor = when {
                        hasAnswered && isCorrect -> FunoraLimePrimary.copy(alpha = 0.2f)
                        hasAnswered && isChosen && !isCorrect -> FunoraError.copy(alpha = 0.2f)
                        isChosen -> FunoraDarkSurfaceHigh
                        else -> FunoraDarkSurface
                    }

                    val borderColor = when {
                        hasAnswered && isCorrect -> FunoraLimePrimary
                        hasAnswered && isChosen && !isCorrect -> FunoraError
                        else -> FunoraDarkBorder
                    }

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = bgColor,
                        border = BorderStroke(1.dp, borderColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = selectedAnswer == null) {
                                viewModel.selectNumberAnswer(optIndex)
                            }
                            .testTag("number_opt_$optIndex")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${('A' + optIndex)}.",
                                    color = if (hasAnswered && isCorrect) FunoraLimePrimary else FunoraTextMuted,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = optionText,
                                    color = FunoraTextHigh,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                            }

                            if (hasAnswered && isCorrect) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = FunoraLimePrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                // Explanation Box when answered
                AnimatedVisibility(visible = selectedAnswer != null) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = FunoraDarkSurfaceHigh,
                        border = BorderStroke(1.dp, FunoraDarkBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (selectedAnswer == puzzle.answerIndex) "That's right!" else "Not quite.",
                                color = if (selectedAnswer == puzzle.answerIndex) FunoraLimePrimary else FunoraError,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = puzzle.explanation,
                                color = FunoraTextMedium,
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }

            FunoraButton(
                text = if (currentIndex == puzzles.size - 1) "See results" else "Next puzzle",
                enabled = selectedAnswer != null,
                onClick = { viewModel.nextNumberPuzzle() },
                modifier = Modifier.fillMaxWidth(),
                tag = "number_next_btn"
            )
        }
    }
}
