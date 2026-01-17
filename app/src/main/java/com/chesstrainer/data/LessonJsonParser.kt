package com.chesstrainer.data

import org.json.JSONArray
import org.json.JSONObject

object LessonJsonParser {
    fun parseModules(json: String): List<LessonModule> {
        val root = JSONObject(json)
        val modules = root.getJSONArray("modules")
        return modules.toLessonModules()
    }

    private fun JSONArray.toLessonModules(): List<LessonModule> {
        return List(length()) { index ->
            val moduleJson = getJSONObject(index)
            val exercises = moduleJson.getJSONArray("exercises").toLessonExercises()
            LessonModule(
                id = moduleJson.getString("id"),
                title = moduleJson.getString("title"),
                summary = moduleJson.getString("summary"),
                focusAreas = moduleJson.getJSONArray("focusAreas").toStringList(),
                exercises = exercises
            )
        }
    }

    private fun JSONArray.toLessonExercises(): List<LessonExercise> {
        return List(length()) { index ->
            val exerciseJson = getJSONObject(index)
            LessonExercise(
                id = exerciseJson.getString("id"),
                title = exerciseJson.getString("title"),
                prompt = exerciseJson.getString("prompt"),
                fen = exerciseJson.getString("fen"),
                expectedMoves = exerciseJson.getJSONArray("expectedMoves").toStringSet(),
                explanation = exerciseJson.getString("explanation")
            )
        }
    }

    private fun JSONArray.toStringList(): List<String> {
        return List(length()) { index -> getString(index) }
    }

    private fun JSONArray.toStringSet(): Set<String> {
        return List(length()) { index -> getString(index) }.toSet()
    }
}
