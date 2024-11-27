package antonio.femxa.appfinal.ui.screens.tablero

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import antonio.femxa.appfinal.databinding.ItemTableroBinding

class ItemTableroRvViewHolder(
    private val binding: ItemTableroBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(letter: Char?) {
        if (letter == null) {
            binding.root.visibility = View.INVISIBLE
        } else {
            binding.letra.setText(letter.toString())
        }
    }

}
