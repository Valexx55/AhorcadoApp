package antonio.femxa.appfinal.domain.usecases

import androidx.annotation.RawRes
import antonio.femxa.appfinal.core.mediaplayer.MediaPlayerManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaySoundUseCase @Inject constructor(
    private val mediaPlayerManager: MediaPlayerManager
) {

    fun playSong(@RawRes resId: Int) {
        mediaPlayerManager.playSong(resId)
    }

    fun playSongOrContinue(@RawRes resId: Int) {
        mediaPlayerManager.playSongOrContinue(resId)
    }

    fun playSoundAsync(@RawRes resId: Int) {
        mediaPlayerManager.playSoundAsync(resId)
    }

}
