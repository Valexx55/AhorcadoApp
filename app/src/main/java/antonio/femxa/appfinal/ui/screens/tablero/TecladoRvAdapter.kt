package antonio.femxa.appfinal.ui.screens.tablero

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import antonio.femxa.appfinal.databinding.ItemTecladoBinding

class TecladoRvAdapter(
    private val teclas: Array<Char>,
    private val onClick: (Char) -> Boolean?
) : RecyclerView.Adapter<TecladoRvViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TecladoRvViewHolder {
        return TecladoRvViewHolder(
            ItemTecladoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = teclas.size

    override fun onBindViewHolder(holder: TecladoRvViewHolder, position: Int) {
        holder.render(teclas[position], onClick)
    }
}
