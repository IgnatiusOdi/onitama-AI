package com.cbengineer.onitamaai

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class PlayerActivity : AppCompatActivity() {

    lateinit var tvNextCard: TextView
    lateinit var tvPlayer1: TextView
    lateinit var tvPlayer2: TextView
    lateinit var moveNext: ImageButton
    lateinit var movePlayer1_1: ImageButton
    lateinit var movePlayer1_2: ImageButton
    lateinit var movePlayer2_1: ImageButton
    lateinit var movePlayer2_2: ImageButton
    var tiles: Array<Array<ImageButton>> = arrayOf()

    lateinit var nextCard: Card
    lateinit var player1: Player
    lateinit var player2: Player
    lateinit var game: GameEngine

    private var cardSelected: Card? = null
    private var tileSelected: Point? = null
    private var ibSelected: ImageButton? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        tvNextCard = findViewById(R.id.tvNextCard)
        tvPlayer1 = findViewById(R.id.tvPlayer1)
        tvPlayer2 = findViewById(R.id.tvPlayer2)
        moveNext = findViewById(R.id.moveNext)
        movePlayer1_1 = findViewById(R.id.movePlayer1_1)
        movePlayer1_2 = findViewById(R.id.movePlayer1_2)
        movePlayer2_1 = findViewById(R.id.movePlayer2_1)
        movePlayer2_2 = findViewById(R.id.movePlayer2_2)
        tiles = arrayOf(
            arrayOf(findViewById(R.id.tile01), findViewById(R.id.tile02), findViewById(R.id.tile03), findViewById(R.id.tile04), findViewById(R.id.tile05)),
            arrayOf(findViewById(R.id.tile06), findViewById(R.id.tile07), findViewById(R.id.tile08), findViewById(R.id.tile09), findViewById(R.id.tile10)),
            arrayOf(findViewById(R.id.tile11), findViewById(R.id.tile12), findViewById(R.id.tile13), findViewById(R.id.tile14), findViewById(R.id.tile15)),
            arrayOf(findViewById(R.id.tile16), findViewById(R.id.tile17), findViewById(R.id.tile18), findViewById(R.id.tile19), findViewById(R.id.tile20)),
            arrayOf(findViewById(R.id.tile21), findViewById(R.id.tile22), findViewById(R.id.tile23), findViewById(R.id.tile24), findViewById(R.id.tile25)),
        )

        // REFILL CARD DECK
        Card.deck = Card.getAllCard()

        player1 = Player("PLAYER 1", Player.ORDER_PLAYER1)
        player2 = Player("PLAYER 2", Player.ORDER_PLAYER2)
        game = GameEngine(player1, player2)

        // PLAYER 1 CARD
        changeCard(movePlayer1_1, player1.cards[0].nama)
        changeCard(movePlayer1_2, player1.cards[1].nama)

        // PLAYER 2 CARD
        changeCard(movePlayer2_1, player2.cards[0].nama)
        changeCard(movePlayer2_2, player2.cards[1].nama)

        tvPlayer2.rotation = 180f
        movePlayer2_1.rotation = 180f
        movePlayer2_2.rotation = 180f

        // NEXT CARD
        nextCard = game.nextCard
        changeCard(moveNext, nextCard.nama)
        tvNextCard.text = "NEXT CARD\n${moveNext.tag.toString().uppercase()}"

        for (i in 0 until tiles.size) {
            for (j in 0 until tiles[i].size) {
                tiles[i][j].tag = game.board[i][j]
                tiles[i][j].setOnClickListener {
                    if (game.board[i][j] != null) {
                        val piece = game.board[i][j]
                        if (game.board[i][j]!!.player == game.getPlayerBasedOnTurn()) {
                            piece?.let {
                                changeTileSelected(Point(j, i))
                                println(cardSelected)
                            }
                        }
                    }
                }
            }
        }

        movePlayer1_1.setOnClickListener {
            if (game.turn == 1) {
                cardSelected = player1.cards[0]
                ibSelected = movePlayer1_1
                Toast.makeText(this, player1.cards[0].nama, Toast.LENGTH_SHORT).show()
            }
        }

        movePlayer1_2.setOnClickListener {
            if (game.turn == 1) {
                cardSelected = player1.cards[1]
                ibSelected = movePlayer1_2
                Toast.makeText(this, player1.cards[1].nama, Toast.LENGTH_SHORT).show()
            }
        }

        movePlayer2_1.setOnClickListener {
            if (game.turn == 2) {
                cardSelected = player2.cards[0]
                ibSelected = movePlayer2_1
                Toast.makeText(this, player2.cards[0].nama, Toast.LENGTH_SHORT).show()
            }
        }

        movePlayer2_2.setOnClickListener {
            if (game.turn == 2) {
                cardSelected = player2.cards[1]
                ibSelected = movePlayer2_2
                Toast.makeText(this, player2.cards[1].nama, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun resetTile() {
        if (tileSelected != null) {
            val listPoint = game.getValidMoves(tileSelected!!, game.getPlayerBasedOnTurn(), cardSelected!!)
            for (p in listPoint) {
                tiles[p.y][p.x].setBackgroundResource(R.drawable.tile_default)
                tiles[p.y][p.x].setOnClickListener(null)
            }
        }
    }

    fun changeTileSelected(point: Point) {
        resetTile()
        tileSelected = point
        val listPoint =
            cardSelected?.let {
                game.getValidMoves(tileSelected!!, game.getPlayerBasedOnTurn(),
                    it
                )
            }
        if (listPoint != null) {
            for (p in listPoint) {
                tiles[p.y][p.x].setBackgroundColor(resources.getColor(R.color.red))
                tiles[p.y][p.x].setOnClickListener {
                    resetTile()
                    tileSelected?.let { it1 -> cardSelected?.let { it2 ->
                        game.move(it1, Point(p.y, p.x), game.getPlayerBasedOnTurn(),
                            it2
                        )
                        val temp = cardSelected
                        changeCard(ibSelected!!, moveNext.tag.toString())
                        val indexCard = player1.cards.indexOf(cardSelected)
                        player1.cards.remove(cardSelected)
                        player1.cards.add(indexCard, nextCard)
                        changeCard(moveNext, temp!!.nama)
                        nextCard = temp
//                        game.endTurn()
                    } }
                }
            }
        }
    }

    fun changeCard(ib: ImageButton, nama: String) {
        when (nama) {
            "Boar" -> {
                ib.setImageResource(R.drawable.boar)
                ib.tag = "Boar"
            }
            "Cobra" -> {
                ib.setImageResource(R.drawable.cobra)
                ib.tag = "Cobra"
            }
            "Crab" -> {
                ib.setImageResource(R.drawable.crab)
                ib.tag = "Crab"
            }
            "Crane" -> {
                ib.setImageResource(R.drawable.crane)
                ib.tag = "Crane"
            }
            "Dragon" -> {
                ib.setImageResource(R.drawable.dragon)
                ib.tag = "Dragon"
            }
            "Eel" -> {
                ib.setImageResource(R.drawable.eel)
                ib.tag = "Eel"
            }
            "Elephant" -> {
                ib.setImageResource(R.drawable.elephant)
                ib.tag = "Elephant"
            }
            "Frog" -> {
                ib.setImageResource(R.drawable.frog)
                ib.tag = "Frog"
            }
            "Goose" -> {
                ib.setImageResource(R.drawable.goose)
                ib.tag = "Goose"
            }
            "Horse" -> {
                ib.setImageResource(R.drawable.horse)
                ib.tag = "Horse"
            }
            "Mantis" -> {
                ib.setImageResource(R.drawable.mantis)
                ib.tag = "Mantis"
            }
            "Monkey" -> {
                ib.setImageResource(R.drawable.monkey)
                ib.tag = "Monkey"
            }
            "Ox" -> {
                ib.setImageResource(R.drawable.ox)
                ib.tag = "Ox"
            }
            "Rabbit" -> {
                ib.setImageResource(R.drawable.rabbit)
                ib.tag = "Rabbit"
            }
            "Rooster" -> {
                ib.setImageResource(R.drawable.rooster)
                ib.tag = "Rooster"
            }
            "Tiger" -> {
                ib.setImageResource(R.drawable.tiger)
                ib.tag = "Tiger"
            }
        }
    }
}