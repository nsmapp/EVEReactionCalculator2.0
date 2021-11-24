package by.nepravsky.sm.evereactioncalculator.presentation.recipe.createitem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.nepravsky.sm.evereactioncalculator.databinding.ItemGroupBinding
import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.presenter.ItemGroupSelected
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemSelectedListener

class ItemGroupsAdapter(
    private val itemSelectedListener: ItemSelectedListener<List<ItemGroup>>
) : RecyclerView.Adapter<ItemGroupsAdapter.ItemGroupHolder>() {


    private val items = mutableListOf<ItemGroupSelected>()

    fun setItems(items: List<ItemGroup>) {
        this.items.clear()
        this.items.addAll(items.map { ItemGroupSelected(it, it.isFormula) })
        sendSelected()
        notifyDataSetChanged()
    }

    inner class ItemGroupHolder(val binding: ItemGroupBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemGroupHolder =
        ItemGroupHolder(
            ItemGroupBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemGroupHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            cbIsSelected.setOnCheckedChangeListener(null)
            tvGroupName.text = item.itemGroup.name
            cbIsSelected.isChecked = item.isSelected
        }
        holder.binding.cbIsSelected.setOnCheckedChangeListener { _, b ->
            items[position].isSelected = b
            sendSelected()
        }
    }

    private fun sendSelected() {
        itemSelectedListener.selectedItems(
            items.filter { it.isSelected }
                .map { it.itemGroup }
        )
    }

    override fun getItemCount(): Int = items.size
}