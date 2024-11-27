package antonio.femxa.appfinal


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity


class CategoriaActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var spCategorias: Spinner? = null
    private var mediaPlayer: MediaPlayer? = null
    private var intent: Intent? = null
    private var musicaOnOff: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria)
        this.spCategorias = findViewById<View>(R.id.spinner_categorias) as Spinner

        loadSpinnerCategorias()


        //programo el evento de botón hacia atrás
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                intent = Intent(this@CategoriaActivity, InicialActivity::class.java)

                if (musicaOnOff) {
                    intent!!.putExtra("SonidoOn-Off", true)
                } else {
                    intent!!.putExtra("SonidoOn-Off", false)
                }

                startActivity(intent)
            }
        })
    }

    /**
     * Cada vez que el activity vuelva de una pausa el spinner se coloca en la posicion selecciona una categoria
     */
    override fun onResume() {
        super.onResume()
        val spinner = findViewById<View>(R.id.spinner_categorias) as Spinner
        spinner.setSelection(0)

        musicaOnOff = getIntent().getBooleanExtra("SonidoOn-Off", true)

        val v = findViewById<View>(R.id.btnImagen)
        val ib = v as ImageButton

        mediaPlayer = MediaPlayer.create(this, R.raw.inicio)
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.setVolume(100f, 100f)

        if (musicaOnOff) {
            mediaPlayer!!.start()
            ib.setImageResource(R.drawable.ic_volume_off)
        } else {
            ib.setImageResource(R.drawable.ic_volume_up)
        }

        ponerMusica()
    }

    override fun onPause() {
        super.onPause()
        val v = findViewById<View>(R.id.btnImagen)
        val ib = v as ImageButton

        mediaPlayer!!.stop()

        if (!musicaOnOff) ib.setImageResource(R.drawable.ic_volume_up)
    }

    /**
     * Cargamos el spinner con el array que esta en categorias2.xml
     */
    fun loadSpinnerCategorias() {
        val adapter =
            ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spCategorias!!.adapter = adapter

        spCategorias!!.setOnItemSelectedListener(this)
    }

    /**
     * Cada vez que se cambie el spinner y no sea la posicion 0(selecciona una categoria) carga
     * el array conrrespondiente de esa categoria, obtiene un string aleatorio de ella
     * y se redirige a activity_tablero con el string conseguido
     * @param parent
     * @param view
     * @param pos La posicion del array de categorias
     * @param id
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        if (pos != 0) {
            val array_categorias = resources.obtainTypedArray(R.array.array_categorias)
            val array_especifico = array_categorias.getTextArray(pos)
            array_categorias.recycle()

            val palabra = palabraOculta(array_especifico)

            Log.d("MENSAJE2", palabra)


            intent = Intent(this@CategoriaActivity, TableroActivity::class.java)

            intent!!.putExtra("palabra_clave", palabra)

            val spinner = findViewById<View>(R.id.spinner_categorias) as Spinner

            val aa = spinner.selectedItem.toString()
            intent!!.putExtra("categoria_seleccionada", aa)

            if (musicaOnOff) {
                intent!!.putExtra("SonidoOn-Off", true)
            } else {
                intent!!.putExtra("SonidoOn-Off", false)
            }

            startActivity(intent)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    /**
     * Dado un array de strings te devuelve un string aleatorio de ese array
     * @param array_especifico
     * @return
     */
    fun palabraOculta(array_especifico: Array<CharSequence>): String {
        var palabra: String? = null

        val aleatoria = (Math.random() * array_especifico.size).toInt()
        Log.d("MENSAJE2", aleatoria.toString() + " " + array_especifico.size)
        palabra = array_especifico[aleatoria].toString()

        return palabra
    }


    fun ponerMusica() {
        super.onStart()


        val v = findViewById<View>(R.id.btnImagen)
        val ib = v as ImageButton

        ib.setOnClickListener {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()
                ib.setImageResource(R.drawable.ic_volume_off)
                musicaOnOff = false
            } else {
                ib.setImageResource(R.drawable.ic_volume_up)
                mediaPlayer = MediaPlayer.create(this@CategoriaActivity, R.raw.inicio)
                mediaPlayer!!.isLooping = true
                mediaPlayer!!.setVolume(100f, 100f)
                mediaPlayer!!.start()
                musicaOnOff = true
            }
        }
    }



   /* override fun onBackPressed() {

//super.onBackPressed();

        intent = Intent(this@CategoriaActivity, InicialActivity::class.java)

        if (musicaOnOff) {
            intent!!.putExtra("SonidoOn-Off", true)
        } else {
            intent!!.putExtra("SonidoOn-Off", false)
        }

        startActivity(intent)
        super.onBackPressed()
    }*/
}