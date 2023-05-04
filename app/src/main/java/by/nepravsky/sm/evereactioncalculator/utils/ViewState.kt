package by.nepravsky.sm.evereactioncalculator.utils


sealed class ViewState<out T>() {

    object Loading: ViewState<Nothing>()
    data class Success<T>(val data: T): ViewState<T>()
    data class ShareReaction<T>(val data: String): ViewState<T>()
    data class Error<T>(val messageId: Int): ViewState<T>()
}