package io.sakurasou.protocol

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.util.concurrent.DefaultPromise
import io.sakurasou.common.Content
import io.sakurasou.common.Response

/**
 * @author mashirot
 * 2024/2/25 1:05
 */
class ResponseHandler(
    private val responsePromise: DefaultPromise<Response>
) : SimpleChannelInboundHandler<Content>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: Content?) {
        msg?.let {
            if (it is Response) responsePromise.setSuccess(it)
            else responsePromise.setSuccess(Response.NO_RESPONSE)
        } ?: { responsePromise.setFailure(RuntimeException("No result returned")) }
        ctx.channel().close()
    }
}