package io.sakurasou.config

import java.util.Properties

/**
 * @author mashirot
 * 2024/2/24 20:30
 */
object Config {
    const val PROTOCOL_VERSION = 1
    val properties = lazy {
        Properties().apply {
            this::class.java.getResourceAsStream("/rpc.properties").use {
                it?.let { load(it) } ?: setProperty("serialization", "json")
            }
        }
    }
}