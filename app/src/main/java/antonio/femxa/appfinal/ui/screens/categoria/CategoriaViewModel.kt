package antonio.femxa.appfinal.ui.screens.categoria

import androidx.annotation.RawRes
import androidx.lifecycle.ViewModel
import antonio.femxa.appfinal.domain.usecases.PlaySoundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriaViewModel @Inject constructor(
    private val playSoundUseCase: PlaySoundUseCase
) : ViewModel() {

    fun playSongOrContinue(@RawRes resId: Int) {
        playSoundUseCase.playSongOrContinue(resId)
    }

}