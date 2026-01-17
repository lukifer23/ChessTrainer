package com.chesstrainer.lessons

data class Lesson(
    val id: String,
    val title: String,
    val description: String,
    val steps: List<LessonStep>
)

data class LessonStep(
    val fen: String,
    val prompt: String,
    val bestMove: String,
    val explanation: String
)
