package antonio.femxa.appfinal.ui.screens.tablero

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import antonio.femxa.appfinal.databinding.FilaTableroBinding

class FilaTableroRvAdapter(
    private var list: List<List<Char?>>
) : RecyclerView.Adapter<FilaTableroRvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilaTableroRvViewHolder {
        return FilaTableroRvViewHolder(
            FilaTableroBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: FilaTableroRvViewHolder, position: Int) {
        holder.render(list[position])
    }

    fun updateList(newList: List<List<Char?>>) {
        val diffUtil = FilaTableroDiffUtil(this.list, newList)
        val result = DiffUtil.calculateDiff(diffUtil, false)
        result.dispatchUpdatesTo(this)
        this.list = newList
    }
}