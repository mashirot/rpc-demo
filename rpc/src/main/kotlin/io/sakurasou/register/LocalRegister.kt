package io.sakurasou.register

import io.github.oshai.kotlinlogging.KotlinLogging

/**
 * @author mashirot
 * 2024/2/24 23:21
 */
object LocalRegister {
    private val logger = KotlinLogging.logger {}
    private val map: MutableMap<String, Class<*>> = HashMap()

    fun <T> register(serviceClazz: Class<T>, implClazz: Class<out T>) {
        if (map.containsKey(serviceClazz.name)) throw RuntimeException("Duplicate interface name")
        map[serviceClazz.name] = implClazz
        logger.info { "${serviceClazz.name} Registration success" }
    }

    fun get(serviceName: String): Class<*> {
        return map[serviceName]
            ?: throw NoSuchElementException("The implementation class corresponding to the interface does not exist")
    }
}