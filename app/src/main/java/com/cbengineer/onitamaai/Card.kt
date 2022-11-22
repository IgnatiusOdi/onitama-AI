package com.cbengineer.onitamaai

import android.util.Log
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.random.Random
import kotlin.random.nextInt

class Card(
  val nama: String,
  val listPoint: List<Point>,
  val resId: Int,
) {
  override fun toString(): String {
    return nama
  }

  fun getMoves(from: Point, player: Player): ArrayList<Point> {
    val points = arrayListOf<Point>()
    for (move in listPoint) {
      if (player.order == Player.ORDER_PLAYER1) points.add(Point(from.x + move.x, from.y + move.y))
      else points.add(Point(from.x + (move.x * -1), from.y + (move.y * -1)))
    }
    return points
  }

  companion object {
    var deck = getAllCard()
    fun visualisasiCard(card: Card) {
      val board = arrayOf(
        arrayOf("-", "-", "-", "-", "-"),
        arrayOf("-", "-", "-", "-", "-"),
        arrayOf("-", "-", "P", "-", "-"),
        arrayOf("-", "-", "-", "-", "-"),
        arrayOf("-", "-", "-", "-", "-"),
      )
      val posisi = Point(2, 2)

      for (point in card.listPoint) {
        board[posisi.y + point.y][posisi.x + point.x] = "X"
      }
      Log.d("ONITAMA", card.nama)
      for (row in board) {
        var string = ""
        for (col in row) {
          string += col
        }
        Log.d("ONITAMA", string)
      }
    }

    fun randomCardFromDeck(): Card {
      // biar random nya nda sama terus
      // pake seed dari detik sekarang
      return deck.removeAt(Random(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)).nextInt(0 until deck.size))
    }

    fun getAllCard(): ArrayList<Card> {
      return arrayListOf<Card>(
        Card(
          "Tiger",
          listOf<Point>(
            Point(0, -2),
            Point(0, 1),
          ),
          R.drawable.tiger
        ),
        Card(
          "Crab",
          listOf<Point>(
            Point(0, -1),
            Point(-2, 0),
            Point(2, 0),
          ),
          R.drawable.crab
        ),
        Card(
          "Monkey",
          listOf<Point>(
            Point(-1, -1),
            Point(1, -1),
            Point(-1, 1),
            Point(1, 1),
          ),
          R.drawable.monkey
        ),
        Card(
          "Crane",
          listOf<Point>(
            Point(0, -1),
            Point(-1, 1),
            Point(1, 1),
          ),
          R.drawable.crane
        ),
        Card(
          "Dragon",
          listOf<Point>(
            Point(-2, -1),
            Point(2, -1),
            Point(-1, 1),
            Point(1, 1),
          ),
          R.drawable.dragon
        ),
        Card(
          "Elephant",
          listOf<Point>(
            Point(-1, -1),
            Point(1, -1),
            Point(-1, 0),
            Point(1, 0),
          ),
          R.drawable.elephant
        ),
        Card(
          "Mantis",
          listOf<Point>(
            Point(-1, -1),
            Point(1, -1),
            Point(0, 1),
          ),
          R.drawable.mantis
        ),
        Card(
          "Boar",
          listOf<Point>(
            Point(0, -1),
            Point(-1, 0),
            Point(1, 0),
          ),
          R.drawable.boar
        ),
        Card(
          "Frog",
          listOf<Point>(
            Point(-1, -1),
            Point(-2, 0),
            Point(1, 1),
          ),
          R.drawable.frog
        ),
        Card(
          "Goose",
          listOf<Point>(
            Point(-1, -1),
            Point(-1, 0),
            Point(1, 0),
            Point(1, 1),
          ),
          R.drawable.goose
        ),
        Card(
          "Horse",
          listOf<Point>(
            Point(0, -1),
            Point(-1, 0),
            Point(0, 1),
          ),
          R.drawable.horse
        ),
        Card(
          "Eel",
          listOf<Point>(
            Point(-1, -1),
            Point(1, 0),
            Point(-1, 1),
          ),
          R.drawable.eel
        ),
        Card(
          "Rabbit",
          listOf<Point>(
            Point(1, -1),
            Point(2, 0),
            Point(-1, 1),
          ),
          R.drawable.rabbit
        ),
        Card(
          "Rooster",
          listOf<Point>(
            Point(1, -1),
            Point(-1, 0),
            Point(1, 0),
            Point(-1, 1),
          ),
          R.drawable.rooster
        ),
        Card(
          "Ox",
          listOf<Point>(
            Point(0, -1),
            Point(1, 0),
            Point(0, 1),
          ),
          R.drawable.ox
        ),
        Card(
          "Cobra",
          listOf<Point>(
            Point(1, -1),
            Point(-1, 0),
            Point(1, 1),
          ),
          R.drawable.cobra
        ),
      )
    }
  }
}