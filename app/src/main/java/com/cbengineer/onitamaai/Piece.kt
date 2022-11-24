package com.cbengineer.onitamaai

class Piece(
  val role: PieceRole, // "PAWN" atau "KING"
  val player: Player,
) {
  fun getDrawable(): Int {
    return if (role == PieceRole.PAWN && player.order == Player.ORDER_PLAYER1) R.drawable.pawn_blue
    else if (role == PieceRole.PAWN && player.order == Player.ORDER_PLAYER2) R.drawable.pawn_red
    else if (role == PieceRole.KING && player.order == Player.ORDER_PLAYER1) R.drawable.king_blue
    else R.drawable.king_red
  }

  override fun toString(): String {
    return when (role) {
      PieceRole.PAWN -> "P"
      PieceRole.KING -> "K"
    }
  }

  enum class PieceRole{
    PAWN,
    KING
  }
}