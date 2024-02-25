package io.sakurasou.proxy

import io.sakurasou.client.RpcClient
import io.sakurasou.common.Invocation
import java.lang.reflect.Proxy

/**
 * @author mashirot
 * 2024/2/25 14:24
 */
object ProxyFactory {
    fun <T> getProxy(serviceClazz: Class<T>): T {
        return Proxy.newProxyInstance(
            serviceClazz.classLoader,
            arrayOf(serviceClazz)
        ) { _, method, args ->
            val invocation = Invocation(serviceClazz.name, method.name, method.parameterTypes, args)
            val rpcClient = RpcClient()
            val response = rpcClient.send("127.0.0.1", 6657, invocation)
            response.data
        } as T
    }
}