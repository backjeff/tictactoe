package com.backjeff.tictactoe

data class Move(
    var slot: Int = 0,
    var score: Int? = 0
)

class TicTacToe {

    lateinit var gameState: MutableList<MutableList<String>>

    fun startGame() {
        gameState = createEmptyBoard()
    }

    fun playMove(
        gameState: MutableList<MutableList<String>>,
        player: String,
        slot: Int
    ): MutableList<MutableList<String>>? {
        return if (gameState[(slot - 1) / 3][(slot - 1) % 3] == "") {
            gameState[(slot - 1) / 3][(slot - 1) % 3] = player
            gameState
        } else {
            null
        }
    }

    fun getBestMove(gameState: MutableList<MutableList<String>>, player: String): Int? {
        /**
         * Minimax Algorithm
         */

        printBoard(gameState)

        val (winner, gameResult) = checkCurrentState(gameState)

        if (gameResult == "Done" && winner == "o") { // If AI won
            println("o won")
            println()
            println()
            return 1
        } else if (gameResult == "Done" && winner == "x") { // If Human won
            println("x won")
            println()
            println()
            return -1
        } else if (gameResult == "Draw") { // Draw condition
            println("draw")
            println()
            println()
            return 0
        }

        val moves = mutableListOf<Move>()
        val emptyCells = getEmptyCells(gameState)

        emptyCells.forEach { emptyCell ->
            val move = Move(slot = emptyCell)
            var newGameState = gameState.toMutableList()

            newGameState = playMove(newGameState, player, emptyCell)!!

            if (player == "o") { // if is AI
                move.score = getBestMove(newGameState, "x")
            } else {
                move.score = getBestMove(newGameState, "o")
            }

            moves.add(move)
        }

        println()
        println(moves)
        println()

        var bestMove: Move? = null
        if (player == "o") { // if is AI
            var bestScore = -99999999
            moves.forEach { move ->
                move.score?.let {
                    if (it > bestScore) {
                        bestScore = it
                        bestMove = move
                    }
                }
            }
        } else {
            var bestScore = 99999999
            moves.forEach { move ->
                move.score?.let {
                    if (it < bestScore) {
                        bestScore = it
                        bestMove = move
                    }
                }
            }
        }

        println("slot: ${bestMove?.slot} score: ${bestMove?.score} player: $player")
        println()
        println()

        return bestMove?.slot
    }

    fun checkCurrentState(gameState: MutableList<MutableList<String>>): Pair<String, String> {
        // Check horizontals

        if (gameState[0][0] == gameState[0][1] && gameState[0][1] == gameState[0][2] && gameState[0][0] != "")
            return gameState[0][0] to "Done"
        if (gameState[1][0] == gameState[1][1] && gameState[1][1] == gameState[1][2] && gameState[1][0] != "")
            return gameState[1][0] to "Done"
        if (gameState[2][0] == gameState[2][1] && gameState[2][1] == gameState[2][2] && gameState[2][0] != "")
            return gameState[2][0] to "Done"

        // Check verticals
        if (gameState[0][0] == gameState[1][0] && gameState[1][0] == gameState[2][0] && gameState[0][0] != "")
            return gameState[0][0] to "Done"
        if (gameState[0][1] == gameState[1][1] && gameState[1][1] == gameState[2][1] && gameState[0][1] != "")
            return gameState[0][1] to "Done"
        if (gameState[0][2] == gameState[1][2] && gameState[1][2] == gameState[2][2] && gameState[0][2] != "")
            return gameState[0][2] to "Done"

        // Check diagonals
        if (gameState[0][0] == gameState[1][1] && gameState[1][1] == gameState[2][2] && gameState[0][0] != "")
            return gameState[1][1] to "Done"
        if (gameState[2][0] == gameState[1][1] && gameState[1][1] == gameState[0][2] && gameState[2][0] != "")
            return gameState[1][1] to "Done"

        // Check if draw
        var drawFlag = 0
        for (i in 0..2) {
            for (j in 0..2) {
                if (gameState[i][j] == "") {
                    drawFlag = 1
                }
            }
        }
        if (drawFlag == 0) return "" to "Draw"

        return "" to "Not Done"
    }

    fun getEmptyCells(gameState: MutableList<MutableList<String>>): MutableList<Int> {
        val emptyCells = mutableListOf<Int>()
        for (i in 0..2) {
            for (j in 0..2) {
                if (gameState[i][j] == "") {
                    emptyCells.add(i * 3 + (j + 1))
                }
            }
        }
        return emptyCells
    }

    fun printBoard(gameState: MutableList<MutableList<String>>) {
        println("--------------")
        println("| " + gameState[0][0] + " || " + gameState[0][1] + " || " + gameState[0][2] + " |")
        println("--------------")
        println("| " + gameState[1][0] + " || " + gameState[1][1] + " || " + gameState[1][2] + " |")
        println("--------------")
        println("| " + gameState[2][0] + " || " + gameState[2][1] + " || " + gameState[2][2] + " |")
        println("--------------")
        println()
    }

    private fun createEmptyBoard() = mutableListOf(
        mutableListOf("", "", ""),
        mutableListOf("", "", ""),
        mutableListOf("", "", ""),
    )
}
