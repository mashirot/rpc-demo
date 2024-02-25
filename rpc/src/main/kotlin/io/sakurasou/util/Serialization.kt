package io.sakurasou.util

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.sakurasou.config.Config
import java.util.*

/**
 * @author mashirot
 * 2024/2/24 19:42
 */
sealed class Serialization {
    abstract fun serialize(any: Any): ByteArray
    abstract fun <T> deserialize(clazz: Class<T>, byteArray: ByteArray): T

    data object Json : Serialization() {
        private val JSON_MAPPER = JsonMapper().registerKotlinModule()
        override fun serialize(any: Any): ByteArray = JSON_MAPPER.writeValueAsBytes(any)
        override fun <T> deserialize(clazz: Class<T>, byteArray: ByteArray): T {
            return JSON_MAPPER.readValue(byteArray, clazz)
        }
    }

    data object Protobuf : Serialization() {
        override fun serialize(any: Any): ByteArray {
            println("Protobuf")
            return byteArrayOf()
        }

        override fun <T> deserialize(clazz: Class<T>, byteArray: ByteArray): T {
            return clazz.getDeclaredConstructor().newInstance()
        }
    }

    companion object {
        fun getSerializationType(): Int {
            return when (Config.properties.value.getProperty("serialization").lowercase(Locale.getDefault())) {
                "json" -> 1
                "protobuf" -> 2
                else -> throw IllegalArgumentException("Unsupported serialization type")
            }
        }

        fun serialize(source: Any): ByteArray {
            val serializationType =
                when (Config.properties.value.getProperty("serialization").lowercase(Locale.getDefault())) {
                    "json" -> Json
                    "protobuf" -> Protobuf
                    else -> throw IllegalArgumentException("Unsupported serialization type")
                }
            return serialize(serializationType, source)
        }

        fun <T> deserialize(serializeType: Short, source: ByteArray, clazz: Class<T>): T {
            val serialization = when (serializeType) {
                1.toShort() -> Json
                2.toShort() -> Protobuf
                else -> throw IllegalArgumentException("Unsupported serialization type: $serializeType")
            }
            return deserialize(serialization, source, clazz)
        }

        private fun serialize(serialization: Serialization, source: Any): ByteArray {
            return when (serialization) {
                is Json -> serialization.serialize(source)
                is Protobuf -> serialization.serialize(source)
            }
        }

        private fun <T> deserialize(serialization: Serialization, source: ByteArray, clazz: Class<T>): T {
            return when (serialization) {
                is Json -> serialization.deserialize(clazz, source)
                is Protobuf -> serialization.deserialize(clazz, source)
            }
        }
    }
}