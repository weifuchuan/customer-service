package com.fuchuan.customerservice.server.config;

import org.tio.utils.time.Time;
import org.tio.websocket.server.WsServerConfig;

public class ImServerConfig extends WsServerConfig {
  public ImServerConfig() {
    // unuseful
    super(0);
  }

  /** ip数据监控统计，时间段 */
  public interface IpStatDuration {
    Long DURATION_1 = Time.MINUTE_1 * 5;
    Long[] IPSTAT_DURATIONS = new Long[] {DURATION_1};
  }

  private String protocolName = "customer-service";
  private int heartbeatTime = 1000 * 60;
  private String charset = "utf-8";
  private boolean useSsl = false;
  private String sslKeystore = null;
  private String sslTruststore = null;
  private String sslpPwd = null;

  public boolean isUseSsl() {
    return useSsl;
  }

  public void setUseSsl(boolean useSsl) {
    this.useSsl = useSsl;
  }

  public String getSslKeystore() {
    return sslKeystore;
  }

  public void setSslKeystore(String sslKeystore) {
    this.sslKeystore = sslKeystore;
  }

  public String getSslTruststore() {
    return sslTruststore;
  }

  public void setSslTruststore(String sslTruststore) {
    this.sslTruststore = sslTruststore;
  }

  public String getSslpPwd() {
    return sslpPwd;
  }

  public void setSslpPwd(String sslpPwd) {
    this.sslpPwd = sslpPwd;
  }

  public String getProtocolName() {
    return protocolName;
  }

  public void setProtocolName(String protocolName) {
    this.protocolName = protocolName;
  }

  public int getHeartbeatTime() {
    return heartbeatTime;
  }

  public void setHeartbeatTime(int heartbeatTime) {
    this.heartbeatTime = heartbeatTime;
  }

  @Override
  public String getCharset() {
    return charset;
  }

  @Override
  public void setCharset(String charset) {
    this.charset = charset;
  }
}
