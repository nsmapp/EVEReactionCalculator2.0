package by.nepravsky.domain.utils



fun CharSequence?.parseToInt(default: Int): Int {
    if(this.isNullOrEmpty()) return  default
    return try { this.toString().trim().toInt()}
                catch (E:Exception){ default }
}