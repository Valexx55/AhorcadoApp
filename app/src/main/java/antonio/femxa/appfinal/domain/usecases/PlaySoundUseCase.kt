package antonio.femxa.appfinal.domain.usecases

import androidx.annotation.RawRes
import antonio.femxa.appfinal.core.mediaplayer.MediaPlayerManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaySoundUseCase @Inject constructor(
    private val mediaPlayerManager: MediaPlayerManager
) {

    fun invoke(@RawRes resId: Int) {
        mediaPlayerManager.playSound(resId)
    }

}