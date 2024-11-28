package antonio.femxa.appfinal.domain.usecases

import antonio.femxa.appfinal.core.datastore.DataStoreManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SonidoOnOffUseCase @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {

    suspend fun invoke(b: Boolean) {
        dataStoreManager.setMusicaOnOff(b)
    }

    suspend fun invoke() {
        invoke(!dataStoreManager.musicaOnOff().first())
    }

}