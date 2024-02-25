package io.sakurasou.common

/**
 * @author mashirot
 * 2024/2/25 2:01
 */
class Response(
    val clazz: Class<*>,
    val data: Any
): Content() {
    override val contentType = 3

    companion object {
        val NO_RESPONSE = Response(Void.TYPE, Unit)
    }
}