package antonio.femxa.appfinal

//import android.R
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity


class InicialActivity : AppCompatActivity() {
     var mediaPlayer: MediaPlayer? = null
     var musicaOnOff: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial)

        mediaPlayer = MediaPlayer.create(this, R.raw.inicio1)
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.setVolume(100f, 100f)

        //ponerTexto()

       musicaOnOff = intent.getBooleanExtra("SonidoOn-Off", true)

        val ib = findViewById<Button>(R.id.botonsonido)


        if (musicaOnOff) {
            mediaPlayer!!.start()
        }


        ib.setOnClickListener {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()
                ib.text = "SONIDO ON"
                musicaOnOff = false
            } else {
                ib.text = "SONIDO OFF"
                mediaPlayer!!.start()
                musicaOnOff = true
            }
        }

        //botón hacia atrás

        //acción botón hacia atrás
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        })
    }

    fun aJugar(v: View?) {
        val intent = Intent(
            this,
            CategoriaActivity::class.java
        )

        if (musicaOnOff) {
            intent.putExtra("SonidoOn-Off", true)
        } else {
            intent.putExtra("SonidoOn-Off", false)
        }

        startActivity(intent)
    }

//    fun ponerTexto() {
//        val v1 = findViewById<View>(R.id.botonsonido)
//        val ib = v1 as Button
//
//        val v2 = findViewById<View>(R.id.botoncreditos)
//        val ib2 = v2 as Button
//
//        ib.text = "SONIDO OFF"
//        ib2.text = "CREDITOS"
//    }

    fun abrirCreditos(v: View?) {
        val intent = Intent(this, CreditosActivity::class.java)
        startActivity(intent)
    }

    //fun sonidoOnOff(view: View) {}

    /* override fun onBackPressed() {

 //super.onBackPressed();

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
             finishAffinity()
         } else {
             finish()
         }

     }*/
}