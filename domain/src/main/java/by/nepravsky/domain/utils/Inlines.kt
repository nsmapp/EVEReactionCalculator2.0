package by.nepravsky.domain.utils


inline fun <T> runFun(body: () -> T): Result<T> {
    return try {
        Result.Success(body.invoke())
    } catch (e: Exception) {
        Result.Error(e)
    }
}