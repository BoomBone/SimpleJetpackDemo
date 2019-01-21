package com.tools.simplejetpackdemo.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class GirlData(
        val error: Boolean,
        val results: List<Result>

) {
    class Deserializer : ResponseDeserializable<GirlData> {
        override fun deserialize(content: String): GirlData? {
            return Gson().fromJson(content, GirlData::class.javaObjectType)
        }
    }
}


class ListingResponse(val data: ListingData)

class ListingData(
        val children: List<Result>,
        val after: String?,
        val before: String?
)

data class Result(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String
)