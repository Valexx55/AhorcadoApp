package antonio.femxa.appfinal.ui.screens.tablero

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import antonio.femxa.appfinal.databinding.ItemTableroBinding

class ItemTableroRvAdapter(
    private var list: List<Char?>
) : RecyclerView.Adapter<ItemTableroRvViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTableroRvViewHolder {
        return ItemTableroRvViewHolder(
            ItemTableroBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ItemTableroRvViewHolder, position: Int) {
        holder.render(list[position])
    }

    fun updateList(newList: List<Char?>) {
        val diffUtil = ItemTableroDiffUtil(this.list, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        result.dispatchUpdatesTo(this)
        this.list = newList
    }
}
