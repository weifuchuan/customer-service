package com.fuchuan.customerservice.common;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.19.0)",
    comments = "Source: customer-service.proto")
public final class AccountServiceGrpc {

  private AccountServiceGrpc() {}

  public static final String SERVICE_NAME = "com.fuchuan.customerservice.common.AccountService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.fuchuan.customerservice.common.AccountBaseInfoReq,
      com.fuchuan.customerservice.common.AccountBaseInfoReply> getFetchBaseInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FetchBaseInfo",
      requestType = com.fuchuan.customerservice.common.AccountBaseInfoReq.class,
      responseType = com.fuchuan.customerservice.common.AccountBaseInfoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fuchuan.customerservice.common.AccountBaseInfoReq,
      com.fuchuan.customerservice.common.AccountBaseInfoReply> getFetchBaseInfoMethod() {
    io.grpc.MethodDescriptor<com.fuchuan.customerservice.common.AccountBaseInfoReq, com.fuchuan.customerservice.common.AccountBaseInfoReply> getFetchBaseInfoMethod;
    if ((getFetchBaseInfoMethod = AccountServiceGrpc.getFetchBaseInfoMethod) == null) {
      synchronized (AccountServiceGrpc.class) {
        if ((getFetchBaseInfoMethod = AccountServiceGrpc.getFetchBaseInfoMethod) == null) {
          AccountServiceGrpc.getFetchBaseInfoMethod = getFetchBaseInfoMethod = 
              io.grpc.MethodDescriptor.<com.fuchuan.customerservice.common.AccountBaseInfoReq, com.fuchuan.customerservice.common.AccountBaseInfoReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.fuchuan.customerservice.common.AccountService", "FetchBaseInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fuchuan.customerservice.common.AccountBaseInfoReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fuchuan.customerservice.common.AccountBaseInfoReply.getDefaultInstance()))
                  .setSchemaDescriptor(new AccountServiceMethodDescriptorSupplier("FetchBaseInfo"))
                  .build();
          }
        }
     }
     return getFetchBaseInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fuchuan.customerservice.common.FetchAccountListBaseInfoReq,
      com.fuchuan.customerservice.common.FetchAccountListBaseInfoReply> getFetchAccountListBaseInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FetchAccountListBaseInfo",
      requestType = com.fuchuan.customerservice.common.FetchAccountListBaseInfoReq.class,
      responseType = com.fuchuan.customerservice.common.FetchAccountListBaseInfoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fuchuan.customerservice.common.FetchAccountListBaseInfoReq,
      com.fuchuan.customerservice.common.FetchAccountListBaseInfoReply> getFetchAccountListBaseInfoMethod() {
    io.grpc.MethodDescriptor<com.fuchuan.customerservice.common.FetchAccountListBaseInfoReq, com.fuchuan.customerservice.common.FetchAccountListBaseInfoReply> getFetchAccountListBaseInfoMethod;
    if ((getFetchAccountListBaseInfoMethod = AccountServiceGrpc.getFetchAccountListBaseInfoMethod) == null) {
      synchronized (AccountServiceGrpc.class) {
        if ((getFetchAccountListBaseInfoMethod = AccountServiceGrpc.getFetchAccountListBaseInfoMethod) == null) {
          AccountServiceGrpc.getFetchAccountListBaseInfoMethod = getFetchAccountListBaseInfoMethod = 
              io.grpc.MethodDescriptor.<com.fuchuan.customerservice.common.FetchAccountListBaseInfoReq, com.fuchuan.customerservice.common.FetchAccountListBaseInfoReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.fuchuan.customerservice.common.AccountService", "FetchAccountListBaseInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fuchuan.customerservice.common.FetchAccountListBaseInfoReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fuchuan.customerservice.common.FetchAccountListBaseInfoReply.getDefaultInstance()))
                  .setSchemaDescriptor(new AccountServiceMethodDescriptorSupplier("FetchAccountListBaseInfo"))
                  .build();
          }
        }
     }
     return getFetchAccountListBaseInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fuchuan.customerservice.common.WaitersReq,
      com.fuchuan.customerservice.common.Waiters> getFetchWaitersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FetchWaiters",
      requestType = com.fuchuan.customerservice.common.WaitersReq.class,
      responseType = com.fuchuan.customerservice.common.Waiters.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fuchuan.customerservice.common.WaitersReq,
      com.fuchuan.customerservice.common.Waiters> getFetchWaitersMethod() {
    io.grpc.MethodDescriptor<com.fuchuan.customerservice.common.WaitersReq, com.fuchuan.customerservice.common.Waiters> getFetchWaitersMethod;
    if ((getFetchWaitersMethod = AccountServiceGrpc.getFetchWaitersMethod) == null) {
      synchronized (AccountServiceGrpc.class) {
        if ((getFetchWaitersMethod = AccountServiceGrpc.getFetchWaitersMethod) == null) {
          AccountServiceGrpc.getFetchWaitersMethod = getFetchWaitersMethod = 
              io.grpc.MethodDescriptor.<com.fuchuan.customerservice.common.WaitersReq, com.fuchuan.customerservice.common.Waiters>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.fuchuan.customerservice.common.AccountService", "FetchWaiters"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fuchuan.customerservice.common.WaitersReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fuchuan.customerservice.common.Waiters.getDefaultInstance()))
                  .setSchemaDescriptor(new AccountServiceMethodDescriptorSupplier("FetchWaiters"))
                  .build();
          }
        }
     }
     return getFetchWaitersMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AccountServiceStub newStub(io.grpc.Channel channel) {
    return new AccountServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AccountServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AccountServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AccountServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AccountServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class AccountServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void fetchBaseInfo(com.fuchuan.customerservice.common.AccountBaseInfoReq request,
        io.grpc.stub.StreamObserver<com.fuchuan.customerservice.common.AccountBaseInfoReply> responseObserver) {
      asyncUnimplementedUnaryCall(getFetchBaseInfoMethod(), responseObserver);
    }

    /**
     */
    public void fetchAccountListBaseInfo(com.fuchuan.customerservice.common.FetchAccountListBaseInfoReq request,
        io.grpc.stub.StreamObserver<com.fuchuan.customerservice.common.FetchAccountListBaseInfoReply> responseObserver) {
      asyncUnimplementedUnaryCall(getFetchAccountListBaseInfoMethod(), responseObserver);
    }

    /**
     */
    public void fetchWaiters(com.fuchuan.customerservice.common.WaitersReq request,
        io.grpc.stub.StreamObserver<com.fuchuan.customerservice.common.Waiters> responseObserver) {
      asyncUnimplementedUnaryCall(getFetchWaitersMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getFetchBaseInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.fuchuan.customerservice.common.AccountBaseInfoReq,
                com.fuchuan.customerservice.common.AccountBaseInfoReply>(
                  this, METHODID_FETCH_BASE_INFO)))
          .addMethod(
            getFetchAccountListBaseInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.fuchuan.customerservice.common.FetchAccountListBaseInfoReq,
                com.fuchuan.customerservice.common.FetchAccountListBaseInfoReply>(
                  this, METHODID_FETCH_ACCOUNT_LIST_BASE_INFO)))
          .addMethod(
            getFetchWaitersMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.fuchuan.customerservice.common.WaitersReq,
                com.fuchuan.customerservice.common.Waiters>(
                  this, METHODID_FETCH_WAITERS)))
          .build();
    }
  }

  /**
   */
  public static final class AccountServiceStub extends io.grpc.stub.AbstractStub<AccountServiceStub> {
    private AccountServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AccountServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AccountServiceStub(channel, callOptions);
    }

    /**
     */
    public void fetchBaseInfo(com.fuchuan.customerservice.common.AccountBaseInfoReq request,
        io.grpc.stub.StreamObserver<com.fuchuan.customerservice.common.AccountBaseInfoReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFetchBaseInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void fetchAccountListBaseInfo(com.fuchuan.customerservice.common.FetchAccountListBaseInfoReq request,
        io.grpc.stub.StreamObserver<com.fuchuan.customerservice.common.FetchAccountListBaseInfoReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFetchAccountListBaseInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void fetchWaiters(com.fuchuan.customerservice.common.WaitersReq request,
        io.grpc.stub.StreamObserver<com.fuchuan.customerservice.common.Waiters> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFetchWaitersMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AccountServiceBlockingStub extends io.grpc.stub.AbstractStub<AccountServiceBlockingStub> {
    private AccountServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AccountServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AccountServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.fuchuan.customerservice.common.AccountBaseInfoReply fetchBaseInfo(com.fuchuan.customerservice.common.AccountBaseInfoReq request) {
      return blockingUnaryCall(
          getChannel(), getFetchBaseInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fuchuan.customerservice.common.FetchAccountListBaseInfoReply fetchAccountListBaseInfo(com.fuchuan.customerservice.common.FetchAccountListBaseInfoReq request) {
      return blockingUnaryCall(
          getChannel(), getFetchAccountListBaseInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fuchuan.customerservice.common.Waiters fetchWaiters(com.fuchuan.customerservice.common.WaitersReq request) {
      return blockingUnaryCall(
          getChannel(), getFetchWaitersMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AccountServiceFutureStub extends io.grpc.stub.AbstractStub<AccountServiceFutureStub> {
    private AccountServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AccountServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AccountServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fuchuan.customerservice.common.AccountBaseInfoReply> fetchBaseInfo(
        com.fuchuan.customerservice.common.AccountBaseInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(getFetchBaseInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fuchuan.customerservice.common.FetchAccountListBaseInfoReply> fetchAccountListBaseInfo(
        com.fuchuan.customerservice.common.FetchAccountListBaseInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(getFetchAccountListBaseInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fuchuan.customerservice.common.Waiters> fetchWaiters(
        com.fuchuan.customerservice.common.WaitersReq request) {
      return futureUnaryCall(
          getChannel().newCall(getFetchWaitersMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FETCH_BASE_INFO = 0;
  private static final int METHODID_FETCH_ACCOUNT_LIST_BASE_INFO = 1;
  private static final int METHODID_FETCH_WAITERS = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AccountServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AccountServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FETCH_BASE_INFO:
          serviceImpl.fetchBaseInfo((com.fuchuan.customerservice.common.AccountBaseInfoReq) request,
              (io.grpc.stub.StreamObserver<com.fuchuan.customerservice.common.AccountBaseInfoReply>) responseObserver);
          break;
        case METHODID_FETCH_ACCOUNT_LIST_BASE_INFO:
          serviceImpl.fetchAccountListBaseInfo((com.fuchuan.customerservice.common.FetchAccountListBaseInfoReq) request,
              (io.grpc.stub.StreamObserver<com.fuchuan.customerservice.common.FetchAccountListBaseInfoReply>) responseObserver);
          break;
        case METHODID_FETCH_WAITERS:
          serviceImpl.fetchWaiters((com.fuchuan.customerservice.common.WaitersReq) request,
              (io.grpc.stub.StreamObserver<com.fuchuan.customerservice.common.Waiters>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AccountServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AccountServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.fuchuan.customerservice.common.CustomerServiceGrpc.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AccountService");
    }
  }

  private static final class AccountServiceFileDescriptorSupplier
      extends AccountServiceBaseDescriptorSupplier {
    AccountServiceFileDescriptorSupplier() {}
  }

  private static final class AccountServiceMethodDescriptorSupplier
      extends AccountServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AccountServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AccountServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AccountServiceFileDescriptorSupplier())
              .addMethod(getFetchBaseInfoMethod())
              .addMethod(getFetchAccountListBaseInfoMethod())
              .addMethod(getFetchWaitersMethod())
              .build();
        }
      }
    }
    return result;
  }
}
