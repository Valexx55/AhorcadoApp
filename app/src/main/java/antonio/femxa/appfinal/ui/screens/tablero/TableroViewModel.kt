package antonio.femxa.appfinal.ui.screens.tablero

import android.content.Context
import androidx.annotation.RawRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antonio.femxa.appfinal.R
import antonio.femxa.appfinal.core.datastore.DataStoreManager
import antonio.femxa.appfinal.domain.usecases.PlaySoundUseCase
import antonio.femxa.appfinal.domain.usecases.SonidoOnOffUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TableroViewModel @Inject constructor(
    private val playSoundUseCase: PlaySoundUseCase,
    private val sonidoOnOffUseCase: SonidoOnOffUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private lateinit var onWin: (String) -> Unit
    private lateinit var onLose: (String) -> Unit

    private val maxFallos = 6
    private var fallos = 0

    private lateinit var palabraOculta: String
    private val letrasUsadas: MutableSet<Char> = mutableSetOf()

    private val _palabraFlow: MutableStateFlow<TableroUiState> =
        MutableStateFlow(TableroUiState.Waiting)
    val palabraFlow: StateFlow<TableroUiState> = _palabraFlow

    fun startGame(
        context: Context,
        categoria: Int,
        onWin: (String) -> Unit,
        onLose: (String) -> Unit
    ) {
        this.onWin = onWin
        this.onLose = onLose

        palabraOculta = escogerPalabraDeCategoria(context, categoria).uppercase()
        _palabraFlow.value = TableroUiState.Correct(encriptarPalabra())
    }

    private fun escogerPalabraDeCategoria(context: Context, categoria: Int): String {
        val arrayCategorias = context.resources.obtainTypedArray(R.array.array_categorias)
        val palabra = arrayCategorias.getTextArray(categoria).random().toString()

        arrayCategorias.recycle()

        return palabra
    }

    private fun encriptarPalabra(): List<List<Char?>> {
        return palabraOculta.split(" ").map { palabra ->
            palabra.toCharArray().map {
                if (!it.isLetter() || letrasUsadas.contains(it)) it else null
            }
        }
    }

    fun tryLetter(letter: Char): Boolean? {
        if (letrasUsadas.contains(letter)) return null

        letrasUsadas.add(letter)

        val palabra = palabraOculta.toCharArray().map {
            if (it == letter) it else null
        }

        if (palabra.contains(letter)) {
            val palabraEncriptada = encriptarPalabra()

            if (palabraEncriptada.flatten().all { it != null }) {
                onWin.invoke(palabraOculta)
            }

            _palabraFlow.value = TableroUiState.Correct(palabraEncriptada)
            return true
        }

        if (++fallos == maxFallos) {
            onLose.invoke(palabraOculta)
        }

        _palabraFlow.value = TableroUiState.Fail(fallos)
        return false
    }

    fun playSongOrContinue(@RawRes resId: Int) {
        playSoundUseCase.playSongOrContinue(resId)
    }

    fun toggleSound() {
        viewModelScope.launch(Dispatchers.IO) {
            sonidoOnOffUseCase.invoke()
        }
    }

    fun musicaOnOff(): Flow<Boolean> {
        return dataStoreManager.musicaOnOff()
    }

}
