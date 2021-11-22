package by.nepravsky.sm.evereactioncalculator.utils.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import by.nepravsky.domain.entity.base.PriceSource
import by.nepravsky.domain.entity.base.SolarSystem
import by.nepravsky.domain.entity.presenter.SearchLanguage
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemClickListener

class DropDownAdapter<T>(
    context: Context,
    private val resource: Int,
    private val objects: List<T>,
    private val itemClickListener: ItemClickListener<T>
) : ArrayAdapter<T>(context,
    resource,
    objects) {

    val ctx = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(ctx).inflate(resource, parent, false)
        val tvName = view.findViewById<TextView>(android.R.id.text1)
        val item = objects[position]

        view.setOnClickListener{
            itemClickListener.onItemClick(item)
        }
        tvName.text = when(item){
            is SearchLanguage -> item.name
            is SolarSystem -> item.name
            is PriceSource -> item.name
            else -> error("illegal state, unknown format")
        }
        return view


    }


}