package com.example.ui.viewmodel

import android.app.Application
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.DailyQuestion
import com.example.data.FunFact
import com.example.data.FunoraData
import com.example.data.FunoraPreferences
import com.example.data.FunoraStats
import com.example.data.MemoryCard
import com.example.data.NumberPuzzle
import com.example.data.PersonalityResult
import com.example.data.PersonalityTest
import com.example.data.Quiz
import com.example.data.ReactionState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

sealed class FunoraScreen {
    object Home : FunoraScreen()
    object Games : FunoraScreen()
    object MemoryGame : FunoraScreen()
    object ReactionGame : FunoraScreen()
    object NumberGame : FunoraScreen()
    object Quizzes : FunoraScreen()
    data class QuizPlay(val quizId: String) : FunoraScreen()
    data class PersonalityPlay(val testId: String) : FunoraScreen()
    object Facts : FunoraScreen()
    object Daily : FunoraScreen()
    object About : FunoraScreen()
}

class FunoraViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences = FunoraPreferences(application)

    val stats: StateFlow<FunoraStats> = preferences.statsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FunoraStats()
    )

    private val _currentScreen = MutableStateFlow<FunoraScreen>(FunoraScreen.Home)
    val currentScreen: StateFlow<FunoraScreen> = _currentScreen.asStateFlow()

    private val screenBackStack = mutableListOf<FunoraScreen>(FunoraScreen.Home)

    fun navigateTo(screen: FunoraScreen) {
        if (_currentScreen.value != screen) {
            screenBackStack.add(screen)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenBackStack.size > 1) {
            screenBackStack.removeAt(screenBackStack.lastIndex)
            _currentScreen.value = screenBackStack.last()
            return true
        }
        return false
    }

    // ==========================================
    // MEMORY MATCH GAME
    // ==========================================
    private val memorySymbols = listOf("✦", "☀", "☂", "♫", "☘", "◆")

    private val _memoryCards = MutableStateFlow<List<MemoryCard>>(emptyList())
    val memoryCards: StateFlow<List<MemoryCard>> = _memoryCards.asStateFlow()

    private val _memoryMoves = MutableStateFlow(0)
    val memoryMoves: StateFlow<Int> = _memoryMoves.asStateFlow()

    private val _memoryLocked = MutableStateFlow(false)
    val memoryLocked: StateFlow<Boolean> = _memoryLocked.asStateFlow()

    private val _memoryWon = MutableStateFlow(false)
    val memoryWon: StateFlow<Boolean> = _memoryWon.asStateFlow()

    private val flippedIndices = mutableListOf<Int>()

    init {
        initMemoryGame()
    }

    fun initMemoryGame() {
        val paired = (memorySymbols + memorySymbols).shuffled()
        _memoryCards.value = paired.mapIndexed { index, symbol ->
            MemoryCard(id = index, symbol = symbol)
        }
        _memoryMoves.value = 0
        _memoryLocked.value = false
        _memoryWon.value = false
        flippedIndices.clear()
    }

    fun onMemoryCardClicked(index: Int) {
        if (_memoryLocked.value) return
        val currentCards = _memoryCards.value
        if (index !in currentCards.indices) return
        val card = currentCards[index]
        if (card.isFaceUp || card.isMatched) return

        // Flip card up
        val updated = currentCards.mapIndexed { i, c ->
            if (i == index) c.copy(isFaceUp = true) else c
        }
        _memoryCards.value = updated
        flippedIndices.add(index)

        if (flippedIndices.size == 2) {
            _memoryMoves.value += 1
            val idx1 = flippedIndices[0]
            val idx2 = flippedIndices[1]
            val card1 = updated[idx1]
            val card2 = updated[idx2]

            if (card1.symbol == card2.symbol) {
                // Matched!
                val matchedList = updated.mapIndexed { i, c ->
                    if (i == idx1 || i == idx2) c.copy(isMatched = true) else c
                }
                _memoryCards.value = matchedList
                flippedIndices.clear()

                if (matchedList.all { it.isMatched }) {
                    _memoryWon.value = true
                    viewModelScope.launch {
                        preferences.recordMemoryWin(_memoryMoves.value)
                    }
                }
            } else {
                // Not a match, flip back after delay
                _memoryLocked.value = true
                viewModelScope.launch {
                    delay(800)
                    _memoryCards.value = _memoryCards.value.mapIndexed { i, c ->
                        if (i == idx1 || i == idx2) c.copy(isFaceUp = false) else c
                    }
                    flippedIndices.clear()
                    _memoryLocked.value = false
                }
            }
        }
    }

    // ==========================================
    // REACTION TEST GAME
    // ==========================================
    private val _reactionState = MutableStateFlow(ReactionState.IDLE)
    val reactionState: StateFlow<ReactionState> = _reactionState.asStateFlow()

    private val _reactionTimeMs = MutableStateFlow<Int?>(null)
    val reactionTimeMs: StateFlow<Int?> = _reactionTimeMs.asStateFlow()

    private var reactionJob: Job? = null
    private var reactionStartNano: Long = 0L

    fun startReactionTest() {
        reactionJob?.cancel()
        _reactionTimeMs.value = null
        _reactionState.value = ReactionState.WAITING

        reactionJob = viewModelScope.launch {
            val randomDelay = Random.nextLong(1500, 3500)
            delay(randomDelay)
            reactionStartNano = SystemClock.elapsedRealtimeNanos()
            _reactionState.value = ReactionState.GO
        }
    }

    fun onReactionPadTapped() {
        when (_reactionState.value) {
            ReactionState.IDLE, ReactionState.DONE, ReactionState.EARLY -> {
                startReactionTest()
            }
            ReactionState.WAITING -> {
                reactionJob?.cancel()
                _reactionState.value = ReactionState.EARLY
            }
            ReactionState.GO -> {
                val endNano = SystemClock.elapsedRealtimeNanos()
                val elapsedMs = ((endNano - reactionStartNano) / 1_000_000L).toInt().coerceAtLeast(1)
                _reactionTimeMs.value = elapsedMs
                _reactionState.value = ReactionState.DONE
                viewModelScope.launch {
                    preferences.recordReactionTime(elapsedMs)
                }
            }
        }
    }

    // ==========================================
    // NUMBER PUZZLE GAME
    // ==========================================
    val numberPuzzles: List<NumberPuzzle> = FunoraData.numberPuzzles

    private val _numberPuzzleIndex = MutableStateFlow(0)
    val numberPuzzleIndex: StateFlow<Int> = _numberPuzzleIndex.asStateFlow()

    private val _numberSelectedAnswer = MutableStateFlow<Int?>(null)
    val numberSelectedAnswer: StateFlow<Int?> = _numberSelectedAnswer.asStateFlow()

    private val _numberScore = MutableStateFlow(0)
    val numberScore: StateFlow<Int> = _numberScore.asStateFlow()

    private val _numberFinished = MutableStateFlow(false)
    val numberFinished: StateFlow<Boolean> = _numberFinished.asStateFlow()

    fun selectNumberAnswer(optionIndex: Int) {
        if (_numberSelectedAnswer.value == null && !_numberFinished.value) {
            _numberSelectedAnswer.value = optionIndex
            val currentPuzzle = numberPuzzles[_numberPuzzleIndex.value]
            if (optionIndex == currentPuzzle.answerIndex) {
                _numberScore.value += 1
            }
        }
    }

    fun nextNumberPuzzle() {
        if (_numberPuzzleIndex.value < numberPuzzles.size - 1) {
            _numberPuzzleIndex.value += 1
            _numberSelectedAnswer.value = null
        } else {
            _numberFinished.value = true
            viewModelScope.launch {
                preferences.recordNumberPuzzleScore(_numberScore.value)
            }
        }
    }

    fun restartNumberPuzzle() {
        _numberPuzzleIndex.value = 0
        _numberSelectedAnswer.value = null
        _numberScore.value = 0
        _numberFinished.value = false
    }

    // ==========================================
    // TRIVIA QUIZ
    // ==========================================
    private val _activeQuiz = MutableStateFlow<Quiz?>(null)
    val activeQuiz: StateFlow<Quiz?> = _activeQuiz.asStateFlow()

    private val _quizQuestionIndex = MutableStateFlow(0)
    val quizQuestionIndex: StateFlow<Int> = _quizQuestionIndex.asStateFlow()

    private val _quizSelectedAnswer = MutableStateFlow<Int?>(null)
    val quizSelectedAnswer: StateFlow<Int?> = _quizSelectedAnswer.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    private val _quizFinished = MutableStateFlow(false)
    val quizFinished: StateFlow<Boolean> = _quizFinished.asStateFlow()

    fun startQuiz(quizId: String) {
        val q = FunoraData.quizzes.find { it.id == quizId } ?: FunoraData.quizzes.first()
        _activeQuiz.value = q
        _quizQuestionIndex.value = 0
        _quizSelectedAnswer.value = null
        _quizScore.value = 0
        _quizFinished.value = false
        navigateTo(FunoraScreen.QuizPlay(q.id))
    }

    fun selectQuizAnswer(index: Int) {
        if (_quizSelectedAnswer.value == null && !_quizFinished.value) {
            _quizSelectedAnswer.value = index
            val quiz = _activeQuiz.value ?: return
            val question = quiz.questions[_quizQuestionIndex.value]
            if (index == question.answerIndex) {
                _quizScore.value += 1
            }
        }
    }

    fun nextQuizQuestion() {
        val quiz = _activeQuiz.value ?: return
        if (_quizQuestionIndex.value < quiz.questions.size - 1) {
            _quizQuestionIndex.value += 1
            _quizSelectedAnswer.value = null
        } else {
            _quizFinished.value = true
            viewModelScope.launch {
                preferences.incrementQuizzesCompleted()
            }
        }
    }

    fun restartActiveQuiz() {
        _quizQuestionIndex.value = 0
        _quizSelectedAnswer.value = null
        _quizScore.value = 0
        _quizFinished.value = false
    }

    // ==========================================
    // PERSONALITY TEST
    // ==========================================
    private val _activePersonalityTest = MutableStateFlow<PersonalityTest?>(null)
    val activePersonalityTest: StateFlow<PersonalityTest?> = _activePersonalityTest.asStateFlow()

    private val _personalityQuestionIndex = MutableStateFlow(0)
    val personalityQuestionIndex: StateFlow<Int> = _personalityQuestionIndex.asStateFlow()

    private val personalityScoreArray = IntArray(4)

    private val _personalityResult = MutableStateFlow<PersonalityResult?>(null)
    val personalityResult: StateFlow<PersonalityResult?> = _personalityResult.asStateFlow()

    fun startPersonalityTest(testId: String) {
        val test = FunoraData.personalityTests.find { it.id == testId } ?: FunoraData.personalityTests.first()
        _activePersonalityTest.value = test
        _personalityQuestionIndex.value = 0
        for (i in 0 until 4) personalityScoreArray[i] = 0
        _personalityResult.value = null
        navigateTo(FunoraScreen.PersonalityPlay(test.id))
    }

    fun selectPersonalityOption(archetypeIndex: Int) {
        if (_personalityResult.value != null) return
        if (archetypeIndex in 0..3) {
            personalityScoreArray[archetypeIndex]++
        }
        val test = _activePersonalityTest.value ?: return
        if (_personalityQuestionIndex.value < test.questions.size - 1) {
            _personalityQuestionIndex.value += 1
        } else {
            // Find winning archetype index
            var maxIndex = 0
            var maxVal = personalityScoreArray[0]
            for (i in 1..3) {
                if (personalityScoreArray[i] > maxVal) {
                    maxVal = personalityScoreArray[i]
                    maxIndex = i
                }
            }
            _personalityResult.value = test.results.getOrNull(maxIndex) ?: test.results.first()
        }
    }

    fun restartPersonalityTest() {
        _personalityQuestionIndex.value = 0
        for (i in 0 until 4) personalityScoreArray[i] = 0
        _personalityResult.value = null
    }

    // ==========================================
    // FUN FACTS CAROUSEL
    // ==========================================
    val allFacts: List<FunFact> = FunoraData.facts

    private val _factIndex = MutableStateFlow(0)
    val factIndex: StateFlow<Int> = _factIndex.asStateFlow()

    fun nextFact() {
        _factIndex.value = (_factIndex.value + 1) % allFacts.size
    }

    fun prevFact() {
        val size = allFacts.size
        _factIndex.value = (_factIndex.value - 1 + size) % size
    }

    fun randomFact() {
        val next = Random.nextInt(allFacts.size)
        _factIndex.value = if (next == _factIndex.value) (next + 1) % allFacts.size else next
    }

    fun toggleFavorite(factId: Int) {
        viewModelScope.launch {
            preferences.toggleFavoriteFact(factId)
        }
    }

    // ==========================================
    // DAILY CHALLENGE
    // ==========================================
    val todayDateString: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    val todayQuestion: DailyQuestion by lazy {
        val dayNumber = SimpleDateFormat("d", Locale.getDefault()).format(Date()).toIntOrNull() ?: 1
        val pool = FunoraData.dailyQuestionsPool
        pool[dayNumber % pool.size]
    }

    private val _selectedDailyOption = MutableStateFlow<Int?>(null)
    val selectedDailyOption: StateFlow<Int?> = _selectedDailyOption.asStateFlow()

    fun selectDailyOption(index: Int) {
        if (stats.value.dailyCompletedDate != todayDateString) {
            _selectedDailyOption.value = index
        }
    }

    fun submitDailyAnswer() {
        val choice = _selectedDailyOption.value ?: return
        val isCorrect = choice == todayQuestion.answerIndex
        viewModelScope.launch {
            preferences.recordDailyCompletion(todayDateString, choice, isCorrect)
        }
    }
}
