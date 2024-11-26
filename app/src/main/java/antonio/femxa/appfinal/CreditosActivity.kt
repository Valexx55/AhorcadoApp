package antonio.femxa.appfinal

//import android.R
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class CreditosActivity : AppCompatActivity() {
    var lv: ListView? = null
    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creditos)
        val v = findViewById<View>(R.id.boton1) //castin
        val boton = v as Button

        mediaPlayer = MediaPlayer.create(this, R.raw.soni)
        mediaPlayer!!.isLooping = false
        mediaPlayer!!.setVolume(100f, 100f)

        boton.setOnLongClickListener {
            mediaPlayer!!.start()
            true
        }
    }
}