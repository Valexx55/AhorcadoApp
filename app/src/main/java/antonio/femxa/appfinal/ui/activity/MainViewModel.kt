package antonio.femxa.appfinal.ui.activity

import androidx.lifecycle.ViewModel
import antonio.femxa.appfinal.domain.usecases.StopSoundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stopSoundUseCase: StopSoundUseCase
) : ViewModel() {

    fun stopSound() {
        stopSoundUseCase.invoke()
    }

}