package com.example.spotifai

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PlayerActivity : AppCompatActivity() {
    // Declaración de variables que serán usadas para interactuar con la interfaz de usuario
    private lateinit var songImageView: ImageView
    private lateinit var songTitleTextView: TextView
    private lateinit var songDurationTextView: TextView // Nueva vista para mostrar la duración
    private lateinit var playPauseButton: Button
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private var mediaPlayer: MediaPlayer? = null
    private var currentSongIndex: Int = 0 // Índice para saber cuál canción está seleccionada

    // Arrays que almacenan los títulos, imágenes, recursos de audio y duraciones de las canciones
    private val songTitles = arrayOf(
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

    // Almacena las imágenes asociadas a cada canción
    private val songImages = intArrayOf(
        R.drawable.zapato,
        R.drawable.mostaza,
        R.drawable.tears,
        R.drawable.goober,
        R.drawable.scatman,
        R.drawable.quaoar,
        R.drawable.hey,
        R.drawable.vitas,
        R.drawable.mora,
        R.drawable.some
    )

    // Almacena los archivos de audio (recursos .raw)
    private val songResources = intArrayOf(
        R.raw.zapato,
        R.raw.mostaza,
        R.raw.tears,
        R.raw.goober,
        R.raw.scatman,
        R.raw.quaoar,
        R.raw.hey,
        R.raw.vitas,
        R.raw.mora,
        R.raw.some
    )

    // Nuevo array con las duraciones de las canciones en formato "min:seg"
    private val songDurations = arrayOf(
        "2:45",
        "3:10",
        "3:36",
        "2:58",
        "3:30",
        "4:05",
        "3:52",
        "3:20",
        "3:40",
        "4:30"
    )

    companion object {
        private const val EXTRA_SONG_INDEX = "com.example.spotifai.SONG_INDEX"

        // Método para crear un Intent y pasar el índice de la canción seleccionada
        fun newIntent(context: Context, songIndex: Int): Intent {
            return Intent(context, PlayerActivity::class.java).apply {
                putExtra(EXTRA_SONG_INDEX, songIndex)
            }
        }
    }

    // Método onCreate, inicializa la actividad y configura la interfaz de usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        // Asignamos las vistas a sus respectivas variables
        songImageView = findViewById(R.id.songImageView)
        songTitleTextView = findViewById(R.id.songTitleTextView)
        songDurationTextView = findViewById(R.id.songDurationTextView) // Nueva referencia a la TextView
        playPauseButton = findViewById(R.id.playPauseButton)
        previousButton = findViewById(R.id.previousButton)
        nextButton = findViewById(R.id.nextButton)
        backButton = findViewById(R.id.backButton)

        // Recibimos el índice de la canción seleccionada desde el Intent
        currentSongIndex = intent.getIntExtra(EXTRA_SONG_INDEX, 0)
        updateSongInfo() // Llamamos al método para actualizar la información de la canción

        // Listener para el botón de reproducir/pausar
        playPauseButton.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                pauseSong() // Si la canción está reproduciéndose, pausarla
            } else {
                playSong() // Si no está reproduciéndose, iniciar la canción
            }
        }

        // Listener para ir a la canción anterior
        previousButton.setOnClickListener {
            playPreviousSong()
        }

        // Listener para ir a la siguiente canción
        nextButton.setOnClickListener {
            playNextSong()
        }

        // Listener para regresar al menú principal
        backButton.setOnClickListener {
            finish() // Finaliza la actividad y regresa a la anterior
        }
    }

    // Método para actualizar la información de la canción actual
    private fun updateSongInfo() {
        songImageView.setImageResource(songImages[currentSongIndex]) // Cambia la imagen de la canción
        songTitleTextView.text = songTitles[currentSongIndex] // Muestra el título de la canción
        songDurationTextView.text = "Duration: ${songDurations[currentSongIndex]}" // Muestra la duración de la canción
    }

    // Método para reproducir la canción seleccionada
    private fun playSong() {
        mediaPlayer?.release() // Libera el recurso actual de MediaPlayer si existe
        mediaPlayer = MediaPlayer.create(this, songResources[currentSongIndex]) // Crea un nuevo MediaPlayer con la canción seleccionada
        mediaPlayer?.start() // Inicia la reproducción
        playPauseButton.text = "Pause" // Cambia el texto del botón a "Pause"
    }

    // Método para pausar la canción
    private fun pauseSong() {
        mediaPlayer?.pause() // Pausa la reproducción
        playPauseButton.text = "Play" // Cambia el texto del botón a "Play"
    }

    // Método para reproducir la canción anterior
    private fun playPreviousSong() {
        // Calcula el índice de la canción anterior, asegurando que sea circular
        currentSongIndex = (currentSongIndex - 1 + songTitles.size) % songTitles.size
        updateSongInfo() // Actualiza la información de la nueva canción
        playSong() // Reproduce la nueva canción
    }

    // Método para reproducir la siguiente canción
    private fun playNextSong() {
        // Calcula el índice de la siguiente canción, asegurando que sea circular
        currentSongIndex = (currentSongIndex + 1) % songTitles.size
        updateSongInfo() // Actualiza la información de la nueva canción
        playSong() // Reproduce la nueva canción
    }

    // Método que se llama cuando la actividad se destruye, liberando los recursos del MediaPlayer
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Libera los recursos del MediaPlayer
        mediaPlayer = null // Se asegura de que el MediaPlayer sea nulo
    }
}
