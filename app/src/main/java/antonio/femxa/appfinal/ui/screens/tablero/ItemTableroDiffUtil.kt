package antonio.femxa.appfinal.ui.screens.tablero

import androidx.recyclerview.widget.DiffUtil

class ItemTableroDiffUtil(
    private val oldList: List<Char?>,
    private val newList: List<Char?>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemPosition == newItemPosition
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}
