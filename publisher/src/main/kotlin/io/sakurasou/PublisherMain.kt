package io.sakurasou

import io.github.oshai.kotlinlogging.KotlinLogging
import io.sakurasou.register.LocalRegister
import io.sakurasou.server.RpcServer
import io.sakurasou.service.HelloService
import io.sakurasou.service.HelloServiceImpl

/**
 * @author mashirot
 * 2024/2/24 17:28
 */
fun main() {
    val logger = KotlinLogging.logger {}
    logger.info { "Publisher running" }

    LocalRegister.register(HelloService::class.java, HelloServiceImpl::class.java)
    val rpcServer = RpcServer()
    rpcServer.start("127.0.0.1", 6657)
}