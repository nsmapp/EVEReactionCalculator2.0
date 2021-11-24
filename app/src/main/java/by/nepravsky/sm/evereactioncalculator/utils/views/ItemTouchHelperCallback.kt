package by.nepravsky.sm.evereactioncalculator.utils.views

import android.graphics.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.nepravsky.sm.evereactioncalculator.R
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemTouchListener

class ItemTouchHelperCallback(
    private val itemTouchListener: ItemTouchListener<Int>
): ItemTouchHelper.Callback() {

    private val backgroundPaint = Paint().apply { color =  Color.RED }
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    init {

    }

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




    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val view = viewHolder.itemView
        val itemHeight = view.bottom - view.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            c.drawRect(
                view.right + dX,
                view.top.toFloat(),
                view.right.toFloat(),
                view.bottom.toFloat(),
                clearPaint
            )
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        if (dX < 0) {
            val deleteIcon = ContextCompat.getDrawable(
                viewHolder.itemView.context,
                R.drawable.outline_delete_24
            )
            val intrinsicWidth = deleteIcon?.intrinsicWidth
            val intrinsicHeight = deleteIcon?.intrinsicHeight
            c.drawRoundRect(
                RectF(
                    (view.right + dX.toInt() - 15).toFloat(),
                    view.top.toFloat(),
                    view.right.toFloat(),
                    view.bottom.toFloat()
                ), 16f, 16f, backgroundPaint
            )
            val deleteIconTop = view.top + (itemHeight - intrinsicHeight!!) / 2
            val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
            val deleteIconLeft = view.right - deleteIconMargin - intrinsicWidth!!
            val deleteIconRight = view.right - deleteIconMargin
            val deleteIconBottom = deleteIconTop + intrinsicHeight

            deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
            deleteIcon.draw(c)
            
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
       itemTouchListener.onSwipe(viewHolder.layoutPosition)
    }
}