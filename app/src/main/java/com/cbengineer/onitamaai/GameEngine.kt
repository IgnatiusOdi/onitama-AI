package com.cbengineer.onitamaai

const val BOARD_SIZE = 5

class GameEngine(
  val player1: Player,
  val player2: Player,
) {
  var board = createBoard(player1, player2)
  var nextCard = Card.randomCardFromDeck()
  var turn = 1

  constructor(
    player1: Player,
    player2: Player,
    board : Array<Array<Piece?>>,
    nextCard : Card
  ) : this(player1, player2) {
    this.board = board
    this.nextCard = nextCard
  }

  /**
   * turn++
   */
  fun endTurn() {
    turn++
  }

  /**
   * @return player1 jika turn ganjil, player2 jika turn genap
   */
  fun getPlayerBasedOnTurn(): Player {
    return when (turn % 2) {
      1 -> player1
      else -> player2
    }
  }

  /**
   * @return player2 jika turn ganjil, player1 jika turn genap
   */
  fun getOpponentBasedOnTurn(): Player {
    return when (turn % 2) {
      1 -> player2
      else -> player1
    }
  }

  /**
   * deck card player ditambah next card dan merandom nextCard dari deck
   * @param player card akan dimasukkan dalam deck player ini
   */
  fun putNextCardToPlayer(player: Player) {
    player.cards.add(
      nextCard
    )
//        nextCard = Card.randomCardFromDeck()
  }

  /**
   * @author Kosmasu
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
   * @author Kosmasu
   * @param from titik asal
   * @param to titik tujuan
   * @param player player yang melakukan move
   * @param card card yang digunakan
   */
  fun move(from: Point, to: Point) {
    board[to.y][to.x] = board[from.y][from.x]
    board[from.y][from.x] = null
  }

  /**
   * @param player player sekarang
   * @return true jika player menang, false jika tidak
   */
  fun checkIfWin(player: Player): Boolean {
    // cek apakah king musuh sudah mati
    var isEnemyKingDead = true
    for (row: Array<Piece?> in board) {
      for (col: Piece? in row) {
        if (col != null && col.role == Piece.PieceRole.KING && col.player != player) {
          isEnemyKingDead = false
        }
      }
    }
    // cek apakah piece yang ada di base musuh itu KING dan bukan milik player saat ini
    val basePoint = getEnemyBasePoint(player)
    val pieceAtBaseEnemyPlayer: Piece? = board[basePoint.y][basePoint.x]
    val isEnemyBaseTaken = pieceAtBaseEnemyPlayer != null &&
      pieceAtBaseEnemyPlayer.player == player &&
      pieceAtBaseEnemyPlayer.role == Piece.PieceRole.KING
    println("isEnemyKingDead = $isEnemyKingDead")
    println("isEnemyBaseTaken = $isEnemyBaseTaken")
    return isEnemyKingDead || isEnemyBaseTaken
  }

  /**
   * Discarding
   * If you have no moves that you can legally make, you instead will discard a card, playing it and not moving a pieces
   *
   * If you can play a move, you cannot discard
   * @param player Player to check
   * @return Boolean true/false
   */
  fun checkLegalMovesExist(player: Player): Boolean {
    var pieceCount: Int = 0
    for (card in player.cards) {
      for (i in 0 until board.size) {
        for (j in 0 until board[i].size) {
          val piece = board[i][j]
          if (piece != null && piece.player == player) {
            pieceCount++
            val validMoves = getValidMoves(Point(j, i), player, card)
            if (validMoves.isNotEmpty()) {
              return true
            }
          }
        }
      }
    }
    return pieceCount == 0
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

  companion object {
    val PLAYER1_BASE: Point = Point(BOARD_SIZE / 2, BOARD_SIZE - 1)
    val PLAYER2_BASE: Point = Point(BOARD_SIZE / 2, 0)

    fun getEnemyBasePoint(player: Player): Point {
      return when (player.order) {
        Player.ORDER_PLAYER1 -> PLAYER2_BASE
        Player.ORDER_PLAYER2 -> PLAYER1_BASE
        else -> Point(-1, -1)
      }
    }

    fun createBoard(player1: Player, player2: Player): Array<Array<Piece?>> {
      return arrayOf<Array<Piece?>>(
        //first row, player 2
        arrayOf<Piece?>(
          Piece(Piece.PieceRole.PAWN, player2),
          Piece(Piece.PieceRole.PAWN, player2),
          Piece(Piece.PieceRole.KING, player2),
          Piece(Piece.PieceRole.PAWN, player2),
          Piece(Piece.PieceRole.PAWN, player2)
        ),
        arrayOfNulls(5),
        arrayOfNulls(5),
        arrayOfNulls(5),
        //last row, player 1
        arrayOf<Piece?>(
          Piece(Piece.PieceRole.PAWN, player1),
          Piece(Piece.PieceRole.PAWN, player1),
          Piece(Piece.PieceRole.KING, player1),
          Piece(Piece.PieceRole.PAWN, player1),
          Piece(Piece.PieceRole.PAWN, player1)
        ),
      )
    }

    fun clone(other: GameEngine): GameEngine {
      return GameEngine(
        other.player1,
        other.player2,
        other.board,
        other.nextCard
      ).apply {
        this.turn = other.turn
      }
    }
  }
}