package io.github.tirth132.connectfour

import java.lang.StringBuilder

const val DEFAULT_ROWS = 6
const val DEFAULT_COLUMNS = 6
const val MIN_ROWS = 5
const val MAX_ROWS = 9
const val MIN_COLUMNS = 5
const val MAX_COLUMNS = 9

class Game {
    private lateinit var firstPlayer: String
    private lateinit var secondPlayer: String
    private var columns: Int = DEFAULT_COLUMNS
    private var rows: Int = DEFAULT_ROWS
    private lateinit var board: List<MutableList<String>>

    fun init() {
        firstPlayer = readInput("First player's name")
        secondPlayer = readInput("Second player's name")

        val promptMsg = """
        Set the board dimensions (Rows x Columns)
        Press Enter for default ($DEFAULT_ROWS x $DEFAULT_COLUMNS)
        """.trimIndent()

        while (true) {
            val userInput = readInput(promptMsg).lowercase().replace("\\s+".toRegex(), "")

            if (Regex("(-?\\d+)x(-?\\d+)").matches(userInput)) {
                val (rows, columns) = userInput.split("x").map { it.toInt() }.toList()

                if (rows !in MIN_ROWS..MAX_ROWS) {
                    println("Board rows should be from $MIN_ROWS to $MAX_ROWS")
                    continue
                }
                if (columns !in MIN_COLUMNS..MAX_COLUMNS) {
                    println("Board columns should be from $MIN_COLUMNS to $MAX_COLUMNS")
                    continue
                }

                this.columns = columns
                this.rows = rows

                break
            } else if (userInput.isNotEmpty()) {
                println("Invalid input")
            } else {
                // Accept default columns and rows
                break
            }
        }
        board = List(columns) { mutableListOf() }      // Create empty game board(2D array)

        // Print new game details to standard output
        println("""
        $firstPlayer VS $secondPlayer
        $rows x $columns board
        """.trimIndent())
        printBoard()
    }

    fun start() {
        var firstPlayerTurn = true
        while (true) {
            val playerName = if (firstPlayerTurn) firstPlayer else secondPlayer
            val input = readInput("$playerName's turn")

            if (input == "end") {
                println("Game over!")
                break
            }

            if (input.toIntOrNull() == null) {
                println("Invalid column number")
                continue
            }

            val columnNumber = input.toInt()
            if (columnNumber !in 1..columns) {
                println("The column number is out of range (1 - $columns)")
                continue
            }

            if (board[columnNumber - 1].size >= rows) {
                println("Column $columnNumber is full")
                continue
            }

            board[columnNumber - 1].add(if (firstPlayerTurn) "o" else "*")
            printBoard()
            firstPlayerTurn = !firstPlayerTurn
        }
    }

    private fun printBoard() {
        val boardStr = StringBuilder()
        repeat(columns) { boardStr.append(" ${it + 1}") }
        boardStr.append("\n")

        repeat(rows) {
            boardStr.append("║")

            for (column in board) {
                val currentRowNumber = rows - it
                boardStr.append(
                    if (column.size >= currentRowNumber) {
                        // If column has disk in current row add it
                        column[currentRowNumber - 1]
                    } else {
                        // If current row of column is empty add space
                        " "
                    }
                ).append("║")
            }
            boardStr.append("\n")
        }

        boardStr.append("╚")
        repeat(columns - 1) { boardStr.append("═╩") }
        boardStr.append("═╝")

        println(boardStr.toString())
    }

    private fun readInput(prompt: String): String {
        print("$prompt\n> ")
        return readln()
    }
}
