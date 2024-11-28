package antonio.femxa.appfinal

//import android.R
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class InicialActivity : AppCompatActivity() {
     var mediaPlayer: MediaPlayer? = null
     var musicaOnOff: Boolean = false

    private var mInterstitialAd: InterstitialAd? = null
    private final val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial)


        Thread {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(
                this
            ) { initializationStatus: InitializationStatus? ->
                Log.d("MIAPP", "inicialización de anuncios completada $initializationStatus")

                val adRequest = AdRequest.Builder().build()

                InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(TAG, adError.toString())
                        mInterstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d(TAG, "Anuncio Cargado")
                        mInterstitialAd = interstitialAd
                    }
                })



            }
        }
            .start()




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