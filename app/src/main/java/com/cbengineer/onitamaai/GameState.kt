package com.cbengineer.onitamaai

data class GameState(
    val board : Array<Array<Piece?>>,
    val playerCards : ArrayList<Card>,
    val botCards : ArrayList<Card>,
    val nextCard : Card,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (!board.contentDeepEquals(other.board)) return false
        if (playerCards != other.playerCards) return false
        if (botCards != other.botCards) return false
        if (nextCard != other.nextCard) return false

        return true
    }

    override fun hashCode(): Int {
        var result = board.contentDeepHashCode()
        result = 31 * result + playerCards.hashCode()
        result = 31 * result + botCards.hashCode()
        result = 31 * result + nextCard.hashCode()
        return result
    }
}