package com.cbengineer.onitamaai

class Player (
    nama: String,
    order: Int,
) {
    lateinit var cards: ArrayList<Card>
//    lateinit var
    //tambahkan cara agar player bisa diidentifikasi
    //player satu atau player dua?
    //untuk pembeda card nya
    //karena card di palyer dua itu di balik 180 derajat
    init {
        cards.add(Card.randomCardFromDeck())
        cards.add(Card.randomCardFromDeck())

    }
}