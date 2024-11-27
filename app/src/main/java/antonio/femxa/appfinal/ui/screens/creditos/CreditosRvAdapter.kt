package antonio.femxa.appfinal.ui.screens.creditos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import antonio.femxa.appfinal.R
import antonio.femxa.appfinal.domain.model.PersonaCreditos

class CreditosRvAdapter(
    private val list: List<PersonaCreditos>
) : RecyclerView.Adapter<CreditosRvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditosRvViewHolder {
        return CreditosRvViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_creditos, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CreditosRvViewHolder, position: Int) {
        holder.render(list[position])
    }
}