package antonio.femxa.appfinal.ui.screens.creditos

import android.content.res.XmlResourceParser
import androidx.annotation.RawRes
import androidx.lifecycle.ViewModel
import antonio.femxa.appfinal.domain.model.PersonaCreditos
import antonio.femxa.appfinal.domain.usecases.PlaySoundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.xmlpull.v1.XmlPullParser
import javax.inject.Inject

@HiltViewModel
class CreditosViewModel @Inject constructor(
    private val playSoundUseCase: PlaySoundUseCase
) : ViewModel() {

    fun playSongOrContinue(@RawRes soundRes: Int) {
        playSoundUseCase.playSongOrContinue(soundRes)
    }

    fun playSoundAsync(@RawRes soundRes: Int) {
        playSoundUseCase.playSoundAsync(soundRes)
    }

    fun parseCredits(xmlParser: XmlResourceParser): List<PersonaCreditos> {
        val list: MutableList<PersonaCreditos> = mutableListOf()

        var eventType = xmlParser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && xmlParser.name == "persona") {
                val name = xmlParser.getAttributeValue(null, "name")
                val contact = xmlParser.getAttributeValue(null, "contact")
                val github = xmlParser.getAttributeValue(null, "github")
                val from = xmlParser.getAttributeValue(null, "from")

                list.add(PersonaCreditos(name, contact, github, from))
            }
            eventType = xmlParser.next()
        }

        return list
    }

}
