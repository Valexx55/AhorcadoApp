package antonio.femxa.appfinal.ui.screens.tablero

import androidx.recyclerview.widget.RecyclerView
import antonio.femxa.appfinal.databinding.ItemTableroBinding

class ItemTableroRvViewHolder(
    private val binding: ItemTableroBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(letter: Char?) {
        binding.letra.setText(letter?.toString() ?: "")
    }

}
