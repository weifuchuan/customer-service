package com.fuchuan.customerservice.mock.grpc;

import com.fuchuan.customerservice.common.*;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountServiceImpl extends AccountServiceGrpc.AccountServiceImplBase {
  Set<String> notExistsUserIdSet =
      Stream.of(/*"100", "200", "300", "400", "500", "600", "700", "800", "900"*/"")
          .collect(Collectors.toSet());

  @Override
  public void fetchBaseInfo(
      AccountBaseInfoReq request, StreamObserver<AccountBaseInfoReply> responseObserver) {
    String id = request.getId();
    if (notExistsUserIdSet.contains(id)) {
      responseObserver.onNext(AccountBaseInfoReply.newBuilder().setCode(Code.FAIL).build());
      responseObserver.onCompleted();
      return;
    }
    AccountBaseInfoReply.Builder builder =
        AccountBaseInfoReply.newBuilder()
            .setAccount(
                AccountBaseInfo.newBuilder()
                    .setId(id)
                    .setNickName("user " + id)
                    .setAvatar(
                        "https://cdn.pixabay.com/photo/2018/04/13/11/52/girl-3316342__340.jpg")
                    .build())
            .setCode(Code.OK);
    if (id.equals("1008601") || id.equals("1008602") || id.equals("1008603")) {
      builder.setRole(Role.WAITER);

      for (AccountBaseInfo waiter : waiters) {
        if (waiter.getId().equals(id)) {
          responseObserver.onNext(
              AccountBaseInfoReply.newBuilder()
                  .setCode(Code.OK)
                  .setRole(Role.WAITER)
                  .setAccount(waiter)
                  .build());
          responseObserver.onCompleted();
          return;
        }
      }
    } else {
      builder.setRole(Role.CUSTOMER);
    }
    responseObserver.onNext(builder.build());
    responseObserver.onCompleted();
  }

  List<AccountBaseInfo> waiters =
      Arrays.asList(
          AccountBaseInfo.newBuilder()
              .setId("1008601")
              .setNickName("客服01")
              .setAvatar("https://cdn.pixabay.com/photo/2014/11/30/14/11/kitty-551554__340.jpg")
              .build(),
          AccountBaseInfo.newBuilder()
              .setId("1008602")
              .setNickName("客服02")
              .setAvatar(
                  "https://cdn.pixabay.com/photo/2019/03/23/05/15/schafer-dog-4074699__340.jpg")
              .build(),
          AccountBaseInfo.newBuilder()
              .setId("1008603")
              .setNickName("客服03")
              .setAvatar("https://cdn.pixabay.com/photo/2019/03/24/09/11/duck-4077117__340.jpg")
              .build());

  @Override
  public void fetchWaiters(WaitersReq request, StreamObserver<Waiters> responseObserver) {
    responseObserver.onNext(Waiters.newBuilder().addAllAccount(waiters).build());
    responseObserver.onCompleted();
  }

  @Override
  public void fetchAccountListBaseInfo(
      FetchAccountListBaseInfoReq request,
      StreamObserver<FetchAccountListBaseInfoReply> responseObserver) {
    List<AccountBaseInfo> list = request.getIdList().stream()
      .map(
        id -> {
          if (notExistsUserIdSet.contains(id)) {
            return null;
          }
          return AccountBaseInfo.newBuilder()
            .setId(id)
            .setNickName("user " + id)
            .setAvatar("https://cdn.pixabay.com/photo/2018/04/13/11/52/girl-3316342__340.jpg")
            .build();
        }).collect(Collectors.toList());
    responseObserver.onNext(FetchAccountListBaseInfoReply.newBuilder().addAllAccount(list).build());
    responseObserver.onCompleted();
  }
}
