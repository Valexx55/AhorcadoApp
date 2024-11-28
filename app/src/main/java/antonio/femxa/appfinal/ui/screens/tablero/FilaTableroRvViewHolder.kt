package antonio.femxa.appfinal.ui.screens.tablero

import androidx.recyclerview.widget.RecyclerView
import antonio.femxa.appfinal.databinding.FilaTableroBinding
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class FilaTableroRvViewHolder(
    private val binding: FilaTableroBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var adapter: ItemTableroRvAdapter? = null

    fun render(chars: List<Char?>) {
        adapter = adapter ?: ItemTableroRvAdapter(emptyList())

        binding.rvFilaTablero.apply {
            adapter = this@FilaTableroRvViewHolder.adapter
            layoutManager = FlexboxLayoutManager(context).apply {
                justifyContent = JustifyContent.FLEX_START
                alignItems = AlignItems.CENTER
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
        }

        adapter!!.updateList(chars)
    }

}