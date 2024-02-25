package io.sakurasou.protocol

import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageCodec
import io.sakurasou.common.Content
import io.sakurasou.config.Config
import io.sakurasou.util.Serialization

private val MAGIC = "shiina".toByteArray()

/**
 * @author mashirot
 * 2024/2/24 17:40
 */
@Sharable
class ProtocolCodec : MessageToMessageCodec<ByteBuf, Content>() {
    private val logger = KotlinLogging.logger {}
    override fun encode(ctx: ChannelHandlerContext, msg: Content, out: MutableList<Any>) {
        val buffer = ctx.alloc().buffer()
        val contentBytes = Serialization.serialize(msg)
        // magic 6b
        buffer.writeBytes(MAGIC)
        // version 2b
        buffer.writeShort(Config.PROTOCOL_VERSION)
        // serialization type 2b
        buffer.writeShort(Serialization.getSerializationType())
        // content type 2b
        buffer.writeShort(msg.contentType)
        // body length 4b
        buffer.writeInt(contentBytes.size)
        // body
        buffer.writeBytes(contentBytes)
        out.add(buffer)
    }

    override fun decode(ctx: ChannelHandlerContext, msg: ByteBuf, out: MutableList<Any>) {
        val magic = ByteArray(MAGIC.size)
        msg.readBytes(magic)
        if (!MAGIC.contentEquals(magic)) {
            logger.info { "magic error" }
            return
        }
        val version = msg.readShort()
        if (Config.PROTOCOL_VERSION != version.toInt()) {
            logger.info { "version error" }
            return
        }
        val serializationType = msg.readShort()
        val contentType = msg.readShort()
        val contentClass = Content.getContentClass(contentType)
        val length = msg.readInt()
        val contentBytes = ByteArray(length)
        msg.readBytes(contentBytes)
        val content = Serialization.deserialize(serializationType, contentBytes, contentClass)
        out.add(content)
    }
}