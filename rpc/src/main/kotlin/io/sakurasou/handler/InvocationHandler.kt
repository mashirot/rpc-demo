package io.sakurasou.handler

import io.sakurasou.common.Invocation
import io.sakurasou.register.LocalRegister

/**
 * @author mashirot
 * 2024/2/24 22:03
 */
object InvocationHandler {
    fun handle(invocation: Invocation): Pair<Class<*>, Any> {
        val implClazz = LocalRegister.get(invocation.interfaceName)
        val method = implClazz.getMethod(invocation.methodName, *invocation.parameterTypes)
        method.trySetAccessible()
        val returnType = method.returnType
        val instance = implClazz.getDeclaredConstructor().newInstance()
        val invoke = method.invoke(instance, *invocation.parameterValues)
        return if (returnType == Void.TYPE) returnType to Unit else returnType to invoke
    }
}