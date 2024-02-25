package io.sakurasou.handler

import io.sakurasou.common.Message

/**
 * @author mashirot
 * 2024/2/24 22:04
 */
object MessageHandler {
    fun handle(message: Message): Pair<Class<*>, Any> {
        return String::class.java to ""
    }
}