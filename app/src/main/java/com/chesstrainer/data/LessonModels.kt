package com.chesstrainer.data

data class LessonExercise(
    val id: String,
    val title: String,
    val prompt: String,
    val fen: String,
    val solutionLine: List<String>,
    val explanation: String,
    val scorePerMove: Int = 10,
    val maxScore: Int? = null,
    val minEval: Int? = null,
    val maxMistake: Int? = null
) {
    fun resolvedMaxScore(): Int = maxScore ?: solutionLine.size * scorePerMove
}

data class LessonExerciseProgress(
    val exerciseId: String,
    val completed: Boolean,
    val score: Int
)

data class LessonModule(
    val id: String,
    val title: String,
    val summary: String,
    val focusAreas: List<String>,
    val exercises: List<LessonExercise>
)
