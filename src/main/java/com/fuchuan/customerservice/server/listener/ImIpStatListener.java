package com.fuchuan.customerservice.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.intf.Packet;
import org.tio.core.stat.IpStat;
import org.tio.core.stat.IpStatListener;

/** @author tanyaowu */
public class ImIpStatListener implements IpStatListener {
  @SuppressWarnings("unused")
  private static Logger log = LoggerFactory.getLogger(ImIpStatListener.class);

  @Override
  public void onExpired(TioConfig groupContext, IpStat ipStat) {
    // 在这里把统计数据入库中或日志
    //		if (log.isInfoEnabled()) {
    //			log.info("可以把统计数据入库\r\n{}", Json.toFormatedJson(ipStat));
    //		}
  }

  @Override
  public void onAfterConnected(
      ChannelContext channelContext, boolean isConnected, boolean isReconnect, IpStat ipStat)
      throws Exception {
    //		if (log.isInfoEnabled()) {
    //			log.info("onAfterConnected\r\n{}", Json.toFormatedJson(ipStat));
    //		}
  }

  @Override
  public void onDecodeError(ChannelContext channelContext, IpStat ipStat) {
    //		if (log.isInfoEnabled()) {
    //			log.info("onDecodeError\r\n{}", Json.toFormatedJson(ipStat));
    //		}
  }

  @Override
  public void onAfterSent(
      ChannelContext channelContext, Packet packet, boolean isSentSuccess, IpStat ipStat)
      throws Exception {
    //		if (log.isInfoEnabled()) {
    //			log.info("onAfterSent\r\n{}\r\n{}", packet.logstr(), Json.toFormatedJson(ipStat));
    //		}
  }

  @Override
  public void onAfterDecoded(
      ChannelContext channelContext, Packet packet, int packetSize, IpStat ipStat)
      throws Exception {
    //		if (log.isInfoEnabled()) {
    //			log.info("onAfterDecoded\r\n{}\r\n{}", packet.logstr(), Json.toFormatedJson(ipStat));
    //		}
  }

  @Override
  public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes, IpStat ipStat)
      throws Exception {
    //		if (log.isInfoEnabled()) {
    //			log.info("onAfterReceivedBytes\r\n{}", Json.toFormatedJson(ipStat));
    //		}
  }

  @Override
  public void onAfterHandled(ChannelContext channelContext, Packet packet, IpStat ipStat, long cost)
      throws Exception {
    //		if (log.isInfoEnabled()) {
    //			log.info("onAfterHandled\r\n{}\r\n{}", packet.logstr(), Json.toFormatedJson(ipStat));
    //		}
  }
}
