package io.sakurasou.server

import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.sakurasou.protocol.ContentHandler
import io.sakurasou.protocol.ProtocolCodec
import io.sakurasou.protocol.ProtocolFrameDecoder

/**
 * @author mashirot
 * 2024/2/24 17:26
 */
class RpcServer {
    private val logger = KotlinLogging.logger {}
    fun start(host: String, port: Int) {
        logger.info { "TcpServer start, address: $host, port: $port" }
        val selectorGroup = NioEventLoopGroup(1)
        val workerGroup = NioEventLoopGroup(2)
        try {
            val channelFuture = ServerBootstrap()
                .group(selectorGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<NioSocketChannel>() {
                    override fun initChannel(ch: NioSocketChannel) {
                        ch.pipeline()
                            .addLast(LoggingHandler(LogLevel.INFO))
                            .addLast(ProtocolFrameDecoder())
                            .addLast(ProtocolCodec())
                            .addLast(ContentHandler())
                    }
                })
                .bind(host, port)
            val channel = channelFuture.channel()
            channel.closeFuture().sync()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            selectorGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }
}