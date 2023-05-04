package by.nepravsky.domain.entity


sealed class Answer<out D> {

    data class Success<D>(val data: D) : Answer<D>()
    data class Error(val error: Exception) : Answer<Nothing>()

    inline fun collect(
        Success: (D) -> Unit,
        Error: (Exception) -> Unit,
    ): Answer<D> {

        when(this){
            is Success -> Success(data)
            is Error -> Error(error)
        }
        return this
    }

}