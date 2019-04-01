package com.fuchuan.customerservice.server.handler;

import com.fuchuan.customerservice.App;
import com.fuchuan.customerservice.db.IDao;
import org.codejargon.feather.Feather;

public abstract class BaseActualHandler implements IActualHandler {
  protected static final Feather feather = App.feather;

  protected final IDao dao;

  public BaseActualHandler(IDao dao) {
    this.dao = dao;
  }
}
