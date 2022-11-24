package com.cbengineer.onitamaai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  lateinit var btVsPlayer: Button
  lateinit var btVsAI: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    coba()

    btVsPlayer = findViewById(R.id.btVsPlayer)
    btVsAI = findViewById(R.id.btVsAI)

    btVsPlayer.setOnClickListener {
      startActivity(Intent(this, PlayerActivity::class.java))
    }

    btVsAI.setOnClickListener {
      startActivity(Intent(this, AIActivity::class.java))
//      Toast.makeText(this, "COMING SOON", Toast.LENGTH_SHORT).show()
    }
  }

  fun coba() {
    val listCard = Card.getAllCard()
    for (card in listCard) {
      Card.visualisasiCard(card)
      Log.d("ONITAMA", "==============")
    }
  }
}