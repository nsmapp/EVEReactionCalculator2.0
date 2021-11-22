package by.nepravsky.domain.entity.domain

sealed class Lang(val id: Int){
    object EN: Lang(12)
    object FR: Lang(13)
    object DE: Lang(14)
    object RU: Lang(15)
    object JA: Lang(16)
    object ZH: Lang(17)
}
