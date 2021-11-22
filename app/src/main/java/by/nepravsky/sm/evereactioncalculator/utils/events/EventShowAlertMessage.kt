package by.nepravsky.sm.evereactioncalculator.utils.events

class EventShowAlertMessage(private val _message: String): Event {
    override val message: String
        get() = _message
}