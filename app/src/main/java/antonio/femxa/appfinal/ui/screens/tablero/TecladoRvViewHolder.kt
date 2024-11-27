package antonio.femxa.appfinal.ui.screens.tablero

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import antonio.femxa.appfinal.databinding.ItemTecladoBinding

class TecladoRvViewHolder(
    private val binding: ItemTecladoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(letter: Char, onClick: (Char) -> Boolean?) {
        binding.root.text = letter.toString()
        binding.root.setOnClickListener {
            val result = onClick.invoke(letter)
            result?.let {
                binding.root.isEnabled = false
                binding.root.setTextColor(if (it) Color.GREEN else Color.RED)
            }
        }
    }

}
