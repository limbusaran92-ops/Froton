package com.example.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.ui.theme.FunoraNeonCyan
import com.example.ui.theme.FunoraTextHigh
import com.example.ui.theme.FunoraTextMedium
import com.example.ui.theme.FunoraTextMuted
import com.example.ui.viewmodel.FunoraViewModel

@Composable
fun QuizzesHubScreen(
    viewModel: FunoraViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val filteredQuizzes = FunoraData.quizzes.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
    }

    val filteredPersonalityTests = FunoraData.personalityTests.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("quizzes_hub_screen")
    ) {
        FunoraPageIntro(
            eyebrow = "QUIZ YOURSELF",
            title = "Curious mind?",
            accent = "Prove it.",
            description = "Pick an engaging topic, answer fun questions and discover something new."
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search quizzes or topics...", color = FunoraTextMuted) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = FunoraTextMedium
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = FunoraDarkSurface,
                unfocusedContainerColor = FunoraDarkSurface,
                focusedBorderColor = FunoraLimePrimary,
                unfocusedBorderColor = FunoraDarkBorder,
                focusedTextColor = FunoraTextHigh,
                unfocusedTextColor = FunoraTextHigh
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("quizzes_search_field")
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tabs: Trivia Quizzes vs Personality Tests
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = FunoraDarkSurface,
            contentColor = FunoraLimePrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = FunoraLimePrimary
                )
            },
            divider = {
                Surface(
                    color = FunoraDarkBorder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                ) {}
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = {
                    Text(
                        text = "Trivia Quizzes",
                        fontWeight = if (selectedTabIndex == 0) FontWeight.Bold else FontWeight.Medium,
                        color = if (selectedTabIndex == 0) FunoraLimePrimary else FunoraTextMedium
                    )
                },
                modifier = Modifier.testTag("tab_trivia_quizzes")
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = {
                    Text(
                        text = "Personality Tests",
                        fontWeight = if (selectedTabIndex == 1) FontWeight.Bold else FontWeight.Medium,
                        color = if (selectedTabIndex == 1) FunoraLimePrimary else FunoraTextMedium
                    )
                },
                modifier = Modifier.testTag("tab_personality_tests")
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (selectedTabIndex == 0) {
            // Trivia Quizzes List
            if (filteredQuizzes.isEmpty()) {
                EmptyStateCard(message = "No quizzes found matching '$searchQuery'.")
            } else {
                filteredQuizzes.forEach { quiz ->
                    QuizListCard(
                        category = quiz.category,
                        title = quiz.title,
                        description = quiz.description,
                        questionCount = quiz.questions.size,
                        onClick = { viewModel.startQuiz(quiz.id) },
                        testTag = "quiz_item_${quiz.id}"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        } else {
            // Personality Tests List
            if (filteredPersonalityTests.isEmpty()) {
                EmptyStateCard(message = "No personality tests found matching '$searchQuery'.")
            } else {
                filteredPersonalityTests.forEach { test ->
                    PersonalityListCard(
                        title = test.title,
                        description = test.description,
                        questionCount = test.questions.size,
                        onClick = { viewModel.startPersonalityTest(test.id) },
                        testTag = "personality_item_${test.id}"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun QuizListCard(
    category: String,
    title: String,
    description: String,
    questionCount: Int,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
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
                FunoraTagPill(text = category, color = FunoraLimePrimary)
                Text(
                    text = "$questionCount questions",
                    color = FunoraTextMuted,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                color = FunoraTextHigh,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
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
                    text = "Start quiz",
                    color = FunoraLimePrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = FunoraLimePrimary,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Composable
fun PersonalityListCard(
    title: String,
    description: String,
    questionCount: Int,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
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
                FunoraTagPill(text = "JUST FOR FUN", color = FunoraLimeSecondary)
                Text(
                    text = "$questionCount questions",
                    color = FunoraTextMuted,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                color = FunoraTextHigh,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
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
                    text = "Take personality test",
                    color = FunoraLimeSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = FunoraLimeSecondary,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Composable
fun EmptyStateCard(message: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = FunoraDarkSurface,
        border = BorderStroke(1.dp, FunoraDarkBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                color = FunoraTextMedium,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ==========================================
// QUIZ PLAY SCREEN
// ==========================================
@Composable
fun QuizPlayScreen(
    viewModel: FunoraViewModel,
    modifier: Modifier = Modifier
) {
    val quiz by viewModel.activeQuiz.collectAsState()
    val questionIndex by viewModel.quizQuestionIndex.collectAsState()
    val selectedAnswer by viewModel.quizSelectedAnswer.collectAsState()
    val score by viewModel.quizScore.collectAsState()
    val isFinished by viewModel.quizFinished.collectAsState()

    val currentQuiz = quiz ?: return
    val totalQuestions = currentQuiz.questions.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("quiz_play_screen")
    ) {
        if (isFinished) {
            // Results screen
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
                        text = "Quiz Complete!",
                        color = FunoraTextHigh,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "You scored $score / $totalQuestions",
                        color = FunoraLimePrimary,
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    val message = when {
                        score == totalQuestions -> "Perfect score! Outstanding knowledge."
                        score >= totalQuestions / 2 -> "Great job! Keep nurturing that curiosity."
                        else -> "Good try! Every question is a chance to learn something new."
                    }
                    Text(
                        text = message,
                        color = FunoraTextMedium,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    FunoraButton(
                        text = "Try again",
                        onClick = { viewModel.restartActiveQuiz() },
                        modifier = Modifier.fillMaxWidth(),
                        icon = Icons.Default.Refresh,
                        tag = "quiz_try_again_btn"
                    )
                }
            }
        } else {
            val question = currentQuiz.questions[questionIndex]
            val progress = (questionIndex.toFloat()) / totalQuestions.toFloat()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FunoraTagPill(text = currentQuiz.category, color = FunoraLimePrimary)
                Text(
                    text = "QUESTION ${questionIndex + 1} OF $totalQuestions",
                    color = FunoraTextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { progress },
                color = FunoraLimePrimary,
                trackColor = FunoraDarkSurfaceHigh,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = question.question,
                color = FunoraTextHigh,
                style = MaterialTheme.typography.headlineSmall,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Options
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                question.options.forEachIndexed { optIndex, optionText ->
                    val isChosen = selectedAnswer == optIndex
                    val isCorrect = optIndex == question.answerIndex
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
                                viewModel.selectQuizAnswer(optIndex)
                            }
                            .testTag("quiz_opt_$optIndex")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "${('A' + optIndex)}.",
                                    color = if (hasAnswered && isCorrect) FunoraLimePrimary else FunoraTextMuted,
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

                            if (hasAnswered && isCorrect) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = FunoraLimePrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            } else if (hasAnswered && isChosen) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    tint = FunoraError,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                // Explanation
                AnimatedVisibility(visible = selectedAnswer != null) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = FunoraDarkSurfaceHigh,
                        border = BorderStroke(1.dp, FunoraDarkBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (selectedAnswer == question.answerIndex) "That's right!" else "Not quite.",
                                color = if (selectedAnswer == question.answerIndex) FunoraLimePrimary else FunoraError,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = question.explanation,
                                color = FunoraTextMedium,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            FunoraButton(
                text = if (questionIndex == totalQuestions - 1) "See results" else "Next question",
                enabled = selectedAnswer != null,
                onClick = { viewModel.nextQuizQuestion() },
                modifier = Modifier.fillMaxWidth(),
                tag = "quiz_next_btn"
            )
        }
    }
}

// ==========================================
// PERSONALITY PLAY SCREEN
// ==========================================
@Composable
fun PersonalityPlayScreen(
    viewModel: FunoraViewModel,
    modifier: Modifier = Modifier
) {
    val test by viewModel.activePersonalityTest.collectAsState()
    val questionIndex by viewModel.personalityQuestionIndex.collectAsState()
    val result by viewModel.personalityResult.collectAsState()

    val currentTest = test ?: return
    val totalQuestions = currentTest.questions.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("personality_play_screen")
    ) {
        if (result != null) {
            val res = result!!
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = FunoraDarkSurface,
                border = BorderStroke(1.dp, FunoraLimeSecondary.copy(alpha = 0.5f)),
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
                            .background(FunoraLimeSecondary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = FunoraLimeSecondary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    FunoraTagPill(text = res.badge, color = FunoraLimeSecondary)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = res.title,
                        color = FunoraTextHigh,
                        style = MaterialTheme.typography.displayMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = res.description,
                        color = FunoraTextMedium,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        res.traits.forEach { trait ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = FunoraDarkSurfaceHigh,
                                border = BorderStroke(1.dp, FunoraDarkBorder)
                            ) {
                                Text(
                                    text = trait,
                                    color = FunoraTextHigh,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    FunoraButton(
                        text = "Take test again",
                        onClick = { viewModel.restartPersonalityTest() },
                        modifier = Modifier.fillMaxWidth(),
                        icon = Icons.Default.Refresh,
                        tag = "personality_restart_btn"
                    )
                }
            }
        } else {
            val question = currentTest.questions[questionIndex]
            val progress = (questionIndex.toFloat()) / totalQuestions.toFloat()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FunoraTagPill(text = "NOT SCIENTIFIC · JUST FOR FUN", color = FunoraLimeSecondary)
                Text(
                    text = "${questionIndex + 1} OF $totalQuestions",
                    color = FunoraTextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { progress },
                color = FunoraLimeSecondary,
                trackColor = FunoraDarkSurfaceHigh,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = question.question,
                color = FunoraTextHigh,
                style = MaterialTheme.typography.headlineSmall,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                question.options.forEachIndexed { optIndex, option ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = FunoraDarkSurface,
                        border = BorderStroke(1.dp, FunoraDarkBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.selectPersonalityOption(option.archetypeIndex)
                            }
                            .testTag("personality_opt_$optIndex")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "✦",
                                    color = FunoraLimeSecondary,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = option.label,
                                    color = FunoraTextHigh,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = FunoraLimeSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
