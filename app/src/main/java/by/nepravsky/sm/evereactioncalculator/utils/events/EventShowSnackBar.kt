package by.nepravsky.sm.evereactioncalculator.utils.events

class EventShowSnackBar(private val _message: String): Event {
    override val message: String
        get() = _message
}