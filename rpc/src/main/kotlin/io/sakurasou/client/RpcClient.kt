package io.sakurasou.client

import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.util.concurrent.DefaultPromise
import io.sakurasou.common.Content
import io.sakurasou.common.Response
import io.sakurasou.protocol.ProtocolCodec
import io.sakurasou.protocol.ResponseHandler

/**
 * @author mashirot
 * 2024/2/24 23:53
 */
class RpcClient {
    private val logger = KotlinLogging.logger {}
    fun send(host: String, port: Int, content: Content): Response {
        val eventLoopGroup = NioEventLoopGroup(2)
        val responsePromise = DefaultPromise<Response>(eventLoopGroup.next())
        try {
            val channelFuture = Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel::class.java)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .handler(object : ChannelInitializer<NioSocketChannel>() {
                    override fun initChannel(ch: NioSocketChannel) {
                        ch.pipeline()
                            .addLast(LoggingHandler(LogLevel.INFO))
                            .addLast(ProtocolCodec())
                            .addLast(ResponseHandler(responsePromise))
                    }
                })
                .connect(host, port)
                .sync()
            val channel = channelFuture.channel()
            logger.info { channel }
            channel.writeAndFlush(content)
            channel.closeFuture().addListener { eventLoopGroup.shutdownGracefully() }
            responsePromise.await()
            return if (responsePromise.isSuccess) responsePromise.get() else throw responsePromise.exceptionNow()
        } finally {
            eventLoopGroup.shutdownGracefully()
        }
    }
}