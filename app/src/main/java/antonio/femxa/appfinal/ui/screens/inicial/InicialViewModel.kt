package antonio.femxa.appfinal.ui.screens.inicial

import androidx.annotation.RawRes
import androidx.lifecycle.ViewModel
import antonio.femxa.appfinal.domain.usecases.PlaySoundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InicialViewModel @Inject constructor(
    private val playSoundUseCase: PlaySoundUseCase
) : ViewModel() {

    fun playSound(@RawRes soundRes: Int) {
        playSoundUseCase.invoke(soundRes)
    }

}