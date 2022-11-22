package com.cbengineer.onitamaai

import android.graphics.drawable.Drawable

class Piece(
  val role: String, // "PAWN" atau "KING"
  val player: Player,
) {
  fun getDrawable(): Int {
    return if (role == "PAWN" && player.order == Player.ORDER_PLAYER1) R.drawable.pawn_blue
    else if (role == "PAWN" && player.order == Player.ORDER_PLAYER2) R.drawable.pawn_red
    else if (role == "KING" && player.order == Player.ORDER_PLAYER1) R.drawable.king_blue
    else R.drawable.king_red
  }

  override fun toString(): String {
    return when (role) {
      "PAWN" -> "P"
      "KING" -> "K"
      else -> ""
    }
  }
}