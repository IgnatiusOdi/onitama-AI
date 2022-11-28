package com.cbengineer.onitamaai

data class GameState(
    val board : Array<Array<Piece?>>,
    val player: Player,
    val opponent: Player,
    val playerCards : ArrayList<Card>,
    val opponentCards : ArrayList<Card>,
    val nextCard : Card,
) {
    /**
     * Modified getValidMoves from GameEngine
     * @see GameEngine.getValidMoves
     * @author Kosmasu
     * @author Xander
     * @param from titik asal
     * @param player player yang melakukan move
     * @param card card yang digunakan
     * @return list semua move yang bisa dilakukan
     */
    fun getValidMoves(from: Point, player: Player, card: Card): ArrayList<Point> {
        val validMoves = arrayListOf<Point>()
        val moves = card.getMoves(from, player)

        for (move in moves) {
            if (move.x < 0 || move.x >= BOARD_SIZE || move.y < 0 || move.y >= BOARD_SIZE) continue
            if (
                board[move.y][move.x] != null &&
                board[move.y][move.x] is Piece &&
                board[move.y][move.x]!!.player == player
            ) continue
            validMoves.add(move)
        }
        return validMoves
    }

    /**
     * Modified move from GameEngine
     * @see GameEngine.move
     * @author Kosmasu
     * @author Xander
     * @param from titik asal
     * @param to titik tujuan
     */
    fun move(from: Point, to: Point) {
        board[to.y][to.x] = board[from.y][from.x]
        board[from.y][from.x] = null
    }

    fun printBoard() {
        for (row in this.board) {
            var str = ""
            for (col in row) {
                str += when (col) {
                    null -> " "
                    else -> col.toString()
                }
            }
            println(str)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (!board.contentDeepEquals(other.board)) return false
        if (player != other.player) return false
        if (opponent != other.opponent) return false
        if (nextCard != other.nextCard) return false

        return true
    }

    override fun hashCode(): Int {
        var result = board.contentDeepHashCode()
        result = 31 * result + player.hashCode()
        result = 31 * result + opponent.hashCode()
        result = 31 * result + nextCard.hashCode()
        return result
    }

//    /**
//     * @author Kosmasu
//     * @author Xander
//     * @return player1 jika turn ganjil, player2 jika turn genap
//     */
//    fun getPlayerBasedOnTurn(): Player {
//        return when (turn % 2) {
//            1 -> player1
//            else -> player2
//        }
//    }
//
//    /**
//     * @author Kosmasu
//     * @author Xander
//     * @return player2 jika turn ganjil, player1 jika turn genap
//     */
//    fun getOpponentBasedOnTurn(): Player {
//        return when (turn % 2) {
//            1 -> player2
//            else -> player1
//        }
//    }
}