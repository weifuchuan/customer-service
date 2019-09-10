package com.fuchuan.customerservice.kit;

import com.fuchuan.customerservice.common.ImPacket;
import com.fuchuan.customerservice.common.ImPacketCoder;
import org.tio.websocket.common.WsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.cluster.TioClusterConfig;
import org.tio.cluster.TioClusterVo;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.utils.lock.SetWithLock;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class TioKit {
  private static final Logger log = LoggerFactory.getLogger(TioKit.class);

  public static boolean sendWsByText(ChannelContext ctx, ImPacket packet) {
    return Tio.send(ctx, WsResponse.fromText(ImPacketCoder.encodeToString(packet), "UTF-8"));
  }

  public static boolean sendWSToUserIdByText(ChannelContext ctx, String userId, ImPacket packet) {
    return Tio.sendToUser(
        ctx.tioConfig,
        userId,
        WsResponse.fromText(ImPacketCoder.encodeToString(packet), "UTF-8"));
  }

  public static List<Boolean> sendWsToCtxSetByText(Set<ChannelContext> ctxs, ImPacket packet) {
    if (ctxs == null) return Collections.emptyList();
    return ctxs.parallelStream().map(c -> sendWsByText(c, packet)).collect(Collectors.toList());
  }

  public static void notifyClusterForAll(TioConfig groupContext, Packet packet) {
    TioClusterConfig tioClusterConfig = groupContext.getTioClusterConfig();
    TioClusterVo tioClusterVo = new TioClusterVo(packet);
    tioClusterVo.setToAll(true);
    tioClusterConfig.publish(tioClusterVo);
  }

  public static void notifyClusterForAll(TioConfig groupContext, ImPacket packet) {
    notifyClusterForAll(
        groupContext, WsResponse.fromText(ImPacketCoder.encodeToString(packet), "UTF-8"));
  }
}
