package com.fuchuan.customerservice.server.handler;

import com.fuchuan.customerservice.common.Command;
import com.fuchuan.customerservice.common.ImPacket;
import com.fuchuan.customerservice.db.IDao;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ActualHandlerMapper extends HashMap<Command, IActualHandler> implements IActualHandler {
  private static Logger log = LoggerFactory.getLogger(ActualHandlerMapper.class);

  public ActualHandlerMapper(IDao dao) {
    Reflections reflections = new Reflections(getClass().getPackage().getName());
    reflections
        .getSubTypesOf(BaseActualHandler.class)
        .forEach(
            clz -> {
              HandlerForCommand annotation = clz.getAnnotation(HandlerForCommand.class);
              if (annotation != null) {
                Command command = annotation.value();
                try {
                  Constructor<? extends BaseActualHandler> constructor = clz.getConstructor(IDao.class);
                  put(command, constructor.newInstance(dao));
                } catch (InstantiationException
                    | NoSuchMethodException
                    | InvocationTargetException
                    | IllegalAccessException e) {
                  e.printStackTrace();
                }
              }
            });
  }

  @Override
  public Object handle(ImPacket packet, WsRequest req, ChannelContext ctx) throws Exception {
    if (containsKey(packet.getCommand())) {
      return get(packet.getCommand()).handle(packet, req, ctx);
    } else {
      log.warn("Unsupported command", packet.getCommand());
      return null;
    }
  }
}
