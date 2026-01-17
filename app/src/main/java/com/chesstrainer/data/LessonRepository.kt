package com.chesstrainer.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private val Context.lessonDataStore: DataStore<Preferences> by preferencesDataStore(name = "lesson_progress")

class LessonRepository(private val context: Context) {
    private val completedKey = stringSetPreferencesKey("completed_exercise_ids")

    fun observeCompletedExercises(): Flow<Set<String>> {
        return context.lessonDataStore.data.map { preferences ->
            preferences[completedKey] ?: emptySet()
        }
    }

    suspend fun markExerciseCompleted(exerciseId: String) {
        context.lessonDataStore.edit { preferences ->
            val current = preferences[completedKey] ?: emptySet()
            preferences[completedKey] = current + exerciseId
        }
    }

    suspend fun loadModules(): List<LessonModule> {
        return withContext(Dispatchers.IO) {
            val json = context.assets.open("lessons.json").bufferedReader().use { it.readText() }
            LessonJsonParser.parseModules(json)
        }
    }
}
