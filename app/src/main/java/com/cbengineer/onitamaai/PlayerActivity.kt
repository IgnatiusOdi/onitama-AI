package com.cbengineer.onitamaai

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlayerActivity : AppCompatActivity() {
  lateinit var rvDeckPlayer1: RecyclerView
  lateinit var rvDeckPlayer2: RecyclerView
  lateinit var tvTurn: TextView
  var tiles: Array<Array<ImageButton>> = arrayOf()

  lateinit var nextCard: Card
  lateinit var player1: Player
  lateinit var player2: Player
  lateinit var game: GameEngine

  lateinit var adapterDeckPlayer1: AdapterCard
  lateinit var adapterDeckPlayer2: AdapterCard

  var selectedCard: Card? = null
  var selectedTile: ImageButton? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_player)
    tiles = arrayOf(
      arrayOf(findViewById(R.id.tile_1), findViewById(R.id.tile_2), findViewById(R.id.tile_3), findViewById(R.id.tile_4), findViewById(R.id.tile_5)),
      arrayOf(findViewById(R.id.tile_6), findViewById(R.id.tile_7), findViewById(R.id.tile_8), findViewById(R.id.tile_9), findViewById(R.id.tile_10)),
      arrayOf(findViewById(R.id.tile_11), findViewById(R.id.tile_12), findViewById(R.id.tile_13), findViewById(R.id.tile_14), findViewById(R.id.tile_15)),
      arrayOf(findViewById(R.id.tile_16), findViewById(R.id.tile_17), findViewById(R.id.tile_18), findViewById(R.id.tile_19), findViewById(R.id.tile_20)),
      arrayOf(findViewById(R.id.tile_21), findViewById(R.id.tile_22), findViewById(R.id.tile_23), findViewById(R.id.tile_24), findViewById(R.id.tile_25)),
    )
    rvDeckPlayer1 = findViewById(R.id.rvDeckPlayer1)
    rvDeckPlayer2 = findViewById(R.id.rvDeckPlayer2)
    tvTurn = findViewById(R.id.tvTurn)

    Card.deck = Card.getAllCard()
    nextCard = Card.randomCardFromDeck()

    player1 = Player("Player 1", Player.ORDER_PLAYER1)
    player2 = Player("Player 2", Player.ORDER_PLAYER2)
    game = GameEngine(player1, player2)

    for (i in 0 until tiles.size) {
      for (j in 0 until tiles[i].size) {
        val col = tiles[i][j]
        col.setTag(R.id.TAG_TILE, game.board[i][j])
        col.setTag(R.id.TAG_POINT, Point(j, i))
        col.setTag(R.id.TAG_VALID_MOVE, false)
        col.setOnClickListener {
          println("==========DEBUG==========")
          println(col.getTag(R.id.TAG_TILE) as Piece?)
          println(col.getTag(R.id.TAG_POINT) as Point)
          println(col.getTag(R.id.TAG_VALID_MOVE) as Boolean)
          println(game.board[(col.getTag(R.id.TAG_POINT) as Point).y][(col.getTag(R.id.TAG_POINT) as Point).x])
          println("BOARD SESUDAH:")
          game.printBoard()
          //
          val isValidMove = col.getTag(R.id.TAG_VALID_MOVE) as Boolean
          println("isValidMove=$isValidMove")
          if (isValidMove) {
            val from = col.getTag(R.id.TAG_POINT) as Point
            selectedCard?.let { card ->
              // move
              game.board[i][j] = game.board[from.y][from.x]
              game.board[from.y][from.x] = null
//              game.move(from, Point(j, i), game.getPlayerBasedOnTurn(), card)
              val tileBaru = col
              // unhighlight valid move, tile lama
              selectedTile?.let { tileLama ->
                println("swapping...")
                val piece: Piece = tileLama.getTag(R.id.TAG_TILE) as Piece
                val from: Point = tileLama.getTag(R.id.TAG_POINT) as Point
                unHighlightValidMoves(game.getValidMoves(from, piece.player, card))
                tileLama.setTag(R.id.TAG_VALID_MOVE, false)
                tileBaru.setImageResource(piece.getDrawable())
                tileBaru.setTag(R.id.TAG_TILE, tileLama.getTag(R.id.TAG_TILE))
                tileLama.setTag(R.id.TAG_TILE, null)
                tileLama.setImageDrawable(null)
                tileLama.setBackgroundResource(R.drawable.tile_default)
              }
            }
          }
          else {
            onTileClicked(col, Point(j, i))
          }
          println(game.board[(col.getTag(R.id.TAG_POINT) as Point).y][(col.getTag(R.id.TAG_POINT) as Point).x])
          println("BOARD SESUDAH:")
          game.printBoard()
          println("==========DEBUG==========")
        }
      }
    }

    tvTurn.text = getTurnText()
    adapterDeckPlayer1 = AdapterCard(this, player1.cards, player1, game, {selectCard(it)}, {it == selectedCard})
    adapterDeckPlayer2 = AdapterCard(this, player2.cards, player2, game, {selectCard(it)}, {it == selectedCard})

    rvDeckPlayer1.adapter = adapterDeckPlayer1
    rvDeckPlayer2.adapter = adapterDeckPlayer2

    rvDeckPlayer1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    rvDeckPlayer2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
  }

  fun onTileClicked(tile: ImageButton, from: Point) {
    // set selectedTile sebelumnya jadi default
    selectedTile?.setBackgroundResource(R.drawable.tile_default)
    val piece: Piece? = tile.getTag(R.id.TAG_TILE) as Piece?
    // kalau selectedTile yang sebelumnya sama dengan tile yang di click sekarang
    if (selectedTile == tile) {
      // unset, lalu unhighlight valid moves
      selectedTile = null
      if (piece != null) {
        selectedCard?.let {
          unHighlightValidMoves(game.getValidMoves(from, piece.player, it))
        }
      }
    }
    // jika tidak dan piece nya tidak null, dan piece yang di klik itu adalah piece milik
    // player yang sedang gerak turn ini, maka
    else if (
      selectedTile != tile
      && piece != null
      && piece.player == game.getPlayerBasedOnTurn()
    ) {
      selectedCard?.let {
        val card = it
        // unhighlight validMoves sebelumnya
        selectedTile?.let {
          val from: Point = it.getTag(R.id.TAG_POINT) as Point
          unHighlightValidMoves(game.getValidMoves(from, piece.player, card))
        }
      }
      // set selectedTile sesuai dengan tile yang di klik sekarang
      selectedTile = tile
      // highlight valid moves
      selectedCard?.let {
        highlightValidMoves(game.getValidMoves(from, piece.player, it))
      }
    }
    // set backgroundResource selectedTile
    selectedTile?.setBackgroundResource(R.drawable.tile_selected)
  }

  fun highlightValidMoves(validMoves: List<Point>) {
    for (move in validMoves) {
      tiles[move.y][move.x].setBackgroundResource(R.drawable.tile_valid_move)
      tiles[move.y][move.x].setTag(R.id.TAG_VALID_MOVE, true)
    }
  }

  fun unHighlightValidMoves(validMoves: List<Point>) {
    for (move in validMoves) {
      tiles[move.y][move.x].setBackgroundResource(R.drawable.tile_default)
      tiles[move.y][move.x].setTag(R.id.TAG_VALID_MOVE, false)
    }
  }

  fun selectCard(card: Card) {
    if (card == selectedCard)
      selectedCard = null
    else
      selectedCard = card
  }

  fun getTurnText(): String {
    return "${game.getPlayerBasedOnTurn().nama}'s Turn"
  }
}