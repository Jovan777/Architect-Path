package com.example.pmuprojekat.data.local.converters


import androidx.room.TypeConverter
import org.json.JSONArray
import org.json.JSONObject

class RoomConverters {

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        val array = JSONArray()
        value.forEach { array.put(it) }
        return array.toString()
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        if (value.isBlank()) return emptyList()

        val array = JSONArray(value)
        return buildList {
            for (i in 0 until array.length()) {
                add(array.getString(i))
            }
        }
    }

    @TypeConverter
    fun fromStringMap(value: Map<String, String>): String {
        val json = JSONObject()
        value.forEach { (key, mapValue) ->
            json.put(key, mapValue)
        }
        return json.toString()
    }

    @TypeConverter
    fun toStringMap(value: String): Map<String, String> {
        if (value.isBlank()) return emptyMap()

        val json = JSONObject(value)
        return buildMap {
            val keys = json.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                put(key, json.getString(key))
            }
        }
    }
}