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
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class InicialActivity : AppCompatActivity() {
     var mediaPlayer: MediaPlayer? = null
     var musicaOnOff: Boolean = false

    private var mInterstitialAd: InterstitialAd? = null
    private val TAG = "MIAPP"
    val idBloqueMioAdUnitId = "ca-app-pub-9910445535228761/8258514024"
    val idBloqueMioPruebaAdUnitId = "ca-app-pub-3940256099942544/1033173712"


    fun iniciarAnuncios ()
    { //0 PASO AD LIBRRERÍA
        Thread {
            //1 INICIALIZAMOS LA COMUNICACIÓN CON ADMOB - SERVIDOR DE ANUNCIOS- - LO HACE
            MobileAds.initialize(
                this
            ) { initializationStatus: InitializationStatus? ->

                //2 UNA VEZ INICIALIZADO, PEDIMOS ANUNCIOS
                Log.d("MIAPP", "inicialización de anuncios completada $initializationStatus")

                val adRequest = AdRequest.Builder().build()

                InterstitialAd.load(this,idBloqueMioAdUnitId, adRequest, object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(TAG, adError.toString())
                        mInterstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d(TAG, "Anuncio Cargado")
                        mInterstitialAd = interstitialAd
                        //3 CUANDO HEMOS RECIBIDO EL ANUNCIO
                        if (mInterstitialAd != null) {
                            mInterstitialAd?.show(this@InicialActivity)
                            //4 LO MOSTRAMOS

                            //Y 5 programamos DETECCIÓN DE EVENTOS si hay interacción con el anuncio
                            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                                override fun onAdClicked() {
                                    // Called when a click is recorded for an ad.
                                    Log.d(TAG, "Ad was clicked.")
                                }

                                override fun onAdDismissedFullScreenContent() {
                                    // Called when ad is dismissed.
                                    Log.d(TAG, "Ad dismissed fullscreen content.")
                                    mInterstitialAd = null
                                }

                                /* override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                                     // Called when ad fails to show.
                                     Log.e(TAG, "Ad failed to show fullscreen content.")
                                     mInterstitialAd = null
                                 }*/

                                override fun onAdImpression() {
                                    // Called when an impression is recorded for an ad.
                                    Log.d(TAG, "Ad recorded an impression.")
                                }

                                override fun onAdShowedFullScreenContent() {
                                    // Called when ad is shown.
                                    Log.d(TAG, "Ad showed fullscreen content.")
                                }
                            }
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.")
                        }
                    }
                })



            }
        }
            .start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial)

        iniciarAnuncios ()

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