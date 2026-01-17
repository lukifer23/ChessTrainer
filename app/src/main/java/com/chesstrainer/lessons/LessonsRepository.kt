package com.chesstrainer.lessons

import android.content.Context
import org.json.JSONObject

class LessonsRepository(private val context: Context) {
    fun loadLessons(): List<Lesson> {
        val json = context.assets.open("lessons.json").bufferedReader().use { it.readText() }
        val root = JSONObject(json)
        val lessonsArray = root.getJSONArray("lessons")
        val lessons = mutableListOf<Lesson>()

        for (i in 0 until lessonsArray.length()) {
            val lessonObj = lessonsArray.getJSONObject(i)
            val stepsArray = lessonObj.getJSONArray("steps")
            val steps = mutableListOf<LessonStep>()

            for (j in 0 until stepsArray.length()) {
                val stepObj = stepsArray.getJSONObject(j)
                steps.add(
                    LessonStep(
                        fen = stepObj.getString("fen"),
                        prompt = stepObj.getString("prompt"),
                        bestMove = stepObj.getString("bestMove"),
                        explanation = stepObj.getString("explanation")
                    )
                )
            }

            lessons.add(
                Lesson(
                    id = lessonObj.getString("id"),
                    title = lessonObj.getString("title"),
                    description = lessonObj.getString("description"),
                    steps = steps
                )
            )
        }

        return lessons
    }
}
