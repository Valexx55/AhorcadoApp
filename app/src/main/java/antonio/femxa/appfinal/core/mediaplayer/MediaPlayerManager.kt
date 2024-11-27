package antonio.femxa.appfinal.core.mediaplayer

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.annotation.RawRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var mediaPlayer: MediaPlayer? = null

    @RawRes
    private var lastMusicResId: Int? = null

    private val soundMap = mutableMapOf<@receiver:RawRes Int, MediaPlayer>()

    fun playSong(@RawRes soundResId: Int) {
        release(mediaPlayer)

        mediaPlayer = MediaPlayer.create(context, soundResId).apply {
            isLooping = true
            setVolume(100f, 100f)
            start()
        }

        lastMusicResId = soundResId
    }

    fun playSongOrContinue(@RawRes soundResId: Int) {
        if (soundResId == lastMusicResId) {
            Log.v("MediaPlayerManager", "Already playing this song")
            return
        }

        playSong(soundResId)
    }

    fun playSoundAsync(@RawRes soundResId: Int) {
        val player = MediaPlayer.create(context, soundResId).apply {
            setVolume(100f, 100f)
            setOnCompletionListener {
                this@MediaPlayerManager.release(it)
                soundMap.remove(soundResId)
            }
            start()
        }

        soundMap[soundResId] = player
    }

    fun stopAll() {
        release(mediaPlayer)
        for ((@RawRes k, v) in soundMap) {
            release(v)
            soundMap.remove(k)
        }
    }

    private fun release(mediaPlayer: MediaPlayer?) {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
    }

}
