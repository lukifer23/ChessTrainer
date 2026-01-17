package com.chesstrainer.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONObject

private val Context.lessonDataStore: DataStore<Preferences> by preferencesDataStore(name = "lesson_progress")

class LessonRepository(private val context: Context) {
    private val completedKey = stringSetPreferencesKey("completed_exercise_ids")
    private val progressKey = stringPreferencesKey("lesson_exercise_progress")

    fun observeCompletedExercises(): Flow<Set<String>> {
        return context.lessonDataStore.data.map { preferences ->
            val legacyCompleted = preferences[completedKey] ?: emptySet()
            val progressCompleted = parseProgress(preferences[progressKey])
                .filterValues { it.completed }
                .keys
            legacyCompleted + progressCompleted
        }
    }

    fun observeExerciseProgress(): Flow<Map<String, LessonExerciseProgress>> {
        return context.lessonDataStore.data.map { preferences ->
            parseProgress(preferences[progressKey])
        }
    }

    suspend fun markExerciseCompleted(exerciseId: String, score: Int) {
        context.lessonDataStore.edit { preferences ->
            val current = preferences[completedKey] ?: emptySet()
            preferences[completedKey] = current + exerciseId
            preferences[progressKey] = updateProgressJson(
                preferences[progressKey],
                exerciseId,
                completed = true,
                score = score
            )
        }
    }

    suspend fun updateExerciseProgress(exerciseId: String, completed: Boolean, score: Int) {
        context.lessonDataStore.edit { preferences ->
            preferences[progressKey] = updateProgressJson(
                preferences[progressKey],
                exerciseId,
                completed = completed,
                score = score
            )
            if (completed) {
                val current = preferences[completedKey] ?: emptySet()
                preferences[completedKey] = current + exerciseId
            }
        }
    }

    suspend fun loadModules(): List<LessonModule> {
        return withContext(Dispatchers.IO) {
            val json = context.assets.open("lessons.json").bufferedReader().use { it.readText() }
            LessonJsonParser.parseModules(json)
        }
    }

    private fun parseProgress(json: String?): Map<String, LessonExerciseProgress> {
        if (json.isNullOrBlank()) return emptyMap()
        val root = runCatching { JSONObject(json) }.getOrNull() ?: return emptyMap()
        val result = mutableMapOf<String, LessonExerciseProgress>()
        val keys = root.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val entry = root.optJSONObject(key) ?: continue
            result[key] = LessonExerciseProgress(
                exerciseId = key,
                completed = entry.optBoolean("completed", false),
                score = entry.optInt("score", 0)
            )
        }
        return result
    }

    private fun updateProgressJson(
        json: String?,
        exerciseId: String,
        completed: Boolean,
        score: Int
    ): String {
        val root = runCatching { JSONObject(json ?: "{}") }.getOrDefault(JSONObject())
        val entry = JSONObject()
            .put("completed", completed)
            .put("score", score)
        root.put(exerciseId, entry)
        return root.toString()
    }
}
