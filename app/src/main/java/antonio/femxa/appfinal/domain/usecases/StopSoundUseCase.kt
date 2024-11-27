package antonio.femxa.appfinal.domain.usecases

import antonio.femxa.appfinal.core.mediaplayer.MediaPlayerManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StopSoundUseCase @Inject constructor(
    private val mediaPlayerManager: MediaPlayerManager
) {

    fun invoke() {
        mediaPlayerManager.stopAll()
    }

}