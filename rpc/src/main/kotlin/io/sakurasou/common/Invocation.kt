package io.sakurasou.common

/**
 * @author mashirot
 * 2024/2/24 19:26
 */
data class Invocation(
    val interfaceName: String,
    val methodName: String,
    val parameterTypes: Array<Class<*>>,
    val parameterValues: Array<*>
): Content() {
    override val contentType = 1

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Invocation

        if (interfaceName != other.interfaceName) return false
        if (methodName != other.methodName) return false
        if (!parameterTypes.contentEquals(other.parameterTypes)) return false
        if (!parameterValues.contentEquals(other.parameterValues)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = interfaceName.hashCode()
        result = 31 * result + methodName.hashCode()
        result = 31 * result + parameterTypes.contentHashCode()
        result = 31 * result + parameterValues.contentHashCode()
        return result
    }
}