package by.nepravsky.sm.evereactioncalculator.utils

import android.content.Context

const val UI_SETTINGS = "ui_settings"
const val IS_NIGHT_THEME = "is_night_theme"
const val IS_SHOW_GROUP_FILTER = "is_show_group_filter"
const val IS_SHOW_RUN_ME_SETTINGS = "is_show_run_me"
const val IS_SHOW_REACTION_INFO = "is_show_reaction_info"
const val IS_SHORT_REACTION_ITEM = "is_short_reaction_item"

class UISettings(private val context: Context) {

    private val sp = context.getSharedPreferences(UI_SETTINGS, Context.MODE_PRIVATE)

    fun isNightThem(): Boolean = sp.getBoolean(IS_NIGHT_THEME, true)
    fun setNightThem(isNightMode: Boolean) =
        sp.edit().putBoolean(IS_NIGHT_THEME, isNightMode).apply()

    fun isShowRunME(): Boolean = sp.getBoolean(IS_SHOW_RUN_ME_SETTINGS, true)
    fun setShowBuildoptions(isShow: Boolean) =
        sp.edit().putBoolean(IS_SHOW_RUN_ME_SETTINGS, isShow).apply()

    fun isShowReactionInfo():Boolean = sp.getBoolean(IS_SHOW_REACTION_INFO, true)
    fun setShowBaseInfo(isShow: Boolean) =
        sp.edit().putBoolean(IS_SHOW_REACTION_INFO, isShow).apply()

    fun isShortReactionItem(): Boolean = sp.getBoolean(IS_SHORT_REACTION_ITEM, false)
    fun setShortReactionItem(isShort: Boolean) =
        sp.edit().putBoolean(IS_SHORT_REACTION_ITEM, isShort).apply()

}