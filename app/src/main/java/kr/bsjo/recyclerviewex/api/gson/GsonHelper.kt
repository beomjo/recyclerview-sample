package kr.bsjo.recyclerviewex.api.gson

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import kr.bsjo.recyclerviewex.model.type.OutputType
import java.lang.reflect.Type

object GsonHelper {
    private val gsonInstance: Gson by lazy {
        builder().create()
    }

    fun gson(): Gson {
        return gsonInstance
    }

    private fun builder(): GsonBuilder {
        return GsonBuilder().apply {
            setLenient()
            registerTypeAdapter(
                JsonElementAs::class.java,
                stringAdapter()
            )
            registerTypeAdapter(
                JsonElementAsObject::class.java,
                stringAdapter()
            )
            registerTypeAdapter(
                JsonElementAsString::class.java,
                stringAdapter()
            )
        }
    }

    private fun stringAdapter() = object : TypeAdapter<JsonElementAs>() {
        override fun write(writer: JsonWriter, value: JsonElementAs?) {
            value?.let {
                if (it.outputType == OutputType.ToObject) {
                    writer.jsonValue(it.toString())
                } else {
                    writer.value(it.toString())
                }
            }
        }

        override fun read(reader: JsonReader): JsonElementAs {
            throw IllegalStateException()
        }
    }

    fun <T> toJson(any: T): String {
        return gson().toJson(any)
    }

    fun <T> fromJson(json: String?, clazz: Type, functionDefaultObjectCreator: (t: Throwable) -> T = { null!! }): T {
        return try {
            requireNotNull(json) { "Json need to Not null!!" }

            gson().fromJson(json, clazz)
        } catch (t: Throwable) {
            Log.e(this::class.java.canonicalName, t.toString())
            functionDefaultObjectCreator.invoke(t)
        }
    }

    inline fun <reified T> fromJson(
        json: String?,
        noinline functionDefaultObjectCreator: (t: Throwable) -> T = { t: Throwable -> throw t }
    ): T {
        return fromJson(
            json,
            object : TypeToken<T>() {}.type,
            functionDefaultObjectCreator
        )
    }

    inline fun <T, reified R> fromObject(obj: T): R =
        fromJson(toJson(obj))
}