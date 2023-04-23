package com.example.tictactoe_pd1_mc.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe_pd1_mc.R
import kotlin.math.round

class GameActivity : AppCompatActivity(){

    private var player1Turn = true
    private var roundCount = 0
    private var player1score = 0
    private var player2score = 0
    var name1 = "Player 1"
    var name2 = "Player 2"
    private lateinit var buttons: Array<Array<Button?>>
    var pvp = false
    val board= Array(3) { arrayOfNulls<String>(3) }
    var bestscore = -100000
    var bestMove = arrayOf(0, 0)
    var move = arrayOf(0, 0)

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        super.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_main_game)

        val extras = intent.extras

        if (extras != null) {
            name1 = extras.getString("name1").toString()
            name2 = extras.getString("name2").toString()
            val gamestate = extras.getString("gamestate")
            pvp = gamestate.toBoolean()
            val name1text = findViewById<View>(R.id.player1name) as TextView
            name1text.setText(name1)
            val name2text = findViewById<View>(R.id.player2name) as TextView
            name2text.setText(name2)
        }




        buttons = arrayOf(
            arrayOf(
                findViewById<Button>(R.id.button1),
                findViewById<Button>(R.id.button2),
                findViewById<Button>(R.id.button3)
            ),
            arrayOf(
                findViewById<Button>(R.id.button4),
                findViewById<Button>(R.id.button5),
                findViewById<Button>(R.id.button6)
            ),
            arrayOf(
                findViewById<Button>(R.id.button7),
                findViewById<Button>(R.id.button8),
                findViewById<Button>(R.id.button9)
            )
        )


        if (pvp) {
            for (i in buttons.indices) {
                for (j in buttons.indices) {
                    buttons[i][j]?.setOnClickListener {
                        Log.i("Button Pressed", "Button Pressed")
                        if (player1Turn && buttons[i][j]?.text?.isNotEmpty() == false) {
                            buttons[i][j]?.text = "X"
                            board[i][j] = "x"
                            roundCount++
                            player1Turn = !player1Turn
                            Log.i("Gameplay loop", "X pressed")
                        } else if (!player1Turn && buttons[i][j]?.text?.isNotEmpty() == false) {
                            buttons[i][j]?.text = "O"
                            board[i][j] = "o"
                            roundCount++
                            player1Turn = !player1Turn
                            Log.i("Gameplay loop", "O pressed")
                        }

                        if (checkForWin(board)) {
                            if (player1Turn) {
                                player2Wins()
                                Log.i("Winning", "Player 1 Won")
                            } else {
                                player1Wins()
                                Log.i("Winning", "Player 1 Won")
                            }
                        } else if (roundCount == 9) {
                            draw()
                            Log.i("Winning", "Tie")
                        }
                    }
                }
            }
        } else {
            if (player1Turn){
            // Player 1's turn
            for (i in board.indices) {
                for (j in board.indices) {
                    // Attach a click listener to each Button view
                    buttons[i][j]?.setOnClickListener {
                        // Check if the Button is empty and update the board and Button text accordingly
                        if (board[i][j] == null) {
                            board[i][j] = "x"
                            buttons[i][j]?.text = "X"
                            player1Turn = false
                            // Check for a win or draw after each move
                            if (checkForWin(board)) {
                                player2Wins()
                                Log.i("Winning", "Player 1 Won")
                            } else if (roundCount == 9) {
                                draw()
                                Log.i("Winning", "Tie")
                            } else {
                                // If no win or draw, call the AI's move function
                                aiMove(board, buttons)
                            }
                        }
                    }
                }
            }
        } else {
            // AI's turn, call the minimax function to find the best move
            aiMove(board, buttons)
        }}
        /*     for (i in board.indices) {
                for (j in board.indices) {
                    buttons[i][j]?.setOnClickListener {



                    }
                }
            }
            }
            for (i in board.indices) {
                for (j in board.indices) {
                    if (!player1Turn && buttons[i][j]?.text?.isNotEmpty() == false) {
                        if (board[i][j] == null) {
                            board[i][j] = "o"
                            var score = minimax(board, 0, true)
                            board[i][j] = null
                            if (score > bestscore) {
                                bestscore = score
                                bestMove[0] = i
                                bestMove[1] = j
                            }
                        }
                        buttons[bestMove[0]][bestMove[1]]?.text = "O"
                        board[bestMove[0]][bestMove[1]] = "o"
                    }

                    if (checkForWin(board)) {
                        if (player1Turn) {
                            player1Wins()
                            Log.i("Winning", "Player 1 Won")
                        } else {
                            player2Wins()
                            Log.i("Winning", "Player 1 Won")
                        }
                    } else if (roundCount == 9) {
                        draw()
                        Log.i("Winning", "Tie")
                    }

                }
            }


    }

    */ }

// main function ends


    fun aiMove(board: Array<Array<String?>>, buttons: Array<Array<Button?>>) {
        var bestscore = Int.MIN_VALUE
        var bestMove = intArrayOf(0, 0)
        for (i in board.indices) {
            for (j in board.indices) {
                if (!player1Turn && buttons[i][j]?.text?.isNotEmpty() == false) {
                    if (board[i][j] == null) {
                        board[i][j] = "o"
                        var score = minimax(board, 0, true, roundCount)
                        board[i][j] = null
                        if (score > bestscore) {
                            bestscore = score
                            Log.i("BestScore in BestMove", "Score is: " + bestscore.toString())
                            bestMove[0] = i
                            bestMove[1] = j
                        }
                    }
                }
            }
        }
        // Update the board and Button view with the AI's move
        buttons[bestMove[0]][bestMove[1]]?.text = "O"
        board[bestMove[0]][bestMove[1]] = "o"
        player1Turn = true
        // Check for a win or draw after the AI's move
        if (checkForWin(board)) {
            player1Wins()
            Log.i("Winning", "Player 2 Won")
        } else if (roundCount == 9) {
            draw()
            Log.i("Winning", "Tie")
        }
    }

    fun minimax(board: Array<Array<String?>>, depth: Int, isMaximizingPlayer: Boolean, roundcount: Int): Int {
        // Evaluate the current state of the board
        if (checkForWin(board)) {
            if (!isMaximizingPlayer) {
                return 1
            } else {
                return -1
            }
        }
        if (roundcount == 9){
            return 0
        }


        if (depth == 100) {
            return 0
        }

        if (isMaximizingPlayer) {
            var bestScore = Int.MIN_VALUE
            for (i in 0..2) {
                for (j in 0..2) {
                    if (board[i][j] == null) {
                        board[i][j] = "o"
                        val score = minimax(board, depth + 1, false, roundcount + 1)
                        board[i][j] = null
                        bestScore = maxOf(bestScore, score)
                    }
                }
            }
            return bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (i in 0..2) {
                for (j in 0..2) {
                    if (board[i][j] == null) {
                        board[i][j] = "x"
                        val score = minimax(board, depth + 1, true, roundcount + 1)

                        board[i][j] = null
                        bestScore = minOf(bestScore, score)
                        Log.i("TEST IN MINING", "Score is"+ score.toString() + "  Bestscore is " + bestScore.toString())
                    }
                }
            }
            return bestScore
        }
    }

    private fun checkForWin(board: Array<Array<String?>>): Boolean {
        // Check rows
        for (i in board.indices) {
            if (board[i][0] == board[i][1]
                && board[i][0] == board[i][2]
                && board[i][0] != null
            ) {
                return true
            }
        }

        // Check columns
        for (i in board.indices) {
            if (board[0][i] == board[1][i]
                && board[0][i] == board[2][i]
                && board[0][i] != null
            ) {
                return true
            }
        }

        // Check diagonal
        if (board[0][0] == board[1][1]
            && board[0][0] == board[2][2]
            && board[0][0] != null
        ) {
            return true
        }

        // Check reverse diagonal
        if (board[0][2] == board[1][1]
            && board[0][2] == board[2][0]
            && board[0][2] != null
        ) {
            return true
        }

        return false
    }

    private fun player1Wins() {
        Toast.makeText(this, (name2.toString() + " wins!"), Toast.LENGTH_SHORT).show()
        player2score += 1
        val player1scoreboard = findViewById<View>(R.id.player2score) as TextView
        player1scoreboard.setText(player2score.toString())
        resetGame()
    }

    private fun player2Wins() {
        Toast.makeText(this, (name1.toString() + " wins!"), Toast.LENGTH_SHORT).show()
        player1score += 1
        val player2scoreboard = findViewById<View>(R.id.player1score) as TextView
        player2scoreboard.setText(player1score.toString())
        resetGame()
    }

    private fun draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
        resetGame()
    }

    private fun resetGame() {
        roundCount = 0
        player1Turn = true

        for (i in buttons.indices) {
            for (j in buttons.indices) {
                buttons[i][j]?.text = ""
                board[i][j] = null
            }

        }
    }

}

