package com.backjeff.tictactoe

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.ImageView
import android.widget.Toast
import com.backjeff.tictactoe.databinding.ActivityGameBinding
import com.backjeff.tictactoe.model.Board
import com.backjeff.tictactoe.model.Cell
import com.backjeff.tictactoe.model.GameMode
import com.backjeff.tictactoe.model.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameActivity : WearableActivity() {

    private lateinit var binding: ActivityGameBinding

    var board: Board = getFreshBoard()

    private val gameMode: GameMode? by lazy {
        val mode = intent?.extras?.getString("GAME_MODE")
        if (mode != null) {
            GameMode.valueOf(mode)
        } else null
    }

    private var currentPlayer: Player = Player.X

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
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enables Always-on
        setAmbientEnabled()

        setupViews()
    }

    private fun verifyCurrentState() {
        board.calculateWinner().let { winner ->
            if (winner != null) {
                Toast.makeText(this, "$winner won", Toast.LENGTH_SHORT).show()
                newGame()
            } else if (board.isFull()) {
                Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()
                newGame()
            }
        }
    }

    private fun newGame() {
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            finish()
        }
//        board = getFreshBoard()
//        printBoard(board)
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

    private fun nextPlayerMove() {
        currentPlayer = getNextPlayer(currentPlayer)
        printBoard(board)
        verifyCurrentState()
    }

    private fun setupViews() {
        binding.slot1.setOnClickListener {
            playMove(currentPlayer, 0 to 0)
        }

        binding.slot2.setOnClickListener {
            playMove(currentPlayer, 0 to 1)
        }

        binding.slot3.setOnClickListener {
            playMove(currentPlayer, 0 to 2)
        }

        binding.slot4.setOnClickListener {
            playMove(currentPlayer, 1 to 0)
        }

        binding.slot5.setOnClickListener {
            playMove(currentPlayer, 1 to 1)
        }

        binding.slot6.setOnClickListener {
            playMove(currentPlayer, 1 to 2)
        }

        binding.slot7.setOnClickListener {
            playMove(currentPlayer, 2 to 0)
        }

        binding.slot8.setOnClickListener {
            playMove(currentPlayer, 2 to 1)
        }

        binding.slot9.setOnClickListener {
            playMove(currentPlayer, 2 to 2)
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
            if (gameMode == GameMode.HUMAN_VS_AI) {
                printBoard(board)
                enemyMove()
            } else {
                nextPlayerMove()
            }
        } else {
            Toast.makeText(this, "Invalid move", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getNextPlayer(player: Player) = when (player) {
        Player.X -> Player.O
        Player.O -> Player.X
    }
}
