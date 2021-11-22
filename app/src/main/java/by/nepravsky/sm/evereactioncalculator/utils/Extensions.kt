package by.nepravsky.sm.evereactioncalculator.utils

import android.content.Context
import android.view.View
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

fun CharSequence?.parseToInt(default: Int): Int {
    if (this.isNullOrEmpty()) return default
    return try {
        this.toString().toInt()
    } catch (E: Exception) {
        default
    }
}

fun Double.toISK(): String {
    var i = this
    var k = ""
    while(i > 1000){
        i /= 1000
        k = "k$k"
    }
    return "${DecimalFormat("###,##0.00#").format(i)} $k ISK"
}

fun Double.toVolume(): String = "${DecimalFormat("###,##0.0#").format(this)} m³"


fun Int.toPC(): String {
    var i = this.toDouble()
    var k = ""
    while(i > 1000){
        i /= 1000.0
        k = "k$k"
    }
    return "${DecimalFormat("###,##0.00#").format(i)}$k pc."
}

fun Int.toTime(): String{
    val hour: Long
    val min: Long

    val day: Long = TimeUnit.SECONDS.toDays(this.toLong())
    hour = TimeUnit.SECONDS.toHours(this.toLong()) - TimeUnit.DAYS.toHours(day)
    min = (TimeUnit.SECONDS.toMinutes(this.toLong()) - TimeUnit.DAYS.toMinutes(day)
            - TimeUnit.HOURS.toMinutes(this.toLong()))
    val sec: Long = TimeUnit.SECONDS.toSeconds(this.toLong()) - TimeUnit.MINUTES.toSeconds(
        TimeUnit.SECONDS.toMinutes(this.toLong())
    )
    var result = ""
    if (day > 0) result = "${day}d"
    if (hour > 0) result = "$result ${hour}h"
    if (min > 0) result = "$result ${min}m"
    if (sec > 0) result = "$result ${sec}sec"

    return result
}

fun View.hideAndDisabled(){
    this.visibility = View.GONE
    this.isClickable = false
    this.isEnabled = false
    this.isFocusable = false
}

fun Context.getAppName(): String = applicationInfo.loadLabel(packageManager).toString()
