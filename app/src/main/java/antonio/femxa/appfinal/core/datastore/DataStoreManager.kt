package antonio.femxa.appfinal.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import antonio.femxa.appfinal.core.datastore.DataStoreDefinitions.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    private val preferences: DataStore<Preferences>
) {

    private object Keys {
        val MUSICA_ON_OFF = booleanPreferencesKey(PreferencesKeys.MUSICA_ON_OFF)
    }

    fun musicaOnOff(): Flow<Boolean> = preferences.data.map {
        it[Keys.MUSICA_ON_OFF] ?: DataStoreDefinitions.Defaults.MUSICA_ON_OFF
    }

    suspend fun setMusicaOnOff(musicaOnOff: Boolean) {
        preferences.edit {
            it[Keys.MUSICA_ON_OFF] = musicaOnOff
        }
    }

}
