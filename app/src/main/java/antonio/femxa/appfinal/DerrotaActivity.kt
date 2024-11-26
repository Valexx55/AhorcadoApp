package antonio.femxa.appfinal

//import android.R
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity


class DerrotaActivity : AppCompatActivity() {
    private var palabra: String? = null
    private var mediaPlayer: MediaPlayer? = null
    private var intent: Intent? = null
    private var musicaOnOff: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_derrota)
        val imageView = findViewById<View>(R.id.imagenDerrota) as ImageView

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            imageView.setBackgroundResource(R.drawable.progress_animation_gameover)
            val progressAnimation = imageView.background as AnimationDrawable
            progressAnimation.start()
        } else {
            imageView.setBackgroundResource(R.drawable.pantallagameover)
        }
        palabra = getIntent().getStringExtra("palabra_clave")

        val button = findViewById<View>(R.id.boton_derrota_inicio) as Button

        val textView = findViewById<View>(R.id.text_palabra_oculta) as TextView

        textView.text = palabra

        button.setOnClickListener {
            val intent = Intent(
                this@DerrotaActivity,
                CategoriaActivity::class.java
            )
            intent.putExtra("SonidoOn-Off", musicaOnOff)
            startActivity(intent)
        }

        //acción botón hacia atrás
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(
                    this@DerrotaActivity,
                    CategoriaActivity::class.java
                )

                startActivity(intent)
                finish()
            }
        })
    }

    override fun onResume() {
        super.onResume()

        musicaOnOff = getIntent().getBooleanExtra("SonidoOn-Off", true)

        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_perdedor)
        mediaPlayer!!.isLooping = false
        mediaPlayer!!.setVolume(100f, 100f)

        if (musicaOnOff) {
            mediaPlayer!!.start()
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer!!.stop()
    }

   /* override fun onBackPressed() {
        val intent = Intent(
            this@DerrotaActivity,
            CategoriaActivity::class.java
        )

        startActivity(intent)
        finish()
    }*/
}