package com.example.pmuprojekat.data.remote

import org.json.JSONArray
import org.json.JSONObject

internal fun String.toJsonMap(): Map<String, Any?> {
    return JSONObject(this).toMap()
}

private fun JSONObject.toMap(): Map<String, Any?> {
    return keys().asSequence().associateWith { key -> get(key).toKotlinValue() }
}

private fun JSONArray.toList(): List<Any?> {
    return (0 until length()).map { index -> get(index).toKotlinValue() }
}

private fun Any?.toKotlinValue(): Any? = when (this) {
    JSONObject.NULL -> null
    is JSONObject -> toMap()
    is JSONArray -> toList()
    else -> this
}
