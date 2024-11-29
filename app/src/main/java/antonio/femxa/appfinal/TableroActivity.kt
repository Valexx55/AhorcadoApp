package antonio.femxa.appfinal

//import android.R
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import java.util.Locale


class TableroActivity : AppCompatActivity() {
    private var palabra: String? = null
    private var palabraAux: String? = null
    private val array_pics = intArrayOf(
        R.drawable.ic_cuerda,
        R.drawable.ic_cabeza,
        R.drawable.ic_cuerpo,
        R.drawable.ic_brazo,
        R.drawable.ic_brazos,
        R.drawable.ic_pierna
    )
    private var contador: Int = 0
    private var tamaño_palabra: Int = 0
    private var contador_aciertos: Int = 0
    private var intent: Intent? = null
    private var sonidoOnOff: Boolean = false
    private var mediaPlayer: MediaPlayer? = null

    val idUnitBannerPrueba = "ca-app-pub-3940256099942544/9214589741"
    val idUnitBannerMio = "ca-app-pub-9910445535228761/9528197815"
    lateinit var  adView: AdView

    //devuelve el tamño del anuncio

    // Get the ad size with screen width.
    private val adSize: AdSize
        get() {
            val displayMetrics = resources.displayMetrics
            val adWidthPixels =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val windowMetrics: WindowMetrics = this.windowManager.currentWindowMetrics
                    windowMetrics.bounds.width()
                } else {
                    displayMetrics.widthPixels
                }
            val density = displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }


    fun crearAnuncio ()

    {


        //obtengo un anuncio
        val adView = AdView(this)
        adView.adUnitId = idUnitBannerMio//idUnitBannerPrueba
        adView.setAdSize(adSize)
        this.adView = adView

        /**
         * // Replace ad container with new ad view.
         * binding.adViewContainer.removeAllViews()
         * binding.adViewContainer.addView(adView)
         */
        //lo meto en su caja
        val adviewxml = findViewById<AdView> (R.id.anuncio)
        adviewxml.removeAllViews()
        adviewxml.addView(this.adView)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object: AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d("MIAPP","onAdClicked()" )
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.d("MIAPP","onAdClosed()" )
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
                Log.d("MIAPP","onAdFailedToLoad()" )
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
                Log.d("MIAPP","onAdImpression()" )
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d("MIAPP","onAdLoaded()" )
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d("MIAPP","onAdOpened()" )
            }
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablero)
        val v = findViewById<View>(R.id.btnImagen)
        val ib = v as ImageButton

        crearAnuncio ()

        contador = 0
        contador_aciertos = 0

        palabra = getIntent().getStringExtra("palabra_clave")

        ///////// sonido
        mediaPlayer = MediaPlayer.create(this, R.raw.durantejugar)
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.setVolume(100f, 100f)

        sonidoOnOff = getIntent().getBooleanExtra("SonidoOn-Off", true)

        if (sonidoOnOff) {
            mediaPlayer!!.start()
        } else {
            ib.setImageResource(R.drawable.ic_volume_off)
        }

        ib.setOnClickListener {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()
                ib.setImageResource(R.drawable.ic_volume_off)
            } else {
                ib.setImageResource(R.drawable.ic_volume_up)
                mediaPlayer!!.start()
            }
        }


        palabraAux = palabra

        tamaño_palabra = obtenerTamañoPalabra(palabraAux!!)

        val imageView = findViewById<View>(R.id.imagenes_ahorcado) as ImageView
        imageView.setImageResource(array_pics[contador])


        dibujarPanel(palabra)
        var fila1: TableRow? = findViewById<View>(R.id.lugar_inflado) as TableRow
        var fila2: TableRow? = findViewById<View>(R.id.lugar_inflado2) as TableRow
        var fila3: TableRow? = findViewById<View>(R.id.lugar_inflado3) as TableRow
        var fila4: TableRow? = findViewById<View>(R.id.lugar_inflado4) as TableRow

        fila1 = if ((fila1!!.childCount == 0)) null else fila1
        fila2 = if ((fila2!!.childCount == 0)) null else fila2
        fila3 = if ((fila3!!.childCount == 0)) null else fila3
        fila4 = if ((fila4!!.childCount == 0)) null else fila4

        identificarEditText(fila1, fila2, fila3, fila4)
        ocultarEspacios(palabra)

        val textViewCategoria = findViewById<View>(R.id.textviewcategoria) as TextView

        val categoria = getIntent().getStringExtra("categoria_seleccionada")

        textViewCategoria.text = categoria
    }

    fun mostrarLetra(letra: String, palabra: String) {
        val letrita = letra[0]
        for (i in 0 until palabra.length) {
            if (letrita == palabra[i]) {
                val et = findViewById<View>(i) as EditText
                et.setText(letrita.toString() + "")
                Log.d("MENSAJE", "HA ENCONTRADO LA LETRA $letrita")
            }
        }
    }

    fun ocultarEspacios(palabra: String?) {
        val letrita = ' '
        for (i in 0 until palabra!!.length) {
            if (letrita == palabra[i]) {
                val et = findViewById<View>(i) as EditText
                et.visibility = View.INVISIBLE
                Log.d("MENSAJE", "HA ENCONTRADO LA LETRA $letrita")
            }
        }
    }

    private fun dibujarPanel(palabra_oculta: String?) {
        val fila1 = findViewById<View>(R.id.lugar_inflado) as ViewGroup
        val fila2 = findViewById<View>(R.id.lugar_inflado2) as ViewGroup
        val fila3 = findViewById<View>(R.id.lugar_inflado3) as ViewGroup
        val fila4 = findViewById<View>(R.id.lugar_inflado4) as ViewGroup

        val longi_palabra = palabra_oculta!!.length
        val layoutInflater = this@TableroActivity.layoutInflater //o LayoutInflater.from(a)

        val lista_palabra =
            palabra_oculta.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        /*for (int z = 0; z < lista_palabra.length-1; z++)
       {
           lista_palabra[z] = lista_palabra[z] + " ";
       }*/
        var pos_palabra = 0
        var n_linea = 1
        var caracteres_linea_actual = 0


        for (i in 0 until longi_palabra) {
            // CONTAR LÍNEAS CON SWITCH
            when (n_linea) {
                1 -> if (palabra_oculta[i] == ' ') {
                    pos_palabra++
                    val v1: View = layoutInflater.inflate(R.layout.panel, fila1, true)
                    if (lista_palabra[pos_palabra].length + caracteres_linea_actual < 10) {
                        caracteres_linea_actual++
                        Log.d(
                            "MENSAJE",
                            "if Case 1: letra " + palabra_oculta[i] + ", linea " + n_linea
                        )
                    } else {
                        caracteres_linea_actual = 1
                        n_linea = 2
                        Log.d(
                            "MENSAJE",
                            "Else Case 1: letra " + palabra_oculta[i] + ", linea " + n_linea
                        )
                    }
                } else {
                    val v1: View = layoutInflater.inflate(R.layout.panel, fila1, true)
                    caracteres_linea_actual++
                    Log.d("MENSAJE", "Case 1: letra " + palabra_oculta[i] + ", linea " + n_linea)
                }

                2 -> if (palabra_oculta[i] == ' ') {
                    pos_palabra++
                    val v1: View = layoutInflater.inflate(R.layout.panel, fila2, true)
                    if (lista_palabra[pos_palabra].length + caracteres_linea_actual < 10) {
                        caracteres_linea_actual++
                        Log.d(
                            "MENSAJE",
                            "Case 2: letra " + palabra_oculta[i] + ", linea " + n_linea
                        )
                    } else {
                        caracteres_linea_actual = 1
                        n_linea = 3
                        Log.d(
                            "MENSAJE",
                            "Else Case 2: letra " + palabra_oculta[i] + ", linea " + n_linea
                        )
                    }
                } else {
                    val v1: View = layoutInflater.inflate(R.layout.panel, fila2, true)
                    caracteres_linea_actual++
                    Log.d("MENSAJE", "Case 2: letra " + palabra_oculta[i] + ", linea " + n_linea)
                }


                3 -> if (palabra_oculta[i] == ' ') {
                    pos_palabra++

                    val v1: View = layoutInflater.inflate(R.layout.panel, fila3, true)
                    if (lista_palabra[pos_palabra].length + caracteres_linea_actual < 10) {
                        caracteres_linea_actual++
                        Log.d(
                            "MENSAJE",
                            "Case 3: letra " + palabra_oculta[i] + ", linea " + n_linea
                        )
                    } else {
                        caracteres_linea_actual = 1
                        n_linea = 4
                        Log.d(
                            "MENSAJE",
                            "Else Case 3: letra " + palabra_oculta[i] + ", linea " + n_linea
                        )
                    }
                } else {
                    val v1: View = layoutInflater.inflate(R.layout.panel, fila3, true)
                    caracteres_linea_actual++
                    Log.d("MENSAJE", "Case 3: letra " + palabra_oculta[i] + ", linea " + n_linea)
                }


                4 -> if (palabra_oculta[i] == ' ') {
                    pos_palabra++

                    val v1: View = layoutInflater.inflate(R.layout.panel, fila4, true)
                    if (lista_palabra[pos_palabra].length + caracteres_linea_actual < 10) {
                        caracteres_linea_actual++
                        Log.d(
                            "MENSAJE",
                            "Case 4: letra " + palabra_oculta[i] + ", linea " + n_linea
                        )
                    } else {
                        Log.d("MENSAJE", "La cadena tiene más extensión de la permitida")
                    }
                } else {
                    val v1: View = layoutInflater.inflate(R.layout.panel, fila4, true)
                    caracteres_linea_actual++
                    Log.d("MENSAJE", "Case 4: letra " + palabra_oculta[i] + ", linea " + n_linea)
                }

                else -> {}
            }
        }
    }

    fun identificarEditText(
        rowLugarInflado1: ViewGroup?,
        rowLugarInflado2: ViewGroup?,
        rowLugarInflado3: ViewGroup?,
        rowLugarInflado4: ViewGroup?
    ) {
        var cont_aux = 0

        try {
            for (i in 0 until rowLugarInflado1!!.childCount) {
                val linear = rowLugarInflado1.getChildAt(i) as ViewGroup
                val et = linear.getChildAt(0) as EditText
                et.id = i
                Log.d("MENSAJE", "editado EditText n: $i")
                Log.d("MENSAJE", "Id de EditText: " + et.id)
                cont_aux++
            }

            Log.d("MENSAJE", rowLugarInflado2.toString())
            if (rowLugarInflado2 != null) {
                for (i in 0 until (rowLugarInflado2.childCount)) {
                    val linear = rowLugarInflado2.getChildAt(i) as ViewGroup
                    val et = linear.getChildAt(0) as EditText
                    Log.d("MENSAJE", et.toString())
                    et.id = cont_aux
                    Log.d("MENSAJE", "editado EditText n: $cont_aux")
                    Log.d("MENSAJE", "Id de EditText: " + et.id)
                    cont_aux++
                }
            } else {
                Log.d("MENSAJE", "no hay segunda fila")
            }

            if (rowLugarInflado3 != null) {
                var cont_aux2 = 0
                cont_aux2 = cont_aux
                for (i in 0 until (rowLugarInflado3.childCount)) {
                    val linear = rowLugarInflado3.getChildAt(i) as ViewGroup
                    val et = linear.getChildAt(0) as EditText
                    et.id = cont_aux
                    Log.d("MENSAJE", "editado EditText n: $cont_aux")
                    Log.d("MENSAJE", "Id de EditText: " + et.id)
                    cont_aux++
                }
            } else {
                Log.d("MENSAJE", "no hay tercera fila")
            }

            if (rowLugarInflado4 != null) {
                var cont_aux2 = 0
                cont_aux2 = cont_aux
                for (i in 0 until (rowLugarInflado4.childCount)) {
                    val linear = rowLugarInflado4.getChildAt(i) as ViewGroup
                    val et = linear.getChildAt(0) as EditText
                    et.id = cont_aux
                    Log.d("MENSAJE", "editado EditText n: $cont_aux")
                    Log.d("MENSAJE", "Id de EditText: " + et.id)
                    cont_aux++
                }
            } else {
                Log.d("MENSAJE", "no hay cuarta fila")
            }
        } catch (t: Throwable) {
            Log.e("MENSAJE", "ERROR", t)
        }
    }

    fun escribirNumero(boton: View) {
        // declaramos variables y hacemos el casteo del boton para usarle
        var palabra = getPalabra()
        Log.d("MENSAJE", palabra!!)
        val btnPulsado = boton as Button
        val pulsado = btnPulsado.text.toString() //cogemos el texto del boton pulsado


        //nos creamos una variable boleana que nos dara si es falso o verdadero con lo que salga del metodo
        // haremos una condicion if en la que nos dira si la encuentra que cambie el texto del boton y lo ponga del color verde
        //sino que la ponga de color rojo y no deje pulsarla otra vez la deshabilita
        palabra = palabra.uppercase(Locale.getDefault())
        val encontrada = buscarLetra(pulsado, palabra)
        if (encontrada) {
            letraAcertada(btnPulsado)
            mostrarLetra(pulsado, palabra)
        } else {
            letraFallada(btnPulsado)
        }
    }

    /**
     * Se cambia a verde el boton introducido y si el contador_aciertos es igual al tamaño de la palabra oculta
     * se redirige a VictoriaActivity
     * @param button
     */
    fun letraAcertada(button: Button) {
        button.setTextColor(Color.rgb(34, 153, 84))

        Log.d("MENSAJE", "$contador_aciertos contador")
        Log.d("MENSAJE", "$tamaño_palabra tamaño")

        if (contador_aciertos == tamaño_palabra) {
            intent = Intent(this@TableroActivity, VictoriaActivity::class.java)

            intent!!.putExtra("palabra_clave", palabra)

            intent!!.putExtra("SonidoOn-Off", sonidoOnOff)

            startActivity(intent)
        }
    }

    /**
     * Cambia el color a rojo e inutiliza el boton introducido, si el contador de fallos
     * es igual a 6 se redirige a DerrotaActivity, si no, cambia la imagen del ahoracado
     * @param button
     */
    fun letraFallada(button: Button) {
        button.setTextColor(Color.RED)
        button.isEnabled = false
        contador++

        if (contador == 6) {
            intent = Intent(this@TableroActivity, DerrotaActivity::class.java)

            intent!!.putExtra("palabra_clave", palabra)

            intent!!.putExtra("SonidoOn-Off", sonidoOnOff)

            startActivity(intent)
        } else {
            val imageView = findViewById<View>(R.id.imagenes_ahorcado) as ImageView
            imageView.setImageResource(array_pics[contador])
        }
    }

    /**
     * Busca en una palabra una letra introducidas, cada vez que encuentre esa letra en la
     * palabra se añade mas uno al contador_palabra_verdadero y devuelve un true
     * @param letra
     * @param palabra
     * @return
     */
    fun buscarLetra(letra: String, palabra: String): Boolean {
        var encontrado = false
        val letrita = letra[0]
        for (i in 0 until palabra.length) {
            if (letrita == palabra[i]) {
                encontrado = true
                contador_aciertos++
            }
        }

        return encontrado
    }

    fun getPalabra(): String? {
        return palabra
    }

    /**
     * Dada una palabra introducida te dice su numero de letras, no cuenta los espacios
     * @param palabra
     * @return numero de posiciones de esa palabra
     */
    fun obtenerTamañoPalabra(palabra: String): Int {
        var palabra = palabra
        var contador = 0

        palabra = palabra.replace(" ", "")

        for (i in 0 until palabra.length) {
            contador++
        }

        return contador
    }


    override fun onPause() {
        super.onPause()
        mediaPlayer!!.stop()
    }
}