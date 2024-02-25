package io.sakurasou

import io.github.oshai.kotlinlogging.KotlinLogging
import io.sakurasou.proxy.ProxyFactory
import io.sakurasou.service.HelloService

/**
 * @author mashirot
 * 2024/2/24 23:36
 */
fun main() {
    val logger = KotlinLogging.logger {}
    logger.info { "Subscriber running" }

    val helloService = ProxyFactory.getProxy(HelloService::class.java)
    println(helloService.sum(listOf(1, 2, 3)))
}