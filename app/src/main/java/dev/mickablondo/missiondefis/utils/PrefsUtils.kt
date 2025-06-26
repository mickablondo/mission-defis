package dev.mickablondo.missiondefis.utils

import android.content.Context

private const val PREFS_NAME = "mission_prefs"
private const val KEY_SAVED_CHILD_ID = "saved_child_id"

fun saveSelectedChildId(context: Context, childId: Int) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putInt(KEY_SAVED_CHILD_ID, childId).apply()
}

fun loadSelectedChildId(context: Context): Int? {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val id = prefs.getInt(KEY_SAVED_CHILD_ID, -1)
    return if (id == -1) null else id
}

fun clearSelectedChildId(context: Context) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().remove(KEY_SAVED_CHILD_ID).apply()
}
