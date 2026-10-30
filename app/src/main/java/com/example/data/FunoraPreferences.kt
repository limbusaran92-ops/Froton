package com.example.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "funora_prefs")

data class FunoraStats(
    val dailyCompletedDate: String = "",
    val dailySelectedOption: Int = -1,
    val dailyIsCorrect: Boolean = false,
    val bestMemoryMoves: Int = 0,
    val memoryWins: Int = 0,
    val bestReactionMs: Int = 0,
    val bestNumberPuzzleScore: Int = 0,
    val quizzesCompletedCount: Int = 0,
    val favoriteFactIds: Set<String> = emptySet()
)

class FunoraPreferences(private val context: Context) {

    companion object {
        private val KEY_DAILY_DATE = stringPreferencesKey("daily_date")
        private val KEY_DAILY_SELECTION = intPreferencesKey("daily_selection")
        private val KEY_DAILY_CORRECT = stringPreferencesKey("daily_correct")
        private val KEY_BEST_MEMORY = intPreferencesKey("best_memory_moves")
        private val KEY_MEMORY_WINS = intPreferencesKey("memory_wins")
        private val KEY_BEST_REACTION = intPreferencesKey("best_reaction_ms")
        private val KEY_BEST_NUMBER = intPreferencesKey("best_number_score")
        private val KEY_QUIZZES_COUNT = intPreferencesKey("quizzes_count")
        private val KEY_FAVORITE_FACTS = stringSetPreferencesKey("favorite_facts")
    }

    val statsFlow: Flow<FunoraStats> = context.dataStore.data.map { prefs ->
        FunoraStats(
            dailyCompletedDate = prefs[KEY_DAILY_DATE] ?: "",
            dailySelectedOption = prefs[KEY_DAILY_SELECTION] ?: -1,
            dailyIsCorrect = prefs[KEY_DAILY_CORRECT] == "true",
            bestMemoryMoves = prefs[KEY_BEST_MEMORY] ?: 0,
            memoryWins = prefs[KEY_MEMORY_WINS] ?: 0,
            bestReactionMs = prefs[KEY_BEST_REACTION] ?: 0,
            bestNumberPuzzleScore = prefs[KEY_BEST_NUMBER] ?: 0,
            quizzesCompletedCount = prefs[KEY_QUIZZES_COUNT] ?: 0,
            favoriteFactIds = prefs[KEY_FAVORITE_FACTS] ?: emptySet()
        )
    }

    suspend fun recordDailyCompletion(dateKey: String, selection: Int, correct: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_DAILY_DATE] = dateKey
            prefs[KEY_DAILY_SELECTION] = selection
            prefs[KEY_DAILY_CORRECT] = if (correct) "true" else "false"
        }
    }

    suspend fun recordMemoryWin(moves: Int) {
        context.dataStore.edit { prefs ->
            val currentBest = prefs[KEY_BEST_MEMORY] ?: 0
            if (currentBest == 0 || moves < currentBest) {
                prefs[KEY_BEST_MEMORY] = moves
            }
            prefs[KEY_MEMORY_WINS] = (prefs[KEY_MEMORY_WINS] ?: 0) + 1
        }
    }

    suspend fun recordReactionTime(ms: Int) {
        context.dataStore.edit { prefs ->
            val currentBest = prefs[KEY_BEST_REACTION] ?: 0
            if (currentBest == 0 || ms < currentBest) {
                prefs[KEY_BEST_REACTION] = ms
            }
        }
    }

    suspend fun recordNumberPuzzleScore(score: Int) {
        context.dataStore.edit { prefs ->
            val currentBest = prefs[KEY_BEST_NUMBER] ?: 0
            if (score > currentBest) {
                prefs[KEY_BEST_NUMBER] = score
            }
        }
    }

    suspend fun incrementQuizzesCompleted() {
        context.dataStore.edit { prefs ->
            prefs[KEY_QUIZZES_COUNT] = (prefs[KEY_QUIZZES_COUNT] ?: 0) + 1
        }
    }

    suspend fun toggleFavoriteFact(factId: Int) {
        context.dataStore.edit { prefs ->
            val idStr = factId.toString()
            val current = (prefs[KEY_FAVORITE_FACTS] ?: emptySet()).toMutableSet()
            if (current.contains(idStr)) {
                current.remove(idStr)
            } else {
                current.add(idStr)
            }
            prefs[KEY_FAVORITE_FACTS] = current
        }
    }
}
