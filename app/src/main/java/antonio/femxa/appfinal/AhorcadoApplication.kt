package antonio.femxa.appfinal

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltAndroidApp
class AhorcadoApplication : Application() {
    //TODO Delete when https://github.com/google/dagger/issues/3601 is resolved.
    // It causes a DeprecationWarning on compile time.
    @Inject
    @ApplicationContext
    lateinit var context: Context
}