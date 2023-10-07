package com.vicksoson.cashxchange.utils

import org.json.JSONArray
import org.json.JSONObject


object JsonBody {
    fun generate(value: List<Pair<String, Any?>>): String {
        val newline = System.getProperty("line.separator")
        return value.associate {
            "\"${it.first}\"" to if (it.second is String) {
                if ((it.second as String).contains('{')) it.second else "\"${
                    newline?.let { it1 ->
                        (it.second as String).replace(
                            it1, "\\n"
                        )
                    }
                }\""
            } else it.second

        }.filter { it.value != null }
            .toString().replace("=", ":")
            .trimIndent()
    }
}


fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
    when (val value = this[it]) {
        is JSONArray -> {
            val map = (0 until value.length()).associate {
                Pair(it.toString(), value[it])
            }
            JSONObject(map).toMap().values.toList()
        }

        is JSONObject -> value.toMap()
        JSONObject.NULL -> null
        else -> value
    }
}

fun String.toJson(): Map<String, *> {
    val json = JSONObject(this)
    return json.toMap()
}
