package by.nepravsky.domain.entity.request

class Settings(
    var languageId: Int = DEFAULT_LANGUAGE,
    var systemId: Int = DEFAULT_SYSTEM,
    var regionId: Int = DEFAULT_REGION,
    var me: Int = DEFAULT_ME,
    var te: Int = 0,
    var subME: Int = DEFAULT_SUB_ME,
    var subTE: Int = 0,
    var priceUpdateSource: Int = DEFAULT_PRICE_SOURCE
){
    companion object{
        const val DEFAULT_LANGUAGE = 12
        const val DEFAULT_SYSTEM = 30000142
        const val DEFAULT_REGION = 10000002
        const val DEFAULT_TE = 0
        const val DEFAULT_SUB_TE = 0
        const val DEFAULT_ME = 0
        const val DEFAULT_SUB_ME = 0
        const val DEFAULT_PRICE_SOURCE = 2
    }
}