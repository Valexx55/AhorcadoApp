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


class VictoriaActivity : AppCompatActivity() {
    private var palabra: String? = null
    private var musicaOnOff: Boolean = false
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_victoria)

        val imageView = findViewById<View>(R.id.imagenvictoria) as ImageView
        //esta sentencia condicional a continuación es incorrecta, puesto que la clase AnimationDrawable
        //está soportada desde la versión 1; por lo cual, la animación que consiste en una rotacin de foto
        //sería visible en cualquier dispositivo, carenciendo de sentido este if
        //  LO MISMO PARA DERROTA ACTIVITY: SOBRA
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            imageView.setBackgroundResource(R.drawable.progress_animation_gameover2)
            val progressAnimation = imageView.background as AnimationDrawable
            progressAnimation.start()
        } else {
            imageView.setBackgroundResource(R.drawable.pantallavictoria)
        }

        palabra = intent.getStringExtra("palabra_clave")

        val button = findViewById<View>(R.id.boton_victoria_inicio) as Button

        val textView = findViewById<View>(R.id.text_palabra_oculta_victoria) as TextView

        textView.text = palabra

        button.setOnClickListener {
            val intent = Intent(
                this@VictoriaActivity,
                CategoriaActivity::class.java
            )
            intent.putExtra("SonidoOn-Off", musicaOnOff)
            startActivity(intent)
        }


        //acción botón hacia atrás
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(
                    this@VictoriaActivity,
                    CategoriaActivity::class.java
                )

                startActivity(intent)

                finish()
            }
        })
    }

    override fun onResume() {
        super.onResume()

        musicaOnOff = intent.getBooleanExtra("SonidoOn-Off", true)

        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_ganador)
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

    /*override fun onBackPressed() {
        val intent = Intent(
            this@VictoriaActivity,
            CategoriaActivity::class.java
        )

        startActivity(intent)

        finish()
    }*/

}