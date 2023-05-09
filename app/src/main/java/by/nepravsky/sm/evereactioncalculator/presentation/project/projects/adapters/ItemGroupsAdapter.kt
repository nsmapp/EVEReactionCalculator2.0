package by.nepravsky.sm.evereactioncalculator.presentation.project.projects.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.nepravsky.sm.evereactioncalculator.databinding.ItemGroupBinding
import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.utils.getItemImageURL
import by.nepravsky.sm.evereactioncalculator.presentation.recipe.createitem.adapters.ItemGroupListener
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemSelectedListener
import coil.load

class ItemGroupsAdapter(
    private val itemGroupListener: ItemGroupListener
) : RecyclerView.Adapter<ItemGroupsAdapter.ItemGroupHolder>() {


    private val items = mutableListOf<ItemGroup>()

    fun setItems(items: List<ItemGroup>) {
        this.items.clear()
        this.items.addAll(items)
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

        with(holder.binding) {
            cbIsSelected.setOnCheckedChangeListener(null)
            tvGroupName.text = item.name
            cbIsSelected.isChecked = item.isSelected
            ivGroup.load(getItemImageURL(item.iconId))

            cbIsSelected.setOnCheckedChangeListener { _, isSelected ->
                itemGroupListener.updateGroupSelection(item.id, isSelected)
            }

            root.setOnClickListener {
                cbIsSelected.isChecked = !cbIsSelected.isChecked
            }
        }
    }

    override fun getItemCount(): Int = items.size
}