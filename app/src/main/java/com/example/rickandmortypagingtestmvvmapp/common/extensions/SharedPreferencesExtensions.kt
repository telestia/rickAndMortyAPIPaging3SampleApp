package com.mellon.newssampleharun.common.extensions

import android.content.SharedPreferences
import com.mellon.newssampleharun.common.util.StringUtils

fun SharedPreferences.getStringOrEmpty(key: String): String = this.getString(key, null) ?: StringUtils.EMPTY
fun SharedPreferences.getStringOrNull(key: String): String? = this.getString(key, null)