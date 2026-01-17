package com.chesstrainer.engine

import com.chesstrainer.chess.Move
import com.chesstrainer.chess.Square

/**
 * UCI (Universal Chess Interface) protocol parser and command builder.
 * Handles all communication between the chess engine and the application.
 */
class UCIParser {

    // UCI Response types
    sealed class UCIResponse(val type: ResponseType) {
        data class IdResponse(val name: String, val author: String) : UCIResponse(ResponseType.ID)
        data class UciOkResponse(val options: Map<String, UCIOption> = emptyMap()) : UCIResponse(ResponseType.UCIOK)
        data class ReadyOkResponse(val dummy: Int = 0) : UCIResponse(ResponseType.READYOK)
        data class BestMoveResponse(val move: Move, val ponder: Move? = null) : UCIResponse(ResponseType.BESTMOVE)
        data class InfoResponse(val info: EngineInfo) : UCIResponse(ResponseType.INFO)
        data class OptionResponse(val option: UCIOption) : UCIResponse(ResponseType.OPTION)
        data class ErrorResponse(val message: String) : UCIResponse(ResponseType.ERROR)
        data class UnknownResponse(val line: String) : UCIResponse(ResponseType.UNKNOWN)
    }

    enum class ResponseType {
        ID, UCIOK, READYOK, BESTMOVE, INFO, OPTION, ERROR, UNKNOWN
    }

    // Engine info from "info" command
    data class EngineInfo(
        val depth: Int? = null,
        val selectiveDepth: Int? = null,
        val time: Long? = null,
        val nodes: Long? = null,
        val principalVariation: List<Move> = emptyList(),
        val multiPrincipalVariation: Int? = null,
        val score: Score? = null,
        val currentMove: Move? = null,
        val currentMoveNumber: Int? = null,
        val hashFull: Int? = null,
        val nodesPerSecond: Long? = null,
        val tableBaseHits: Long? = null,
        val cpuLoad: Int? = null,
        val string: String? = null,
        val refutation: List<Move> = emptyList(),
        val currentLine: List<Move> = emptyList()
    )

    data class Score(
        val centipawns: Int? = null,
        val mate: Int? = null,
        val lowerBound: Boolean = false,
        val upperBound: Boolean = false
    )

    data class UCIOption(
        val name: String,
        val type: OptionType,
        val default: Any? = null,
        val min: Any? = null,
        val max: Any? = null,
        val options: List<String> = emptyList()
    )

    enum class OptionType {
        CHECK, SPIN, COMBO, BUTTON, STRING
    }

    companion object {

        /**
         * Parse a line of UCI output and return the appropriate response type.
         */
        fun parseLine(line: String): UCIResponse {
            val trimmed = line.trim()
            if (trimmed.isEmpty()) return UCIResponse.UnknownResponse(trimmed)

            val parts = trimmed.split(" ")
            if (parts.isEmpty()) return UCIResponse.UnknownResponse(trimmed)

            return when (parts[0]) {
                "id" -> parseIdResponse(parts)
                "uciok" -> UCIResponse.UciOkResponse()
                "readyok" -> UCIResponse.ReadyOkResponse()
                "bestmove" -> parseBestMoveResponse(parts)
                "info" -> parseInfoResponse(parts)
                "option" -> parseOptionResponse(parts)
                else -> UCIResponse.UnknownResponse(trimmed)
            }
        }

        private fun parseIdResponse(parts: List<String>): UCIResponse {
            if (parts.size < 3) return UCIResponse.UnknownResponse(parts.joinToString(" "))

            val type = parts[1]
            val value = parts.drop(2).joinToString(" ")

            return when (type) {
                "name" -> UCIResponse.IdResponse(name = value, author = "")
                "author" -> UCIResponse.IdResponse(name = "", author = value)
                else -> UCIResponse.UnknownResponse(parts.joinToString(" "))
            }
        }

        private fun parseBestMoveResponse(parts: List<String>): UCIResponse {
            if (parts.size < 2) return UCIResponse.UnknownResponse(parts.joinToString(" "))

            val move = Move.fromUci(parts[1])
            if (move == null) return UCIResponse.ErrorResponse("Invalid move in bestmove: ${parts[1]}")

            val ponder = if (parts.size >= 4 && parts[2] == "ponder") {
                Move.fromUci(parts[3])
            } else null

            return UCIResponse.BestMoveResponse(move, ponder)
        }

        private fun parseInfoResponse(parts: List<String>): UCIResponse {
            val info = parseInfoParameters(parts.drop(1))
            return UCIResponse.InfoResponse(info)
        }

        private fun parseInfoParameters(params: List<String>): EngineInfo {
            var depth: Int? = null
            var selectiveDepth: Int? = null
            var time: Long? = null
            var nodes: Long? = null
            var principalVariation: MutableList<Move> = mutableListOf()
            var multiPrincipalVariation: Int? = null
            var score: Score? = null
            var currentMove: Move? = null
            var currentMoveNumber: Int? = null
            var hashFull: Int? = null
            var nodesPerSecond: Long? = null
            var tableBaseHits: Long? = null
            var cpuLoad: Int? = null
            var string: String? = null
            var refutation: MutableList<Move> = mutableListOf()
            var currentLine: MutableList<Move> = mutableListOf()

            var i = 0
            while (i < params.size) {
                when (params[i]) {
                    "depth" -> {
                        depth = params.getOrNull(++i)?.toIntOrNull()
                    }
                    "seldepth" -> {
                        selectiveDepth = params.getOrNull(++i)?.toIntOrNull()
                    }
                    "time" -> {
                        time = params.getOrNull(++i)?.toLongOrNull()
                    }
                    "nodes" -> {
                        nodes = params.getOrNull(++i)?.toLongOrNull()
                    }
                    "pv" -> {
                        while (++i < params.size && params[i].length >= 4) {
                            Move.fromUci(params[i])?.let { principalVariation.add(it) }
                        }
                        i-- // Adjust for the while loop
                    }
                    "multipv" -> {
                        multiPrincipalVariation = params.getOrNull(++i)?.toIntOrNull()
                    }
                    "score" -> {
                        score = parseScore(params, i + 1)
                        i += when {
                            params.getOrNull(i + 1) == "mate" -> 2
                            params.getOrNull(i + 1) == "cp" -> 2
                            else -> 1
                        }
                    }
                    "currmove" -> {
                        currentMove = params.getOrNull(++i)?.let { Move.fromUci(it) }
                    }
                    "currmovenumber" -> {
                        currentMoveNumber = params.getOrNull(++i)?.toIntOrNull()
                    }
                    "hashfull" -> {
                        hashFull = params.getOrNull(++i)?.toIntOrNull()
                    }
                    "nps" -> {
                        nodesPerSecond = params.getOrNull(++i)?.toLongOrNull()
                    }
                    "tbhits" -> {
                        tableBaseHits = params.getOrNull(++i)?.toLongOrNull()
                    }
                    "cpuload" -> {
                        cpuLoad = params.getOrNull(++i)?.toIntOrNull()
                    }
                    "string" -> {
                        string = params.drop(i + 1).joinToString(" ")
                        break // String consumes the rest
                    }
                    "refutation" -> {
                        while (++i < params.size && params[i].length >= 4) {
                            Move.fromUci(params[i])?.let { refutation.add(it) }
                        }
                        i-- // Adjust for the while loop
                    }
                    "currline" -> {
                        // Skip multipv number if present
                        if (params.getOrNull(i + 1)?.toIntOrNull() != null) i++
                        while (++i < params.size && params[i].length >= 4) {
                            Move.fromUci(params[i])?.let { currentLine.add(it) }
                        }
                        i-- // Adjust for the while loop
                    }
                }
                i++
            }

            return EngineInfo(
                depth = depth,
                selectiveDepth = selectiveDepth,
                time = time,
                nodes = nodes,
                principalVariation = principalVariation,
                multiPrincipalVariation = multiPrincipalVariation,
                score = score,
                currentMove = currentMove,
                currentMoveNumber = currentMoveNumber,
                hashFull = hashFull,
                nodesPerSecond = nodesPerSecond,
                tableBaseHits = tableBaseHits,
                cpuLoad = cpuLoad,
                string = string,
                refutation = refutation,
                currentLine = currentLine
            )
        }

        private fun parseScore(params: List<String>, startIndex: Int): Score? {
            if (startIndex >= params.size) return null

            return when (params[startIndex]) {
                "cp" -> {
                    val cp = params.getOrNull(startIndex + 1)?.toIntOrNull()
                    val lowerBound = startIndex + 2 < params.size && params[startIndex + 2] == "lowerbound"
                    val upperBound = startIndex + 2 < params.size && params[startIndex + 2] == "upperbound"
                    Score(centipawns = cp, lowerBound = lowerBound, upperBound = upperBound)
                }
                "mate" -> {
                    val mate = params.getOrNull(startIndex + 1)?.toIntOrNull()
                    Score(mate = mate)
                }
                else -> null
            }
        }

        private fun parseOptionResponse(parts: List<String>): UCIResponse {
            val keywords = setOf("name", "type", "default", "min", "max", "var")
            fun readValue(startIndex: Int): Pair<String, Int> {
                if (startIndex >= parts.size) return "" to parts.size
                val values = mutableListOf<String>()
                var index = startIndex
                while (index < parts.size && parts[index] !in keywords) {
                    values.add(parts[index])
                    index++
                }
                return values.joinToString(" ") to index
            }

            var name = ""
            var type = OptionType.STRING
            var defaultValue: Any? = null
            var minValue: Any? = null
            var maxValue: Any? = null
            val optionValues = mutableListOf<String>()

            var i = 1
            while (i < parts.size) {
                when (parts[i]) {
                    "name" -> {
                        val (value, nextIndex) = readValue(i + 1)
                        name = value
                        i = nextIndex
                    }
                    "type" -> {
                        val rawType = parts.getOrNull(i + 1)
                        type = when (rawType?.lowercase()) {
                            "check" -> OptionType.CHECK
                            "spin" -> OptionType.SPIN
                            "combo" -> OptionType.COMBO
                            "button" -> OptionType.BUTTON
                            "string" -> OptionType.STRING
                            else -> OptionType.STRING
                        }
                        i += 2
                    }
                    "default" -> {
                        val (value, nextIndex) = readValue(i + 1)
                        defaultValue = when (type) {
                            OptionType.SPIN -> value.toIntOrNull() ?: value
                            OptionType.CHECK -> value.equals("true", ignoreCase = true)
                            else -> value
                        }
                        i = nextIndex
                    }
                    "min" -> {
                        val value = parts.getOrNull(i + 1)
                        minValue = value?.toIntOrNull() ?: value
                        i += 2
                    }
                    "max" -> {
                        val value = parts.getOrNull(i + 1)
                        maxValue = value?.toIntOrNull() ?: value
                        i += 2
                    }
                    "var" -> {
                        val (value, nextIndex) = readValue(i + 1)
                        if (value.isNotBlank()) {
                            optionValues.add(value)
                        }
                        i = nextIndex
                    }
                    else -> i++
                }
            }

            val option = UCIOption(
                name = name,
                type = type,
                default = defaultValue,
                min = minValue,
                max = maxValue,
                options = optionValues
            )
            return UCIResponse.OptionResponse(option)
        }

        // UCI Command builders

        fun uciCommand(): String = "uci"

        fun isReadyCommand(): String = "isready"

        fun newGameCommand(): String = "ucinewgame"

        fun positionCommand(fen: String? = null, moves: List<Move> = emptyList()): String {
            val position = fen ?: "startpos"
            val movesStr = if (moves.isNotEmpty()) {
                " moves " + moves.joinToString(" ") { it.uci }
            } else ""
            return "position $position$movesStr"
        }

        fun goCommand(
            searchMoves: List<Move>? = null,
            ponder: Boolean = false,
            whiteTime: Long? = null,
            blackTime: Long? = null,
            whiteIncrement: Long? = null,
            blackIncrement: Long? = null,
            movesToGo: Int? = null,
            depth: Int? = null,
            nodes: Long? = null,
            mate: Int? = null,
            moveTime: Long? = null,
            infinite: Boolean = false
        ): String {
            val params = mutableListOf<String>()

            searchMoves?.let { params.add("searchmoves ${it.joinToString(" ") { move -> move.uci }}") }
            if (ponder) params.add("ponder")
            whiteTime?.let { params.add("wtime $it") }
            blackTime?.let { params.add("btime $it") }
            whiteIncrement?.let { params.add("winc $it") }
            blackIncrement?.let { params.add("binc $it") }
            movesToGo?.let { params.add("movestogo $it") }
            depth?.let { params.add("depth $it") }
            nodes?.let { params.add("nodes $it") }
            mate?.let { params.add("mate $it") }
            moveTime?.let { params.add("movetime $it") }
            if (infinite) params.add("infinite")

            return "go ${params.joinToString(" ")}"
        }

        fun stopCommand(): String = "stop"

        fun ponderHitCommand(): String = "ponderhit"

        fun quitCommand(): String = "quit"

        fun setOptionCommand(name: String, value: Any): String {
            return "setoption name $name value $value"
        }

        // Utility methods

        /**
         * Check if a line indicates an error or unknown response
         */
        fun isErrorLine(line: String): Boolean {
            val trimmed = line.trim()
            return trimmed.isEmpty() ||
                   trimmed.startsWith("Unknown command") ||
                   trimmed.startsWith("Error") ||
                   trimmed.contains("error", ignoreCase = true)
        }

        /**
         * Extract engine name from ID responses
         */
        fun extractEngineName(responses: List<UCIResponse>): String {
            return responses.filterIsInstance<UCIResponse.IdResponse>()
                .firstOrNull { it.name.isNotEmpty() }
                ?.name ?: "Unknown Engine"
        }
    }
}
