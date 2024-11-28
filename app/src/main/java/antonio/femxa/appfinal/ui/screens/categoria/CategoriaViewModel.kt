package antonio.femxa.appfinal.ui.screens.categoria

import androidx.annotation.RawRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antonio.femxa.appfinal.core.datastore.DataStoreManager
import antonio.femxa.appfinal.domain.usecases.PlaySoundUseCase
import antonio.femxa.appfinal.domain.usecases.SonidoOnOffUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriaViewModel @Inject constructor(
    private val playSoundUseCase: PlaySoundUseCase,
    private val sonidoOnOffUseCase: SonidoOnOffUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    fun playSongOrContinue(@RawRes resId: Int) {
        playSoundUseCase.playSongOrContinue(resId)
    }

    fun musicaOnOff(): Flow<Boolean> {
        return dataStoreManager.musicaOnOff()
    }

    fun toggleSonido() {
        viewModelScope.launch(Dispatchers.IO) {
            sonidoOnOffUseCase.invoke()
        }
    }

}