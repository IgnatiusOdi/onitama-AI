package com.cbengineer.onitamaai

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
    var tiles: ArrayList<ImageButton> = arrayListOf()

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
        tiles.add(findViewById(R.id.tile01))
        tiles.add(findViewById(R.id.tile02))
        tiles.add(findViewById(R.id.tile03))
        tiles.add(findViewById(R.id.tile04))
        tiles.add(findViewById(R.id.tile05))
        tiles.add(findViewById(R.id.tile06))
        tiles.add(findViewById(R.id.tile07))
        tiles.add(findViewById(R.id.tile08))
        tiles.add(findViewById(R.id.tile09))
        tiles.add(findViewById(R.id.tile10))
        tiles.add(findViewById(R.id.tile11))
        tiles.add(findViewById(R.id.tile12))
        tiles.add(findViewById(R.id.tile13))
        tiles.add(findViewById(R.id.tile14))
        tiles.add(findViewById(R.id.tile15))
        tiles.add(findViewById(R.id.tile16))
        tiles.add(findViewById(R.id.tile17))
        tiles.add(findViewById(R.id.tile18))
        tiles.add(findViewById(R.id.tile19))
        tiles.add(findViewById(R.id.tile20))
        tiles.add(findViewById(R.id.tile21))
        tiles.add(findViewById(R.id.tile22))
        tiles.add(findViewById(R.id.tile23))
        tiles.add(findViewById(R.id.tile24))
        tiles.add(findViewById(R.id.tile25))

        movePlayer1_1.setOnClickListener {
            Toast.makeText(this, "Crane", Toast.LENGTH_SHORT).show()
        }

        movePlayer1_2.setOnClickListener {
            Toast.makeText(this, "Dragon", Toast.LENGTH_SHORT).show()
        }

        movePlayer2_1.setOnClickListener {
            Toast.makeText(this, "Cobra", Toast.LENGTH_SHORT).show()
        }

        movePlayer2_2.setOnClickListener {
            Toast.makeText(this, "Crab", Toast.LENGTH_SHORT).show()
        }
    }
}