package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.ui.theme.FunoraNeonAmber
import com.example.ui.theme.FunoraNeonCoral
import com.example.ui.theme.FunoraNeonCyan
import com.example.ui.theme.FunoraTextHigh
import com.example.ui.theme.FunoraTextMedium
import com.example.ui.theme.FunoraTextMuted
import com.example.ui.viewmodel.FunoraViewModel

// ==========================================
// DAILY CHALLENGE SCREEN
// ==========================================
@Composable
fun DailyChallengeScreen(
    viewModel: FunoraViewModel,
    modifier: Modifier = Modifier
) {
    val stats by viewModel.stats.collectAsState()
    val selectedOption by viewModel.selectedDailyOption.collectAsState()
    val isCompleted = stats.dailyCompletedDate == viewModel.todayDateString
    val dailyQ = viewModel.todayQuestion

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("daily_challenge_screen")
    ) {
        FunoraPageIntro(
            eyebrow = "FRESH EVERY DAY",
            title = "The daily",
            accent = "challenge.",
            description = "One quick question. A daily brain workout. Come back tomorrow for another."
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (isCompleted) {
            // Completed card
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = FunoraDarkSurface,
                border = BorderStroke(1.dp, if (stats.dailyIsCorrect) FunoraLimePrimary else FunoraDarkBorder),
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
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (stats.dailyIsCorrect) FunoraLimePrimary else FunoraDarkSurfaceHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (stats.dailyIsCorrect) Icons.Default.EmojiEvents else Icons.Default.Bolt,
                            contentDescription = null,
                            tint = if (stats.dailyIsCorrect) FunoraLimeOnPrimary else FunoraLimePrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (stats.dailyIsCorrect) "Nailed it! 🎉" else "Challenge complete!",
                        color = FunoraTextHigh,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (stats.dailyIsCorrect) {
                            dailyQ.explanation
                        } else {
                            "The correct answer was: ${dailyQ.options[dailyQ.answerIndex]}.\n${dailyQ.explanation}"
                        },
                        color = FunoraTextMedium,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = FunoraDarkSurfaceHigh,
                        border = BorderStroke(1.dp, FunoraDarkBorder)
                    ) {
                        Text(
                            text = "Next question drops tomorrow!",
                            color = FunoraLimePrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        } else {
            // Active Question
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = FunoraDarkSurface,
                border = BorderStroke(1.dp, FunoraDarkBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(FunoraLimePrimary)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = viewModel.todayDateString,
                            color = FunoraTextHigh,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                    }
                    FunoraTagPill(text = "ONE QUESTION", color = FunoraLimePrimary)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = dailyQ.question,
                color = FunoraTextHigh,
                style = MaterialTheme.typography.headlineSmall,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                dailyQ.options.forEachIndexed { optIndex, optionText ->
                    val isChosen = selectedOption == optIndex

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isChosen) FunoraDarkSurfaceHigh else FunoraDarkSurface,
                        border = BorderStroke(1.dp, if (isChosen) FunoraLimePrimary else FunoraDarkBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.selectDailyOption(optIndex) }
                            .testTag("daily_opt_$optIndex")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${('A' + optIndex)}.",
                                color = if (isChosen) FunoraLimePrimary else FunoraTextMuted,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = optionText,
                                color = FunoraTextHigh,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }

            FunoraButton(
                text = "Submit answer",
                enabled = selectedOption != null,
                onClick = { viewModel.submitDailyAnswer() },
                modifier = Modifier.fillMaxWidth(),
                tag = "daily_submit_btn"
            )
        }
    }
}

// ==========================================
// FUN FACTS SCREEN
// ==========================================
@Composable
fun FactsScreen(
    viewModel: FunoraViewModel,
    modifier: Modifier = Modifier
) {
    val facts = viewModel.allFacts
    val factIndex by viewModel.factIndex.collectAsState()
    val stats by viewModel.stats.collectAsState()
    val context = LocalContext.current

    val currentFact = facts[factIndex]
    val isFavorite = stats.favoriteFactIds.contains(currentFact.id.toString())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("facts_screen")
    ) {
        FunoraPageIntro(
            eyebrow = "CURIOSITY BREAK",
            title = "Tiny facts.",
            accent = "Big wonder.",
            description = "A few surprising things to brighten your day and feed your intellect."
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Fact Card
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = FunoraDarkSurface,
            border = BorderStroke(1.5.dp, FunoraDarkBorder),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(22.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Header of card
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FunoraTagPill(text = currentFact.tag, color = FunoraNeonAmber)

                    Row {
                        IconButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Funora Fact", currentFact.text)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Fact copied to clipboard!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.testTag("fact_copy_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy fact",
                                tint = FunoraTextMedium
                            )
                        }

                        IconButton(
                            onClick = { viewModel.toggleFavorite(currentFact.id) },
                            modifier = Modifier.testTag("fact_favorite_btn")
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) FunoraNeonCoral else FunoraTextMedium
                            )
                        }
                    }
                }

                // Middle: Fact Text
                Column {
                    Text(
                        text = "DID YOU KNOW?",
                        color = FunoraLimePrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = currentFact.text,
                        color = FunoraTextHigh,
                        style = MaterialTheme.typography.headlineMedium,
                        lineHeight = 32.sp
                    )
                }

                // Bottom: Index indicator & Category
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Topic: ${currentFact.category}",
                        color = FunoraTextMedium,
                        fontSize = 12.sp
                    )
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = FunoraDarkSurfaceHigh
                    ) {
                        Text(
                            text = "${factIndex + 1} / ${facts.size}",
                            color = FunoraLimePrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FunoraGhostButton(
                text = "Prev",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { viewModel.prevFact() },
                modifier = Modifier.weight(1f),
                tag = "fact_prev_btn"
            )
            FunoraGhostButton(
                text = "Random",
                icon = Icons.Default.Shuffle,
                onClick = { viewModel.randomFact() },
                modifier = Modifier.weight(1f),
                tag = "fact_random_btn"
            )
            FunoraButton(
                text = "Next",
                icon = Icons.AutoMirrored.Filled.ArrowForward,
                onClick = { viewModel.nextFact() },
                modifier = Modifier.weight(1f),
                tag = "fact_next_btn"
            )
        }
    }
}

// ==========================================
// ABOUT & PRIVACY SCREEN
// ==========================================
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("about_screen")
    ) {
        FunoraPageIntro(
            eyebrow = "ABOUT FUNORA",
            title = "Play. Discover.",
            accent = "Enjoy.",
            description = "A little more curiosity and joy in every day."
        )

        Spacer(modifier = Modifier.height(20.dp))

        // About Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = FunoraDarkSurface),
            border = BorderStroke(1.dp, FunoraDarkBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = FunoraLimePrimary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "What is Funora?",
                        color = FunoraTextHigh,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Funora is an entertainment platform crafted for curious minds everywhere. We bring together bite-sized mini games, trivia quizzes, lighthearted personality experiences and surprising fun facts. Our mission is to make daily micro-breaks playful and intellectually stimulating.",
                    color = FunoraTextMedium,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Privacy Policy Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = FunoraDarkSurface),
            border = BorderStroke(1.dp, FunoraDarkBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = FunoraNeonCyan,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Privacy by Design",
                        color = FunoraTextHigh,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Funora does not collect or share personal data. Your game scores, reaction times, quiz progress and favorite facts are stored strictly on your device using secure local Android DataStore.",
                    color = FunoraTextMedium,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Contact Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = FunoraDarkSurface),
            border = BorderStroke(1.dp, FunoraDarkBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Mail,
                        contentDescription = null,
                        tint = FunoraNeonAmber,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Say Hello",
                        color = FunoraTextHigh,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Have ideas for new quizzes, game modes or crazy facts? We'd love to hear your thoughts and feedback.",
                    color = FunoraTextMedium,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
