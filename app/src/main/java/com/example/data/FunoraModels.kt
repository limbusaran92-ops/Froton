package com.example.data

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val answerIndex: Int,
    val explanation: String
)

data class Quiz(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val questions: List<QuizQuestion>
)

data class PersonalityOption(
    val label: String,
    val archetypeIndex: Int
)

data class PersonalityQuestion(
    val question: String,
    val options: List<PersonalityOption>
)

data class PersonalityResult(
    val title: String,
    val description: String,
    val badge: String,
    val traits: List<String>
)

data class PersonalityTest(
    val id: String,
    val title: String,
    val description: String,
    val questions: List<PersonalityQuestion>,
    val results: List<PersonalityResult>
)

data class DailyQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val answerIndex: Int,
    val explanation: String
)

data class NumberPuzzle(
    val sequence: String,
    val options: List<String>,
    val answerIndex: Int,
    val explanation: String
)

data class FunFact(
    val id: Int,
    val text: String,
    val category: String,
    val tag: String
)

enum class ReactionState {
    IDLE,
    WAITING,
    GO,
    EARLY,
    DONE
}

data class MemoryCard(
    val id: Int,
    val symbol: String,
    val isFaceUp: Boolean = false,
    val isMatched: Boolean = false
)
