package antonio.femxa.appfinal.ui.screens.tablero

import androidx.recyclerview.widget.DiffUtil

class FilaTableroDiffUtil(
    private val oldList: List<List<Char?>>,
    private val newList: List<List<Char?>>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemPosition == newItemPosition
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].size == newList[newItemPosition].size && oldList[oldItemPosition].zip(
            newList[newItemPosition]
        ).all { (old, new) -> old == new }
    }
}
