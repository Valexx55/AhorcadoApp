package antonio.femxa.appfinal.core.mediaplayer

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var mediaPlayer: MediaPlayer? = null

    fun playSound(@RawRes soundResId: Int) {
        release()

        mediaPlayer = MediaPlayer.create(context, soundResId).apply {
            isLooping = true
            setVolume(100f, 100f)
            start()
        }
    }

    fun stopSound() {
        release()
    }

    private fun release() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        mediaPlayer = null
    }

}
