package com.chesstrainer.data

data class LessonExercise(
    val id: String,
    val title: String,
    val prompt: String,
    val fen: String,
    val expectedMoves: Set<String>,
    val explanation: String
)

data class LessonModule(
    val id: String,
    val title: String,
    val summary: String,
    val focusAreas: List<String>,
    val exercises: List<LessonExercise>
)
