package antonio.femxa.appfinal.ui.screens.victoria

import androidx.annotation.RawRes
import androidx.lifecycle.ViewModel
import antonio.femxa.appfinal.domain.usecases.PlaySoundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VictoriaViewModel @Inject constructor(
    private val playSoundUseCase: PlaySoundUseCase
) : ViewModel() {

    fun playSoundAsync(@RawRes soundResId: Int) {
        playSoundUseCase.playSoundAsync(soundResId)
    }

}