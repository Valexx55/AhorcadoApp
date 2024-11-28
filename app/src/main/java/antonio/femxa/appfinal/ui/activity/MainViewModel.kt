package antonio.femxa.appfinal.ui.activity

import androidx.lifecycle.ViewModel
import antonio.femxa.appfinal.core.datastore.DataStoreManager
import antonio.femxa.appfinal.domain.usecases.StopSoundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stopSoundUseCase: StopSoundUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    fun stopSound() {
        stopSoundUseCase.invoke()
    }

    fun musicaOnOff(): Flow<Boolean> {
        return dataStoreManager.musicaOnOff()
    }

}