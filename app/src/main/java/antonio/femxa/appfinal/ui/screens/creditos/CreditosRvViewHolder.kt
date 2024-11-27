package antonio.femxa.appfinal.ui.screens.creditos

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import antonio.femxa.appfinal.databinding.ItemCreditosBinding
import antonio.femxa.appfinal.domain.model.PersonaCreditos

class CreditosRvViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding: ItemCreditosBinding = ItemCreditosBinding.bind(view)

    fun render(persona: PersonaCreditos) {
        binding.nombre.text = persona.nombre
        binding.contact.text = persona.contacto
        binding.github.text = persona.github
        binding.form.text = persona.from
    }

}