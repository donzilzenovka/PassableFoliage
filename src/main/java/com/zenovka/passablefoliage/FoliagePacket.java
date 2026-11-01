package com.zenovka.passablefoliage;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class FoliagePacket implements IMessage {
    public FoliagePacket() {}
    @Override public void fromBytes(ByteBuf buf) {}
    @Override public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<FoliagePacket, IMessage> {
        static final ConcurrentLinkedQueue<UUID> requests = new ConcurrentLinkedQueue<UUID>();
        @Override public IMessage onMessage(FoliagePacket msg, MessageContext ctx) {
            requests.add(ctx.getServerHandler().playerEntity.getUniqueID());
            return null;
        }
    }
}
