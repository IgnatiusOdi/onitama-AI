package com.cbengineer.onitamaai

data class GameState(
    val board : Array<Array<Piece?>>,
    val playerCards : ArrayList<Card>,
    val opponentCards : ArrayList<Card>,
    val nextCard : Card,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (!board.contentDeepEquals(other.board)) return false
        if (playerCards != other.playerCards) return false
        if (opponentCards != other.opponentCards) return false
        if (nextCard != other.nextCard) return false

        return true
    }

    override fun hashCode(): Int {
        var result = board.contentDeepHashCode()
        result = 31 * result + playerCards.hashCode()
        result = 31 * result + opponentCards.hashCode()
        result = 31 * result + nextCard.hashCode()
        return result
    }

    /**
     * Modified getValidMoves from GameEngine
     * @see GameEngine.getValidMoves
     * @author Kosmasu, Xander
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
     * @author Kosmasu, Xander
     * @param from titik asal
     * @param to titik tujuan
     * @param player player yang melakukan move
     * @param card card yang digunakan
     */
    fun move(from: Point, to: Point) {
        board[to.y][to.x] = board[from.y][from.x]
        board[from.y][from.x] = null
    }
}