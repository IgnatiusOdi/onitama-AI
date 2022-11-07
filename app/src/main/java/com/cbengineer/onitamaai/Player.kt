package com.cbengineer.onitamaai

class Player (
    val nama: String,
    val order: Int,
) {
    var cards: ArrayList<Card> = ArrayList()
    init {
        cards.add(Card.randomCardFromDeck())
        cards.add(Card.randomCardFromDeck())
    }

    companion object {
        val ORDER_PLAYER1 = 1
        val ORDER_PLAYER2 = 2
    }
}