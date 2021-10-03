package com.backjeff.tictactoe

import io.kotest.matchers.shouldBe
import org.junit.Test

class TicTacToeTest {

    @Test
    fun `playMove`() {
        // GIVEN
        val ticTacToe = TicTacToe()

        // WHEN
        val result = ticTacToe.playMove(
            gameState = getBlankGameState(),
            player = "x",
            slot = 5
        )

        // THEN
        result shouldBe mutableListOf(
            mutableListOf("", "", ""),
            mutableListOf("", "x", ""),
            mutableListOf("", "", ""),
        )
    }

    @Test
    fun `playMove 2`() {
        // GIVEN
        val ticTacToe = TicTacToe()

        // WHEN
        val result = ticTacToe.playMove(
            gameState = getBlankGameState().apply { this[1][1] = "x" },
            player = "x",
            slot = 5
        )

        // THEN
        result shouldBe null
    }

    @Test
    fun `getBestMove`() {
        // GIVEN
        val ticTacToe = TicTacToe()

        // WHEN
        val result = ticTacToe.getBestMove(
            gameState = mutableListOf(
                mutableListOf("x", "", "o"),
                mutableListOf("", "x", ""),
                mutableListOf("x", "", "o"),
            ),
            player = "o"
        )

        // THEN
        result shouldBe 6
    }

    @Test
    fun `checkCurrentState1`() {
        // GIVEN
        val ticTacToe = TicTacToe()

        // WHEN
        val result = ticTacToe.checkCurrentState(
            gameState = mutableListOf(
                mutableListOf("x", "", ""),
                mutableListOf("x", "", ""),
                mutableListOf("x", "", ""),
            )
        )

        // THEN
        result.first shouldBe "x"
        result.second shouldBe "Done"
    }

    @Test
    fun `checkCurrentState11`() {
        // GIVEN
        val ticTacToe = TicTacToe()

        // WHEN
        val result = ticTacToe.checkCurrentState(
            gameState = mutableListOf(
                mutableListOf("", "x", ""),
                mutableListOf("", "x", ""),
                mutableListOf("", "x", ""),
            )
        )

        // THEN
        result.first shouldBe "x"
        result.second shouldBe "Done"
    }

    @Test
    fun `checkCurrentState12`() {
        // GIVEN
        val ticTacToe = TicTacToe()

        // WHEN
        val result = ticTacToe.checkCurrentState(
            gameState = mutableListOf(
                mutableListOf("", "", "x"),
                mutableListOf("", "", "x"),
                mutableListOf("", "", "x"),
            )
        )

        // THEN
        result.first shouldBe "x"
        result.second shouldBe "Done"
    }

    @Test
    fun `checkCurrentState2`() {
        // GIVEN
        val ticTacToe = TicTacToe()

        // WHEN
        val result = ticTacToe.checkCurrentState(
            gameState = mutableListOf(
                mutableListOf("x", "x", "x"),
                mutableListOf("", "", ""),
                mutableListOf("", "", ""),
            )
        )

        // THEN
        result.first shouldBe "x"
        result.second shouldBe "Done"
    }

    @Test
    fun `checkCurrentState21`() {
        // GIVEN
        val ticTacToe = TicTacToe()

        // WHEN
        val result = ticTacToe.checkCurrentState(
            gameState = mutableListOf(
                mutableListOf("", "", ""),
                mutableListOf("x", "x", "x"),
                mutableListOf("", "", ""),
            )
        )

        // THEN
        result.first shouldBe "x"
        result.second shouldBe "Done"
    }

    @Test
    fun `checkCurrentState22`() {
        // GIVEN
        val ticTacToe = TicTacToe()

        // WHEN
        val result = ticTacToe.checkCurrentState(
            gameState = mutableListOf(
                mutableListOf("", "", ""),
                mutableListOf("", "", ""),
                mutableListOf("x", "x", "x"),
            )
        )

        // THEN
        result.first shouldBe "x"
        result.second shouldBe "Done"
    }

    @Test
    fun `checkCurrentState3`() {
        // GIVEN
        val ticTacToe = TicTacToe()

        // WHEN
        val result = ticTacToe.checkCurrentState(
            gameState = mutableListOf(
                mutableListOf("x", "", ""),
                mutableListOf("", "x", ""),
                mutableListOf("", "", "x"),
            )
        )

        // THEN
        result.first shouldBe "x"
        result.second shouldBe "Done"
    }

    @Test
    fun `checkCurrentState4`() {
        // GIVEN
        val ticTacToe = TicTacToe()

        // WHEN
        val result = ticTacToe.checkCurrentState(
            gameState = mutableListOf(
                mutableListOf("", "", "x"),
                mutableListOf("", "x", ""),
                mutableListOf("x", "", ""),
            )
        )

        // THEN
        result.first shouldBe "x"
        result.second shouldBe "Done"
    }

    @Test
    fun `checkCurrentState5`() {
        // GIVEN
        val ticTacToe = TicTacToe()

        // WHEN
        val result = ticTacToe.checkCurrentState(
            gameState = mutableListOf(
                mutableListOf("x", "o", "x"),
                mutableListOf("o", "x", "o"),
                mutableListOf("o", "x", "o"),
            )
        )

        // THEN
        result.first shouldBe ""
        result.second shouldBe "Draw"
    }

    private fun getBlankGameState() = mutableListOf(
        mutableListOf("", "", ""),
        mutableListOf("", "", ""),
        mutableListOf("", "", ""),
    )
}
