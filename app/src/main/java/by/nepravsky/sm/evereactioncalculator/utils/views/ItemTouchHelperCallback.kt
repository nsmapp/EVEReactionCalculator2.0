package by.nepravsky.sm.evereactioncalculator.utils.views

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemTouchListener

class ItemTouchHelperCallback(
    private val itemTouchListener: ItemTouchListener<Int>
): ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFlags = ItemTouchHelper.START
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = true

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
       itemTouchListener.onSwipe(viewHolder.layoutPosition)
    }
}