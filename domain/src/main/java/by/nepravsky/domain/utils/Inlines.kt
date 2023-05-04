package by.nepravsky.domain.utils

import by.nepravsky.domain.entity.Answer
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext


inline fun <T> runFun(body: () -> T): Result<T> {
    return try {
        Result.Success(body.invoke())
    } catch (e: Exception) {
        Result.Error(e)
    }
}

suspend fun <T> runFunc(body: () -> T): Answer<T> {
    return withContext(Main){
        try { withContext(IO){ Answer.Success(body.invoke()) }
        } catch (e: Exception) { Answer.Error(e)}

    }
}