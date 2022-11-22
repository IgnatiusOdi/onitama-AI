package com.cbengineer.onitamaai

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AdapterCard(
  val context: Context,
  var listCard: ArrayList<Card>,
  val player: Player,
  val gameEngine: GameEngine,
  val onCardSelected: (Card)->Unit,
  val isCardSelected: (Card)->Boolean,
) : RecyclerView.Adapter<AdapterCard.CustomViewHolder>() {
  class CustomViewHolder(itemView: View) : ViewHolder(itemView) {
    var tiles: Array<Array<View>> = arrayOf(
      arrayOf(
        itemView.findViewById(R.id.tile_1),
        itemView.findViewById(R.id.tile_2),
        itemView.findViewById(R.id.tile_3),
        itemView.findViewById(R.id.tile_4),
        itemView.findViewById(R.id.tile_5),
      ),
      arrayOf(
        itemView.findViewById(R.id.tile_6),
        itemView.findViewById(R.id.tile_7),
        itemView.findViewById(R.id.tile_8),
        itemView.findViewById(R.id.tile_9),
        itemView.findViewById(R.id.tile_10),
      ),
      arrayOf(
        itemView.findViewById(R.id.tile_11),
        itemView.findViewById(R.id.tile_12),
        itemView.findViewById(R.id.tile_13),
        itemView.findViewById(R.id.tile_14),
        itemView.findViewById(R.id.tile_15),
      ),
      arrayOf(
        itemView.findViewById(R.id.tile_16),
        itemView.findViewById(R.id.tile_17),
        itemView.findViewById(R.id.tile_18),
        itemView.findViewById(R.id.tile_19),
        itemView.findViewById(R.id.tile_20),
      ),
      arrayOf(
        itemView.findViewById(R.id.tile_21),
        itemView.findViewById(R.id.tile_22),
        itemView.findViewById(R.id.tile_23),
        itemView.findViewById(R.id.tile_24),
        itemView.findViewById(R.id.tile_25),
      ),
    )
    var tvNamaCard: TextView = itemView.findViewById(R.id.tvNamaCard)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
    return CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.card, parent, false))
  }

  override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
    val item = listCard[position]
    val from = Point(2, 2)
    val listMove = item.getMoves(from, player)
    holder.tiles[from.y][from.x].setBackgroundResource(R.drawable.card_tile_middle)
    for (point in listMove) {
      holder.tiles[point.y][point.x].setBackgroundResource(R.drawable.card_tile_valid_move)
    }
    holder.tvNamaCard.setText(item.nama)
    if (isCardSelected(item)) {
      holder.itemView.setBackgroundResource(R.drawable.card_parent_selected)
    }
    else {
      holder.itemView.setBackgroundResource(R.drawable.card_parent)
    }
    holder.itemView.setOnClickListener {
      println("gameEngine.getPlayerBasedOnTurn()=${gameEngine.getPlayerBasedOnTurn().nama}")
      println("player=${player.nama}")
      if (gameEngine.getPlayerBasedOnTurn() == player) {
        onCardSelected(item)
        notifyDataSetChanged()
      }
    }
  }

  override fun getItemCount(): Int {
    return listCard.size
  }
}