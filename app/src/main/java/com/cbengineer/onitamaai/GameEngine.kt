package com.cbengineer.onitamaai
const val BOARD_SIZE = 5

class GameEngine(
    player1: Player,
    player2: Player,
) {
    public val board = createBoard(player1, player2)
    private val PLAYER1_BASE: Point = Point(BOARD_SIZE/2, BOARD_SIZE-1)
    private val PLAYER2_BASE: Point = Point(BOARD_SIZE/2, 0)

    //player = player yang gerak
    //card = card yang dipake
    //from = titik saat ini
    fun getValidMoves(from: Point, player: Player, card: Card): ArrayList<Point> {
        val validMoves = arrayListOf<Point>()
        val moves = card.getMoves(from)

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

    fun move(from: Point, to: Point, player: Player, card: Card) {
        board[to.y][to.x] = board[from.y][from.x]
        Card.deck.add(card)
    }

    fun checkIfWin(player: Player): Boolean {
        var isEnemyKingDead = true
        for (row: Array<Piece?> in board) {
            for (col: Piece? in row) {
                if (col != null && col.role == "king" && col.player != player) {
                    isEnemyKingDead = false
                }
            }
        }
        //cek Player.kt
        //butuh cara untuk identifikasi player itu player satu atau dua
        var isEnemyBaseTaken = true
        return isEnemyKingDead || isEnemyBaseTaken
    }

    companion object {
        fun createBoard(player1 : Player, player2: Player) : Array<Array<Piece?>> {
            return arrayOf<Array<Piece?>>(
                //first row, player 2
                arrayOf<Piece?>(
                    Piece("pawn", player2),
                    Piece("pawn", player2),
                    Piece("king", player2),
                    Piece("pawn", player2),
                    Piece("pawn", player2)
                ),
                arrayOfNulls(5),
                arrayOfNulls(5),
                arrayOfNulls(5),
                //last row, player 1
                arrayOf<Piece?>(
                    Piece("pawn", player1),
                    Piece("pawn", player1),
                    Piece("king", player1),
                    Piece("pawn", player1),
                    Piece("pawn", player1)
                ),
            )
        }
    }
}