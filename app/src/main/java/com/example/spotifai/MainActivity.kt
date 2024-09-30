package com.example.spotifai

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val songSpinner: Spinner = findViewById(R.id.songSpinner)
        val playButton: Button = findViewById(R.id.playButton)

        val songs = arrayOf(
            "Mike Laure - El Zapato",
            "Granito de Mostaza - Amén",
            "Save your tears - The Weeknd",
            "Im a goofy goober  - Spoonge Bob",
            "Scatman (ski-ba-bop-ba-dop-bop) - Scatman John",
            "Quaoar - Camelia",
            "He Man sings - 4 Non Blondes",
            "Vitas  - The 7th Element",
            "Morat - Cuando nadie ve",
            "Somewhere over the Rainbow - Israel Kamakawiwo"
        )

        val songAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, songs)
        songAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        songSpinner.adapter = songAdapter

        playButton.setOnClickListener {
            val selectedPosition = songSpinner.selectedItemPosition
            val intent = PlayerActivity.newIntent(this, selectedPosition)
            startActivity(intent)
        }
    }
}