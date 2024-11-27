package antonio.femxa.appfinal.ui.screens.tablero

import android.content.Context
import androidx.annotation.RawRes
import androidx.lifecycle.ViewModel
import antonio.femxa.appfinal.R
import antonio.femxa.appfinal.domain.usecases.PlaySoundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TableroViewModel @Inject constructor(
    private val playSoundUseCase: PlaySoundUseCase
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

        palabraOculta = chooseRandomWord(context, categoria).uppercase()
        _palabraFlow.value = TableroUiState.Correct(encriptarPalabra())
    }

    private fun chooseRandomWord(context: Context, categoria: Int): String {
        val arrayCategorias = context.resources.obtainTypedArray(R.array.array_categorias)
        val palabra = arrayCategorias.getTextArray(categoria).random().toString()

        arrayCategorias.recycle()

        return palabra
    }

    private fun encriptarPalabra(): List<Char?> {
        return palabraOculta.map { c ->
            when (c) {
                ' ' -> null
                in letrasUsadas -> c
                else -> ' '
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
            _palabraFlow.value = TableroUiState.Correct(encriptarPalabra())

            if (encriptarPalabra().all { it != null }) {
                onWin.invoke(palabraOculta)
            }
        } else {
            if (++fallos == maxFallos) {
                onLose.invoke(palabraOculta)
            }

            _palabraFlow.value = TableroUiState.Fail(fallos)
            return false
        }

        return true
    }

    fun playSongOrContinue(@RawRes resId: Int) {
        playSoundUseCase.playSongOrContinue(resId)
    }

}
