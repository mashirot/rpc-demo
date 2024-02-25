package io.sakurasou.protocol

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.sakurasou.common.Content
import io.sakurasou.common.Invocation
import io.sakurasou.common.Message
import io.sakurasou.common.Response
import io.sakurasou.handler.InvocationHandler
import io.sakurasou.handler.MessageHandler

/**
 * @author mashirot
 * 2024/2/24 21:32
 */
class ContentHandler: SimpleChannelInboundHandler<Content>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: Content) {
        val (clazz, data) = when (msg) {
            is Invocation -> InvocationHandler.handle(msg)
            is Message -> MessageHandler.handle(msg)
            is Response -> return
        }
        ctx.writeAndFlush(Response(clazz, data))
    }
}