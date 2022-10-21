package io.github.tirth132.connectfour

fun main() {
    println("Connect Four")
    while (true) {
        print(
            """
                ${"*".repeat(20)}
                Select action
                1 - start a new game
                default - exit
                > 
           """.trimIndent()
        )

        when (readln()) {
            "1" -> {
                val game = Game()
                game.init()
                game.start()
            }

            "" -> return
            else -> println("Not a supported action")
        }
    }
}
