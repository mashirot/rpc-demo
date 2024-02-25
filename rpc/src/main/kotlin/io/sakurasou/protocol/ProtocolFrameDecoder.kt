package io.sakurasou.protocol

import io.netty.handler.codec.LengthFieldBasedFrameDecoder

/**
 * @author mashirot
 * 2024/2/24 21:50
 */
class ProtocolFrameDecoder: LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0)