package com.fuchuan.customerservice.kit;

import com.fuchuan.customerservice.common.ImPacket;
import com.fuchuan.customerservice.common.ImPacketCoder;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.utils.lock.SetWithLock;
import com.fuchuan.customerservice.server.websocket.common.WsResponse;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class TioKit {
  public static boolean sendWsByText(ChannelContext ctx, ImPacket packet) {
    return Tio.send(ctx, WsResponse.fromText(ImPacketCoder.encodeToString(packet), "UTF-8"));
  }

  public static List<Boolean> sendWsToCtxSetByText(Set<ChannelContext> ctxs, ImPacket packet) {
    if (ctxs == null) return Collections.emptyList();
    return ctxs.parallelStream().map(c -> sendWsByText(c, packet)).collect(Collectors.toList());
  }

  public static boolean isOnline(ChannelContext ctx, String userId) {
    SetWithLock<ChannelContext> ctxs =
        Tio.getChannelContextsByUserid(ctx.getGroupContext(), userId);
    if (ctxs == null) return false;
    ReentrantReadWriteLock.ReadLock readLock = ctxs.readLock();
    readLock.lock();
    try {
      Set<ChannelContext> contextSet = ctxs.getObj();
      return contextSet != null && contextSet.size() > 0;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    } finally {
      readLock.unlock();
    }
  }
}
