package com.cbengineer.onitamaai

class Piece (
    val role: PieceRole, // "PAWN" atau "KING"
    val player: Player,
) {
    enum class PieceRole{
        PAWN,
        KING
    }
}

