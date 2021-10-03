package com.backjeff.tictactoe

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.ImageView
import android.widget.Toast
import com.backjeff.tictactoe.databinding.ActivityMainBinding
import com.backjeff.tictactoe.model.Board
import com.backjeff.tictactoe.model.Cell
import com.backjeff.tictactoe.model.Player

class MainActivity : WearableActivity() {

    private lateinit var binding: ActivityMainBinding

    var board: Board = getFreshBoard()

    private fun getFreshBoard() = Board(
        size = 3,
        victoryLength = 3,
        rows = listOf(
            mutableListOf(null, null, null),
            mutableListOf(null, null, null),
            mutableListOf(null, null, null),
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enables Always-on
        setAmbientEnabled()

        setupViews()
    }

    private fun verifyCurrentState() {
        board.calculateWinner().let { winner ->
            if (winner != null) {
                Toast.makeText(this, "$winner won", Toast.LENGTH_SHORT).show()
//                newGame()
            } else if (board.isFull()) {
                Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()
//                newGame()
            }
        }
    }

    private fun newGame() {
        board = getFreshBoard()
        printBoard(board)
    }

    private fun enemyMove() {
        Minimax.getBestMove(
            board = board,
            forPlayer = Player.O
        )?.let { bestMove ->
            board.setCell(
                cell = bestMove,
                player = Player.O
            )
        }
        printBoard(board)

        verifyCurrentState()
    }

    private fun setupViews() {
        binding.slot1.setOnClickListener {
            playMove(Player.X, 0 to 0)
        }

        binding.slot2.setOnClickListener {
            playMove(Player.X, 0 to 1)
        }

        binding.slot3.setOnClickListener {
            playMove(Player.X, 0 to 2)
        }

        binding.slot4.setOnClickListener {
            playMove(Player.X, 1 to 0)
        }

        binding.slot5.setOnClickListener {
            playMove(Player.X, 1 to 1)
        }

        binding.slot6.setOnClickListener {
            playMove(Player.X, 1 to 2)
        }

        binding.slot7.setOnClickListener {
            playMove(Player.X, 2 to 0)
        }

        binding.slot8.setOnClickListener {
            playMove(Player.X, 2 to 1)
        }

        binding.slot9.setOnClickListener {
            playMove(Player.X, 2 to 2)
        }
    }

    private fun printBoard(board: Board) {
        with(board) {
            setSlotImage(getCell(0, 0), binding.slot1)
            setSlotImage(getCell(0, 1), binding.slot2)
            setSlotImage(getCell(0, 2), binding.slot3)
            setSlotImage(getCell(1, 0), binding.slot4)
            setSlotImage(getCell(1, 1), binding.slot5)
            setSlotImage(getCell(1, 2), binding.slot6)
            setSlotImage(getCell(2, 0), binding.slot7)
            setSlotImage(getCell(2, 1), binding.slot8)
            setSlotImage(getCell(2, 2), binding.slot9)
        }
    }

    private fun setSlotImage(value: Player?, slot: ImageView) {
        slot.setImageDrawable(
            when (value) {
                Player.X -> resources.getDrawable(R.drawable.x, null)
                Player.O -> resources.getDrawable(R.drawable.o, null)
                else -> null
            }
        )
    }

    private fun playMove(player: Player, cell: Cell) {
        if (board.setCell(cell, player)) {
            enemyMove()
        } else {
            Toast.makeText(this, "Invalid move", Toast.LENGTH_SHORT).show()
        }
    }
}
