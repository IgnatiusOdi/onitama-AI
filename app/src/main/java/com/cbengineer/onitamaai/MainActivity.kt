package com.cbengineer.onitamaai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var btVsPlayer: Button
    lateinit var btVsAI: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btVsPlayer = findViewById(R.id.btVsPlayer)
        btVsAI = findViewById(R.id.btVsAI)
        
        btVsPlayer.setOnClickListener {
            startActivity(Intent(this, PlayerActivity::class.java))
        }
        
        btVsAI.setOnClickListener {
            Toast.makeText(this, "COMING SOON", Toast.LENGTH_SHORT).show()
        }
    }
}