package com.cbengineer.onitamaai

import android.util.Log

class Card (
    val nama: String,
    val listPoint: List<Point>
) {
    companion object {
        fun visualisasiCard(card: Card) {
            val board = arrayOf(
                arrayOf("-", "-", "-", "-", "-"),
                arrayOf("-", "-", "-", "-", "-"),
                arrayOf("-", "-", "P", "-", "-"),
                arrayOf("-", "-", "-", "-", "-"),
                arrayOf("-", "-", "-", "-", "-"),
            )
            val posisi = Point(2,2)

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
        
        fun getAllCard(): List<Card> {
            return listOf<Card>(
                Card(
                    "Tiger",
                    listOf<Point>(
                        Point(0, -2),
                        Point(0, 1),
                    )
                ),
                Card(
                    "Crab",
                    listOf<Point>(
                        Point(0, -1),
                        Point(-2, 0),
                        Point(2, 0),
                    )
                ),
                Card(
                    "Monkey",
                    listOf<Point>(
                        Point(-1, -1),
                        Point(1, -1),
                        Point(-1, 1),
                        Point(1, 1),
                    )
                ),
                Card(
                    "Crane",
                    listOf<Point>(
                        Point(0, -1),
                        Point(-1, 1),
                        Point(1, 1),
                    )
                ),
                Card(
                    "Dragon",
                    listOf<Point>(
                        Point(-2, -1),
                        Point(2, -1),
                        Point(-1, 1),
                        Point(1, 1),
                    )
                ),
                Card(
                    "Elephant",
                    listOf<Point>(
                        Point(-1, -1),
                        Point(1, -1),
                        Point(-1, 0),
                        Point(1, 0),
                    )
                ),
                Card(
                    "Mantis",
                    listOf<Point>(
                        Point(-1, -1),
                        Point(1, -1),
                        Point(0, 1),
                    )
                ),
                Card(
                    "Boar",
                    listOf<Point>(
                        Point(0, -1),
                        Point(-1, 0),
                        Point(1, 0),
                    )
                ),
                Card(
                    "Frog",
                    listOf<Point>(
                        Point(-1, -1),
                        Point(-2, 0),
                        Point(1, 1),
                    )
                ),
                Card(
                    "Goose",
                    listOf<Point>(
                        Point(-1, -1),
                        Point(-1, 0),
                        Point(1, 0),
                        Point(1, 1),
                    )
                ),
                Card(
                    "Horse",
                    listOf<Point>(
                        Point(0,-1),
                        Point(-1,0),
                        Point(0,1),
                    )
                ),
                Card(
                    "Eel",
                    listOf<Point>(
                        Point(-1, -1),
                        Point(1, 0),
                        Point(-1, 1),
                    )
                ),
                Card(
                    "Rabbit",
                    listOf<Point>(
                        Point(1, -1),
                        Point(2, 0),
                        Point(-1, 1),
                    )
                ),
                Card(
                    "Rooster",
                    listOf<Point>(
                        Point(1, -1),
                        Point(-1, 0),
                        Point(1, 0),
                        Point(-1, 1),
                    )
                ),
                Card(
                    "Ox",
                    listOf<Point>(
                        Point(0, -1),
                        Point(1, 0),
                        Point(0, 1),
                    )
                ),
                Card(
                    "Cobra",
                    listOf<Point>(
                        Point(1, -1),
                        Point(-1, 0),
                        Point(1, 1),
                    )
                ),
            )
        }
    }
}