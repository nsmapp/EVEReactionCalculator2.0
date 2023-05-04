package by.nepravsky.sm.evereactioncalculator.utils.views

import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.transition.Explode
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager

val handler = Handler(Looper.getMainLooper())


fun slideToTop(view: View, rootView: ViewGroup){

    val transition = Explode()
    transition.duration = 250
    transition.addTarget(view)
    TransitionManager.beginDelayedTransition(rootView, transition)

    view.visibility = if(view.isVisible) View.GONE else View.VISIBLE
}

fun slideToBottom(view: View, rootView: ViewGroup){

    val transition = Slide(Gravity.BOTTOM)
    transition.duration = 250
    transition.addTarget(view)
    TransitionManager.beginDelayedTransition(rootView, transition)

    view.visibility = if(view.isVisible) View.GONE else View.VISIBLE
}

fun slideToEnd(view: View, rootView: ViewGroup){

    val transition = Slide(Gravity.END)
    transition.duration = 250
    transition.addTarget(view)
    TransitionManager.beginDelayedTransition(rootView, transition)

    view.visibility = if(view.isVisible) View.GONE else View.VISIBLE
}