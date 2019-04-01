package com.fuchuan.customerservice.common;

import com.fuchuan.customerservice.common.model._ModelGenerator;
import com.fuchuan.customerservice.common.payload._PayloadModelGenerator;

public class _Generator {
  public static void main(String[] args){
    _PayloadModelGenerator.main(args);
    _ModelGenerator.main(args);
  }
}
