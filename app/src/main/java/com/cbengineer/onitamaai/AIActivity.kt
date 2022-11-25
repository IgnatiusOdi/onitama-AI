package com.cbengineer.onitamaai

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AIActivity : AppCompatActivity(){
    lateinit var llMessageParent: LinearLayout
    lateinit var tvMessage: TextView
    lateinit var btnBackToMenu: Button

    lateinit var llDiscardCardParent: LinearLayout
    lateinit var rvDiscardCard: RecyclerView
    lateinit var tvDiscardCard: TextView
    lateinit var btnDiscard: Button

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

        llDiscardCardParent = findViewById(R.id.llDiscardCardParent)
        rvDiscardCard = findViewById(R.id.rvDiscardCard)
        tvDiscardCard = findViewById(R.id.tvDiscardCard)
        btnDiscard = findViewById(R.id.btnDiscard)

        llMessageParent = findViewById(R.id.llMessageParent)
        tvMessage = findViewById(R.id.tvMessage)
        btnBackToMenu = findViewById(R.id.btnBackToMenu)

        btnBackToMenu.setOnClickListener {
            finish()
        }

        btnDiscard.setOnClickListener {
            if (selectedCard != null) {
                val player = game.getPlayerBasedOnTurn()
                val opponent = game.getOpponentBasedOnTurn()
                opponent.cards.remove(game.nextCard)
                player.cards.add(game.nextCard)
                game.nextCard = selectedCard!!
                adapterDeckPlayer1.notifyDataSetChanged()
                adapterDeckPlayer2.notifyDataSetChanged()
                endTurn()
                llDiscardCardParent.visibility = View.GONE
            }
            else {
                Toast.makeText(this, "Please pick a card...", Toast.LENGTH_SHORT).show()
            }
        }

        Card.deck = Card.getAllCard()
        nextCard = Card.randomCardFromDeck()

        player1 = Player("Player 1", Player.ORDER_PLAYER1)
        player2 = Player("Player 2", Player.ORDER_PLAYER2)
        game = GameEngine(player1, player2)
        player2.cards.add(game.nextCard)
        tvTurn.setTextColor(ContextCompat.getColor(this, R.color.blue))

        for (i in tiles.indices) {
            for (j in tiles[i].indices) {
                val col = tiles[i][j]
                col.setTag(R.id.TAG_TILE, game.board[i][j])
                col.setTag(R.id.TAG_POINT, Point(j, i))
                col.setTag(R.id.TAG_VALID_MOVE, false)
                col.setOnClickListener {
                    //
                    val isValidMove = col.getTag(R.id.TAG_VALID_MOVE) as Boolean
                    if (isValidMove) {
                        val to = col.getTag(R.id.TAG_POINT) as Point
                        selectedCard?.let { card ->
                            // move
                            val tileBaru = col
                            // unhighlight valid move, tile lama
                            selectedTile?.let { tileLama ->
                                // swapping
                                val piece: Piece = tileLama.getTag(R.id.TAG_TILE) as Piece
                                val from: Point = tileLama.getTag(R.id.TAG_POINT) as Point
                                game.move(from, to)
                                val player = game.getPlayerBasedOnTurn()
                                val opponent = game.getOpponentBasedOnTurn()
                                opponent.cards.remove(game.nextCard)
                                player.cards.add(game.nextCard)
                                game.nextCard = card
                                adapterDeckPlayer1.notifyDataSetChanged()
                                adapterDeckPlayer2.notifyDataSetChanged()

                                // unhighlight valid moves
                                unHighlightValidMoves(game.getValidMoves(from, piece.player, card))
                                // reset tag valid move
                                tileLama.setTag(R.id.TAG_VALID_MOVE, false)
                                tileBaru.setTag(R.id.TAG_VALID_MOVE, false)

                                // set tag tile baru
                                tileBaru.setTag(R.id.TAG_TILE, tileLama.getTag(R.id.TAG_TILE))
                                tileLama.setTag(R.id.TAG_TILE, null)

                                // reset gambar pion/king
                                tileBaru.setImageResource(piece.getDrawable())
                                tileLama.setImageDrawable(null)

                                //reset background
                                tileBaru.setBackgroundResource(R.drawable.tile_default)
                                tileLama.setBackgroundResource(R.drawable.tile_default)
                                tiles[GameEngine.PLAYER1_BASE.y][GameEngine.PLAYER1_BASE.x].setBackgroundResource(R.drawable.tile_base_blue)
                                tiles[GameEngine.PLAYER2_BASE.y][GameEngine.PLAYER2_BASE.x].setBackgroundResource(R.drawable.tile_base_red)
                            }
                        }
                        // pengecekan menang
                        if (game.checkIfWin(game.getPlayerBasedOnTurn())) {
                            showMessageMenang(game.getPlayerBasedOnTurn())
                        }
                        endTurn()
                        if (!game.checkLegalMovesExist(game.getPlayerBasedOnTurn())) {
                            selectedCard = null
                            if (game.getPlayerBasedOnTurn().order == Player.ORDER_PLAYER1)
                                rvDiscardCard.adapter = adapterDeckPlayer1
                            else
                                rvDiscardCard.adapter = adapterDeckPlayer2
//              adapterDeckPlayer1.notifyDataSetChanged()
//              adapterDeckPlayer2.notifyDataSetChanged()
                            llDiscardCardParent.visibility = View.VISIBLE
                        }
                    }
                    else {
                        onTileClicked(col, Point(j, i))
                    }
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
        rvDiscardCard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    fun showMessageMenang(player: Player) {
        tvMessage.text = "${player.nama} Won!"
        if (player.order == Player.ORDER_PLAYER1) {
            tvMessage.setTextColor(ContextCompat.getColor(this, R.color.blue))
        }
        else {
            tvMessage.setTextColor(ContextCompat.getColor(this, R.color.red))
        }
        llMessageParent.isVisible = true
    }

    fun endTurn() {
        game.endTurn()
        selectedCard = null
        selectedTile = null
        tvTurn.text = getTurnText()
        if (game.getPlayerBasedOnTurn().order == Player.ORDER_PLAYER1) {
            tvTurn.setTextColor(ContextCompat.getColor(this, R.color.blue))
            adapterDeckPlayer2.notifyDataSetChanged()
        } else {
            tvTurn.setTextColor(ContextCompat.getColor(this, R.color.red))
            adapterDeckPlayer1.notifyDataSetChanged()
        }
    }

    fun onTileClicked(tile: ImageButton, from: Point) {
        // set selectedTile sebelumnya jadi default
        selectedTile?.let {
            val point: Point = it.getTag(R.id.TAG_POINT) as Point
            if (point.y == GameEngine.PLAYER1_BASE.y && point.x == GameEngine.PLAYER1_BASE.x) {
                tiles[GameEngine.PLAYER1_BASE.y][GameEngine.PLAYER1_BASE.x].setBackgroundResource(R.drawable.tile_base_blue)
            }
            else if (point.y == GameEngine.PLAYER2_BASE.y && point.x == GameEngine.PLAYER2_BASE.x) {
                tiles[GameEngine.PLAYER2_BASE.y][GameEngine.PLAYER2_BASE.x].setBackgroundResource(R.drawable.tile_base_red)
            }
            else {
                tiles[point.y][point.x].setBackgroundResource(R.drawable.tile_default)
            }
        }
//    selectedTile?.setBackgroundResource(R.drawable.tile_default)
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
            selectedCard?.let { card ->
                // unhighlight validMoves sebelumnya
                selectedTile?.let {
                    val fromm: Point = it.getTag(R.id.TAG_POINT) as Point
                    unHighlightValidMoves(game.getValidMoves(fromm, piece.player, card))
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
        selectedTile?.let {
            val point: Point = it.getTag(R.id.TAG_POINT) as Point
            if (point.y == GameEngine.PLAYER1_BASE.y && point.x == GameEngine.PLAYER1_BASE.x) {
                tiles[GameEngine.PLAYER1_BASE.y][GameEngine.PLAYER1_BASE.x].setBackgroundResource(R.drawable.tile_base_blue_selected)
            }
            else if (point.y == GameEngine.PLAYER2_BASE.y && point.x == GameEngine.PLAYER2_BASE.x) {
                tiles[GameEngine.PLAYER2_BASE.y][GameEngine.PLAYER2_BASE.x].setBackgroundResource(R.drawable.tile_base_red_selected)
            }
            else {
                tiles[point.y][point.x].setBackgroundResource(R.drawable.tile_selected)
            }
        }
//    selectedTile?.setBackgroundResource(R.drawable.tile_selected)
//    tiles[GameEngine.PLAYER1_BASE.y][GameEngine.PLAYER1_BASE.x].setBackgroundResource(R.drawable.tile_base_blue)
//    tiles[GameEngine.PLAYER2_BASE.y][GameEngine.PLAYER2_BASE.x].setBackgroundResource(R.drawable.tile_base_red)
    }

    fun highlightValidMoves(validMoves: List<Point>) {
        for (move in validMoves) {
            if (move.y == GameEngine.PLAYER1_BASE.y && move.x == GameEngine.PLAYER1_BASE.x) {
                tiles[GameEngine.PLAYER1_BASE.y][GameEngine.PLAYER1_BASE.x].setBackgroundResource(R.drawable.tile_base_blue_valid_move)
            }
            else if (move.y == GameEngine.PLAYER2_BASE.y && move.x == GameEngine.PLAYER2_BASE.x) {
                tiles[GameEngine.PLAYER2_BASE.y][GameEngine.PLAYER2_BASE.x].setBackgroundResource(R.drawable.tile_base_red_valid_move)
            }
            else {
                tiles[move.y][move.x].setBackgroundResource(R.drawable.tile_valid_move)
            }
            tiles[move.y][move.x].setTag(R.id.TAG_VALID_MOVE, true)
        }
    }

    fun unHighlightValidMoves(validMoves: List<Point>) {
        for (move in validMoves) {
            if (move.y == GameEngine.PLAYER1_BASE.y && move.x == GameEngine.PLAYER1_BASE.x) {
                tiles[GameEngine.PLAYER1_BASE.y][GameEngine.PLAYER1_BASE.x].setBackgroundResource(R.drawable.tile_base_blue)
            }
            else if (move.y == GameEngine.PLAYER2_BASE.y && move.x == GameEngine.PLAYER2_BASE.x) {
                tiles[GameEngine.PLAYER2_BASE.y][GameEngine.PLAYER2_BASE.x].setBackgroundResource(R.drawable.tile_base_red)
            }
            else {
                tiles[move.y][move.x].setBackgroundResource(R.drawable.tile_default)
            }
            tiles[move.y][move.x].setTag(R.id.TAG_VALID_MOVE, false)
        }
    }

    fun selectCard(card: Card) {
        if (card == selectedCard) {
            selectedTile?.let {
                val piece: Piece = it.getTag(R.id.TAG_TILE) as Piece
                val point: Point? = it.getTag(R.id.TAG_POINT) as Point?
                point?.let {
                    unHighlightValidMoves(game.getValidMoves(point, piece.player, card))
                }
            }
            selectedCard = null
        }
        else {
            selectedCard?.let { cardLama->
                selectedTile?.let {
                    val piece: Piece? = it.getTag(R.id.TAG_TILE) as Piece?
                    val point: Point? = it.getTag(R.id.TAG_POINT) as Point?
                    if (piece != null){
                        point?.let {
                            unHighlightValidMoves(game.getValidMoves(point, piece.player, cardLama))
                        }
                    }
                }
            }
            selectedCard = card
            selectedTile?.let {
                val piece: Piece = it.getTag(R.id.TAG_TILE) as Piece
                val point: Point? = it.getTag(R.id.TAG_POINT) as Point?
                point?.let {
                    highlightValidMoves(game.getValidMoves(point, piece.player, card))
                }
            }
        }
    }

    fun getTurnText(): String {
        return "${game.getPlayerBasedOnTurn().nama}'s Turn"
    }

    fun AiThink() {
        // bot
//        val currState = GameState(game.board,player1.cards,player2.cards,game.nextCard)
        val currState = GameEngine(player1,player2,game.board,game.nextCard)



//        val validMoves = game.getValidMoves()
    }

    /**
     * Traverse through nodes using minimax alpha beta pruning.
     *
     * When max node, use alpha as value.
     * When min node, use beta as value.
     *
     * @author Xander
     *
     * @param   state GameState to expand
     * @param   alpha alpha score
     * @param   beta beta score
     * @param   type NodeType MAX/MIN
     * @param   maxDepth maximum depth to expand
     * @return  node score
     */
    fun nodeTraverse(state : GameEngine, alpha:Int, beta:Int, type:NodeType, currDepth:Int, maxDepth: Int) : Int{

        if (currDepth == maxDepth) {
            // evaluate state

        } else if (type == NodeType.MAX) {
            // max layer
        } else {
            // min layer
        }

        val player = state.getPlayerBasedOnTurn()
        for (i in state.board.indices) {
            for (j in state.board[i].indices) {
                val piece = state.board[i][j]
                if (piece != null && piece.player == player) {
                    for (card in player.cards) {
                        val validMoves = state.getValidMoves(Point(j,i),player,card)
                        for (validMove in validMoves) {                                                  // for every valid move in every piece, branch off
                            val nextState = GameEngine.clone(state)
                            nextState.move(Point(j,i),validMove)
                            nodeTraverse(nextState, alpha, beta, type, currDepth+1, maxDepth)
                        }
                    }
                }
            }
        }
        return 0 // TODO: replace
    }

//    fun staticBoardEvaluator(state: GameEngine) : Int {
//
//    }

    enum class NodeType {
        MIN, MAX
    }
}