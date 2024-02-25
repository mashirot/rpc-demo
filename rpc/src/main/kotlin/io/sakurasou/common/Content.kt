package io.sakurasou.common

/**
 * @author mashirot
 * 2024/2/24 21:01
 */
sealed class Content {
    abstract val contentType: Int

    companion object {
        private const val INVOCATION = 1
        private const val MESSAGE = 2
        private const val RESPONSE = 3
        fun getContentClass(contentType: Short): Class<out Content> {
            return when(contentType.toInt()) {
                INVOCATION -> Invocation::class.java
                MESSAGE -> Message::class.java
                RESPONSE -> Response::class.java
                else -> throw IllegalArgumentException("Unknown content type: $contentType")
            }
        }
    }
}