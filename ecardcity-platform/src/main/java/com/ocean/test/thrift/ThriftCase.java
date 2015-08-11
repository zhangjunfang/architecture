package com.ocean.test.thrift;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.protocol.TMessageType;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.server.AbstractNonblockingServer.AsyncFrameBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @Description :
 * @author : ocean
 * @date : 2014-5-13 上午10:33:15
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
@SuppressWarnings("all")
public class ThriftCase {

	public interface Iface {

		public int testCase1(int num1, int num2, String num3)
				throws org.apache.thrift.TException;

		public List<String> testCase2(Map<String, String> num1)
				throws org.apache.thrift.TException;

		public void testCase3() throws org.apache.thrift.TException;

		public void testCase4(List<Blog> blog)
				throws org.apache.thrift.TException;

	}

	public interface AsyncIface {

		public void testCase1(int num1, int num2, String num3,
				AsyncMethodCallback resultHandler) throws TException;

		public void testCase2(Map<String, String> num1,
				AsyncMethodCallback resultHandler) throws TException;

		public void testCase3(AsyncMethodCallback resultHandler)
				throws org.apache.thrift.TException;

		public void testCase4(List<Blog> blog, AsyncMethodCallback resultHandler)
				throws TException;

	}

	public static class Client extends org.apache.thrift.TServiceClient
			implements Iface {
		public static class Factory implements
				org.apache.thrift.TServiceClientFactory<Client> {
			public Factory() {
			}

			public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
				return new Client(prot);
			}

			public Client getClient(org.apache.thrift.protocol.TProtocol iprot,
					org.apache.thrift.protocol.TProtocol oprot) {
				return new Client(iprot, oprot);
			}
		}

		public Client(org.apache.thrift.protocol.TProtocol prot) {
			super(prot, prot);
		}

		public Client(org.apache.thrift.protocol.TProtocol iprot,
				org.apache.thrift.protocol.TProtocol oprot) {
			super(iprot, oprot);
		}

		public int testCase1(int num1, int num2, String num3)
				throws org.apache.thrift.TException {
			send_testCase1(num1, num2, num3);
			return recv_testCase1();
		}

		public void send_testCase1(int num1, int num2, String num3)
				throws org.apache.thrift.TException {
			testCase1_args args = new testCase1_args();
			args.setNum1(num1);
			args.setNum2(num2);
			args.setNum3(num3);
			sendBase("testCase1", args);
		}

		public int recv_testCase1() throws org.apache.thrift.TException {
			testCase1_result result = new testCase1_result();
			receiveBase(result, "testCase1");
			if (result.isSetSuccess()) {
				return result.success;
			}
			throw new org.apache.thrift.TApplicationException(
					org.apache.thrift.TApplicationException.MISSING_RESULT,
					"testCase1 failed: unknown result");
		}

		public List<String> testCase2(Map<String, String> num1)
				throws org.apache.thrift.TException {
			send_testCase2(num1);
			return recv_testCase2();
		}

		public void send_testCase2(Map<String, String> num1)
				throws org.apache.thrift.TException {
			testCase2_args args = new testCase2_args();
			args.setNum1(num1);
			sendBase("testCase2", args);
		}

		public List<String> recv_testCase2()
				throws org.apache.thrift.TException {
			testCase2_result result = new testCase2_result();
			receiveBase(result, "testCase2");
			if (result.isSetSuccess()) {
				return result.success;
			}
			throw new org.apache.thrift.TApplicationException(
					org.apache.thrift.TApplicationException.MISSING_RESULT,
					"testCase2 failed: unknown result");
		}

		public void testCase3() throws org.apache.thrift.TException {
			send_testCase3();
			recv_testCase3();
		}

		public void send_testCase3() throws org.apache.thrift.TException {
			testCase3_args args = new testCase3_args();
			sendBase("testCase3", args);
		}

		public void recv_testCase3() throws org.apache.thrift.TException {
			testCase3_result result = new testCase3_result();
			receiveBase(result, "testCase3");
			return;
		}

		public void testCase4(List<Blog> blog)
				throws org.apache.thrift.TException {
			send_testCase4(blog);
			recv_testCase4();
		}

		public void send_testCase4(List<Blog> blog)
				throws org.apache.thrift.TException {
			testCase4_args args = new testCase4_args();
			args.setBlog(blog);
			sendBase("testCase4", args);
		}

		public void recv_testCase4() throws org.apache.thrift.TException {
			testCase4_result result = new testCase4_result();
			receiveBase(result, "testCase4");
			return;
		}

	}

	public static class AsyncClient extends
			org.apache.thrift.async.TAsyncClient implements AsyncIface {
		public static class Factory implements
				org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
			private org.apache.thrift.async.TAsyncClientManager clientManager;
			private org.apache.thrift.protocol.TProtocolFactory protocolFactory;

			public Factory(
					org.apache.thrift.async.TAsyncClientManager clientManager,
					org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
				this.clientManager = clientManager;
				this.protocolFactory = protocolFactory;
			}

			public AsyncClient getAsyncClient(
					org.apache.thrift.transport.TNonblockingTransport transport) {
				return new AsyncClient(protocolFactory, clientManager,
						transport);
			}
		}

		public AsyncClient(
				org.apache.thrift.protocol.TProtocolFactory protocolFactory,
				org.apache.thrift.async.TAsyncClientManager clientManager,
				org.apache.thrift.transport.TNonblockingTransport transport) {
			super(protocolFactory, clientManager, transport);
		}

		public void testCase1(int num1, int num2, String num3,
				org.apache.thrift.async.AsyncMethodCallback resultHandler)
				throws org.apache.thrift.TException {
			checkReady();
			testCase1_call method_call = new testCase1_call(num1, num2, num3,
					resultHandler, this, ___protocolFactory, ___transport);
			this.___currentMethod = method_call;
			___manager.call(method_call);
		}

		public static class testCase1_call extends
				org.apache.thrift.async.TAsyncMethodCall {
			private int num1;
			private int num2;
			private String num3;

			public testCase1_call(
					int num1,
					int num2,
					String num3,
					org.apache.thrift.async.AsyncMethodCallback resultHandler,
					org.apache.thrift.async.TAsyncClient client,
					org.apache.thrift.protocol.TProtocolFactory protocolFactory,
					org.apache.thrift.transport.TNonblockingTransport transport)
					throws org.apache.thrift.TException {
				super(client, protocolFactory, transport, resultHandler, false);
				this.num1 = num1;
				this.num2 = num2;
				this.num3 = num3;
			}

			public void write_args(org.apache.thrift.protocol.TProtocol prot)
					throws org.apache.thrift.TException {
				prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage(
						"testCase1",
						org.apache.thrift.protocol.TMessageType.CALL, 0));
				testCase1_args args = new testCase1_args();
				args.setNum1(num1);
				args.setNum2(num2);
				args.setNum3(num3);
				args.write(prot);
				prot.writeMessageEnd();
			}

			public int getResult() throws org.apache.thrift.TException {
				if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
					throw new IllegalStateException("Method call not finished!");
				}
				org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(
						getFrameBuffer().array());
				org.apache.thrift.protocol.TProtocol prot = client
						.getProtocolFactory().getProtocol(memoryTransport);
				return (new Client(prot)).recv_testCase1();
			}
		}

		public void testCase2(Map<String, String> num1,
				org.apache.thrift.async.AsyncMethodCallback resultHandler)
				throws org.apache.thrift.TException {
			checkReady();
			testCase2_call method_call = new testCase2_call(num1,
					resultHandler, this, ___protocolFactory, ___transport);
			this.___currentMethod = method_call;
			___manager.call(method_call);
		}

		public static class testCase2_call extends
				org.apache.thrift.async.TAsyncMethodCall {
			private Map<String, String> num1;

			public testCase2_call(
					Map<String, String> num1,
					org.apache.thrift.async.AsyncMethodCallback resultHandler,
					org.apache.thrift.async.TAsyncClient client,
					org.apache.thrift.protocol.TProtocolFactory protocolFactory,
					org.apache.thrift.transport.TNonblockingTransport transport)
					throws org.apache.thrift.TException {
				super(client, protocolFactory, transport, resultHandler, false);
				this.num1 = num1;
			}

			public void write_args(org.apache.thrift.protocol.TProtocol prot)
					throws org.apache.thrift.TException {
				prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage(
						"testCase2",
						org.apache.thrift.protocol.TMessageType.CALL, 0));
				testCase2_args args = new testCase2_args();
				args.setNum1(num1);
				args.write(prot);
				prot.writeMessageEnd();
			}

			public List<String> getResult() throws org.apache.thrift.TException {
				if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
					throw new IllegalStateException("Method call not finished!");
				}
				org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(
						getFrameBuffer().array());
				org.apache.thrift.protocol.TProtocol prot = client
						.getProtocolFactory().getProtocol(memoryTransport);
				return (new Client(prot)).recv_testCase2();
			}
		}

		public void testCase3(
				org.apache.thrift.async.AsyncMethodCallback resultHandler)
				throws org.apache.thrift.TException {
			checkReady();
			testCase3_call method_call = new testCase3_call(resultHandler,
					this, ___protocolFactory, ___transport);
			this.___currentMethod = method_call;
			___manager.call(method_call);
		}

		public static class testCase3_call extends
				org.apache.thrift.async.TAsyncMethodCall {
			public testCase3_call(
					org.apache.thrift.async.AsyncMethodCallback resultHandler,
					org.apache.thrift.async.TAsyncClient client,
					org.apache.thrift.protocol.TProtocolFactory protocolFactory,
					org.apache.thrift.transport.TNonblockingTransport transport)
					throws org.apache.thrift.TException {
				super(client, protocolFactory, transport, resultHandler, false);
			}

			public void write_args(org.apache.thrift.protocol.TProtocol prot)
					throws org.apache.thrift.TException {
				prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage(
						"testCase3",
						org.apache.thrift.protocol.TMessageType.CALL, 0));
				testCase3_args args = new testCase3_args();
				args.write(prot);
				prot.writeMessageEnd();
			}

			public void getResult() throws org.apache.thrift.TException {
				if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
					throw new IllegalStateException("Method call not finished!");
				}
				org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(
						getFrameBuffer().array());
				org.apache.thrift.protocol.TProtocol prot = client
						.getProtocolFactory().getProtocol(memoryTransport);
				(new Client(prot)).recv_testCase3();
			}
		}

		public void testCase4(List<Blog> blog,
				org.apache.thrift.async.AsyncMethodCallback resultHandler)
				throws org.apache.thrift.TException {
			checkReady();
			testCase4_call method_call = new testCase4_call(blog,
					resultHandler, this, ___protocolFactory, ___transport);
			this.___currentMethod = method_call;
			___manager.call(method_call);
		}

		public static class testCase4_call extends
				org.apache.thrift.async.TAsyncMethodCall {
			private List<Blog> blog;

			public testCase4_call(
					List<Blog> blog,
					org.apache.thrift.async.AsyncMethodCallback resultHandler,
					org.apache.thrift.async.TAsyncClient client,
					org.apache.thrift.protocol.TProtocolFactory protocolFactory,
					org.apache.thrift.transport.TNonblockingTransport transport)
					throws org.apache.thrift.TException {
				super(client, protocolFactory, transport, resultHandler, false);
				this.blog = blog;
			}

			public void write_args(org.apache.thrift.protocol.TProtocol prot)
					throws org.apache.thrift.TException {
				prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage(
						"testCase4",
						org.apache.thrift.protocol.TMessageType.CALL, 0));
				testCase4_args args = new testCase4_args();
				args.setBlog(blog);
				args.write(prot);
				prot.writeMessageEnd();
			}

			public void getResult() throws org.apache.thrift.TException {
				if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
					throw new IllegalStateException("Method call not finished!");
				}
				org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(
						getFrameBuffer().array());
				org.apache.thrift.protocol.TProtocol prot = client
						.getProtocolFactory().getProtocol(memoryTransport);
				(new Client(prot)).recv_testCase4();
			}
		}

	}

	public static class Processor<I extends Iface> extends
			org.apache.thrift.TBaseProcessor<I> implements
			org.apache.thrift.TProcessor {
		private static final Logger LOGGER = LoggerFactory
				.getLogger(Processor.class.getName());

		public Processor(I iface) {
			super(
					iface,
					getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
		}

		protected Processor(
				I iface,
				Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap) {
			super(iface, getProcessMap(processMap));
		}

		private static <I extends Iface> Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> getProcessMap(
				Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap) {
			processMap.put("testCase1", new testCase1());
			processMap.put("testCase2", new testCase2());
			processMap.put("testCase3", new testCase3());
			processMap.put("testCase4", new testCase4());
			return processMap;
		}

		public static class testCase1<I extends Iface> extends
				org.apache.thrift.ProcessFunction<I, testCase1_args> {
			public testCase1() {
				super("testCase1");
			}

			public testCase1_args getEmptyArgsInstance() {
				return new testCase1_args();
			}

			protected boolean isOneway() {
				return false;
			}

			public testCase1_result getResult(I iface, testCase1_args args)
					throws org.apache.thrift.TException {
				testCase1_result result = new testCase1_result();
				result.success = iface.testCase1(args.num1, args.num2,
						args.num3);
				result.setSuccessIsSet(true);
				return result;
			}
		}

		public static class testCase2<I extends Iface> extends
				org.apache.thrift.ProcessFunction<I, testCase2_args> {
			public testCase2() {
				super("testCase2");
			}

			public testCase2_args getEmptyArgsInstance() {
				return new testCase2_args();
			}

			protected boolean isOneway() {
				return false;
			}

			public testCase2_result getResult(I iface, testCase2_args args)
					throws org.apache.thrift.TException {
				testCase2_result result = new testCase2_result();
				result.success = iface.testCase2(args.num1);
				return result;
			}
		}

		public static class testCase3<I extends Iface> extends
				org.apache.thrift.ProcessFunction<I, testCase3_args> {
			public testCase3() {
				super("testCase3");
			}

			public testCase3_args getEmptyArgsInstance() {
				return new testCase3_args();
			}

			protected boolean isOneway() {
				return false;
			}

			public testCase3_result getResult(I iface, testCase3_args args)
					throws org.apache.thrift.TException {
				testCase3_result result = new testCase3_result();
				iface.testCase3();
				return result;
			}
		}

		public static class testCase4<I extends Iface> extends
				org.apache.thrift.ProcessFunction<I, testCase4_args> {
			public testCase4() {
				super("testCase4");
			}

			public testCase4_args getEmptyArgsInstance() {
				return new testCase4_args();
			}

			protected boolean isOneway() {
				return false;
			}

			public testCase4_result getResult(I iface, testCase4_args args)
					throws org.apache.thrift.TException {
				testCase4_result result = new testCase4_result();
				iface.testCase4(args.blog);
				return result;
			}
		}

	}

	public static class AsyncProcessor<I extends AsyncIface> extends
			org.apache.thrift.TBaseAsyncProcessor<I> {
		private static final Logger LOGGER = LoggerFactory
				.getLogger(AsyncProcessor.class.getName());

		public AsyncProcessor(I iface) {
			super(
					iface,
					getProcessMap(new HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
		}

		protected AsyncProcessor(
				I iface,
				Map<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>> processMap) {
			super(iface, getProcessMap(processMap));
		}

		private static <I extends AsyncIface> Map<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>> getProcessMap(
				Map<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>> processMap) {
			processMap.put("testCase1", new testCase1());
			processMap.put("testCase2", new testCase2());
			processMap.put("testCase3", new testCase3());
			processMap.put("testCase4", new testCase4());
			return processMap;
		}

		public static class testCase1<I extends AsyncIface>
				extends
				org.apache.thrift.AsyncProcessFunction<I, testCase1_args, Integer> {
			public testCase1() {
				super("testCase1");
			}

			public testCase1_args getEmptyArgsInstance() {
				return new testCase1_args();
			}

			public AsyncMethodCallback<Integer> getResultHandler(
					final AsyncFrameBuffer fb, final int seqid) {
				final org.apache.thrift.AsyncProcessFunction fcall = this;
				return new AsyncMethodCallback<Integer>() {
					public void onComplete(Integer o) {
						testCase1_result result = new testCase1_result();
						result.success = o;
						result.setSuccessIsSet(true);
						try {
							fcall.sendResponse(
									fb,
									result,
									org.apache.thrift.protocol.TMessageType.REPLY,
									seqid);
							return;
						} catch (Exception e) {
							LOGGER.error(
									"Exception writing to internal frame buffer",
									e);
						}
						fb.close();
					}

					public void onError(Exception e) {
						byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
						org.apache.thrift.TBase msg;
						{
							msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
							msg = (TBase) new TApplicationException(
									TApplicationException.INTERNAL_ERROR,
									e.getMessage());
						}
						try {
							fcall.sendResponse(fb, msg, msgType, seqid);
							return;
						} catch (Exception ex) {
							LOGGER.error(
									"Exception writing to internal frame buffer",
									ex);
						}
						fb.close();
					}
				};
			}

			protected boolean isOneway() {
				return false;
			}

			public void start(
					I iface,
					testCase1_args args,
					org.apache.thrift.async.AsyncMethodCallback<Integer> resultHandler)
					throws TException {
				iface.testCase1(args.num1, args.num2, args.num3, resultHandler);
			}
		}

		public static class testCase2<I extends AsyncIface>
				extends
				org.apache.thrift.AsyncProcessFunction<I, testCase2_args, List<String>> {
			public testCase2() {
				super("testCase2");
			}

			public testCase2_args getEmptyArgsInstance() {
				return new testCase2_args();
			}

			public AsyncMethodCallback<List<String>> getResultHandler(
					final AsyncFrameBuffer fb, final int seqid) {
				final org.apache.thrift.AsyncProcessFunction fcall = this;
				return new AsyncMethodCallback<List<String>>() {
					public void onComplete(List<String> o) {
						testCase2_result result = new testCase2_result();
						result.success = o;
						try {
							fcall.sendResponse(
									fb,
									result,
									org.apache.thrift.protocol.TMessageType.REPLY,
									seqid);
							return;
						} catch (Exception e) {
							LOGGER.error(
									"Exception writing to internal frame buffer",
									e);
						}
						fb.close();
					}

					public void onError(Exception e) {
						byte msgType = TMessageType.REPLY;
						TBase msg;
						//testCase2_result result = new testCase2_result();
						{
							msgType = TMessageType.EXCEPTION;
							msg = (TBase) new TApplicationException(
									TApplicationException.INTERNAL_ERROR,
									e.getMessage());
						}
						try {
							fcall.sendResponse(fb, msg, msgType, seqid);
							return;
						} catch (Exception ex) {
							LOGGER.error(
									"Exception writing to internal frame buffer",
									ex);
						}
						fb.close();
					}
				};
			}

			protected boolean isOneway() {
				return false;
			}

			public void start(
					I iface,
					testCase2_args args,
					AsyncMethodCallback<List<String>> resultHandler)
					throws TException {
				iface.testCase2(args.num1, resultHandler);
			}
		}

		public static class testCase3<I extends AsyncIface> extends
				org.apache.thrift.AsyncProcessFunction<I, testCase3_args, Void> {
			public testCase3() {
				super("testCase3");
			}

			public testCase3_args getEmptyArgsInstance() {
				return new testCase3_args();
			}

			public AsyncMethodCallback<Void> getResultHandler(
					final AsyncFrameBuffer fb, final int seqid) {
				final org.apache.thrift.AsyncProcessFunction fcall = this;
				return new AsyncMethodCallback<Void>() {
					public void onComplete(Void o) {
						testCase3_result result = new testCase3_result();
						try {
							fcall.sendResponse(
									fb,
									result,
									org.apache.thrift.protocol.TMessageType.REPLY,
									seqid);
							return;
						} catch (Exception e) {
							LOGGER.error(
									"Exception writing to internal frame buffer",
									e);
						}
						fb.close();
					}

					public void onError(Exception e) {
						byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
						TBase msg;
						{
							msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
							msg = (TBase) new TApplicationException(
									TApplicationException.INTERNAL_ERROR,
									e.getMessage());
						}
						try {
							fcall.sendResponse(fb, msg, msgType, seqid);
							return;
						} catch (Exception ex) {
							LOGGER.error(
									"Exception writing to internal frame buffer",
									ex);
						}
						fb.close();
					}
				};
			}

			protected boolean isOneway() {
				return false;
			}

			public void start(
					I iface,
					testCase3_args args,
					org.apache.thrift.async.AsyncMethodCallback<Void> resultHandler)
					throws TException {
				iface.testCase3(resultHandler);
			}
		}

		public static class testCase4<I extends AsyncIface> extends
				org.apache.thrift.AsyncProcessFunction<I, testCase4_args, Void> {
			public testCase4() {
				super("testCase4");
			}

			public testCase4_args getEmptyArgsInstance() {
				return new testCase4_args();
			}

			public AsyncMethodCallback<Void> getResultHandler(
					final AsyncFrameBuffer fb, final int seqid) {
				final org.apache.thrift.AsyncProcessFunction fcall = this;
				return new AsyncMethodCallback<Void>() {
					public void onComplete(Void o) {
						testCase4_result result = new testCase4_result();
						try {
							fcall.sendResponse(
									fb,
									result,
									org.apache.thrift.protocol.TMessageType.REPLY,
									seqid);
							return;
						} catch (Exception e) {
							LOGGER.error(
									"Exception writing to internal frame buffer",
									e);
						}
						fb.close();
					}

					public void onError(Exception e) {
						byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
						TBase msg;
						{
							msgType = TMessageType.EXCEPTION;
							msg = (TBase) new TApplicationException(
									TApplicationException.INTERNAL_ERROR,
									e.getMessage());
						}
						try {
							fcall.sendResponse(fb, msg, msgType, seqid);
							return;
						} catch (Exception ex) {
							LOGGER.error(
									"Exception writing to internal frame buffer",
									ex);
						}
						fb.close();
					}
				};
			}

			protected boolean isOneway() {
				return false;
			}

			public void start(
					I iface,
					testCase4_args args,
					org.apache.thrift.async.AsyncMethodCallback<Void> resultHandler)
					throws TException {
				iface.testCase4(args.blog, resultHandler);
			}
		}

	}

	public static class testCase1_args implements
			org.apache.thrift.TBase<testCase1_args, testCase1_args._Fields>,
			java.io.Serializable, Cloneable, Comparable<testCase1_args> {
		private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
				"testCase1_args");

		private static final org.apache.thrift.protocol.TField NUM1_FIELD_DESC = new org.apache.thrift.protocol.TField(
				"num1", org.apache.thrift.protocol.TType.I32, (short) 1);
		private static final org.apache.thrift.protocol.TField NUM2_FIELD_DESC = new org.apache.thrift.protocol.TField(
				"num2", org.apache.thrift.protocol.TType.I32, (short) 2);
		private static final org.apache.thrift.protocol.TField NUM3_FIELD_DESC = new org.apache.thrift.protocol.TField(
				"num3", org.apache.thrift.protocol.TType.STRING, (short) 3);

		private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
		static {
			schemes.put(StandardScheme.class,
					new testCase1_argsStandardSchemeFactory());
			schemes.put(TupleScheme.class,
					new testCase1_argsTupleSchemeFactory());
		}

		public int num1; // required
		public int num2; // required
		public String num3; // required

		public enum _Fields implements org.apache.thrift.TFieldIdEnum {
			NUM1((short) 1, "num1"), NUM2((short) 2, "num2"), NUM3((short) 3,
					"num3");

			private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

			static {
				for (_Fields field : EnumSet.allOf(_Fields.class)) {
					byName.put(field.getFieldName(), field);
				}
			}

			public static _Fields findByThriftId(int fieldId) {
				switch (fieldId) {
				case 1: // NUM1
					return NUM1;
				case 2: // NUM2
					return NUM2;
				case 3: // NUM3
					return NUM3;
				default:
					return null;
				}
			}

			public static _Fields findByThriftIdOrThrow(int fieldId) {
				_Fields fields = findByThriftId(fieldId);
				if (fields == null)
					throw new IllegalArgumentException("Field " + fieldId
							+ " doesn't exist!");
				return fields;
			}

			public static _Fields findByName(String name) {
				return byName.get(name);
			}

			private final short _thriftId;
			private final String _fieldName;

			_Fields(short thriftId, String fieldName) {
				_thriftId = thriftId;
				_fieldName = fieldName;
			}

			public short getThriftFieldId() {
				return _thriftId;
			}

			public String getFieldName() {
				return _fieldName;
			}
		}

		// isset id assignments
		private static final int __NUM1_ISSET_ID = 0;
		private static final int __NUM2_ISSET_ID = 1;
		private byte __isset_bitfield = 0;
		public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
		static {
			Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
					_Fields.class);
			tmpMap.put(_Fields.NUM1,
					new org.apache.thrift.meta_data.FieldMetaData("num1",
							org.apache.thrift.TFieldRequirementType.DEFAULT,
							new org.apache.thrift.meta_data.FieldValueMetaData(
									org.apache.thrift.protocol.TType.I32)));
			tmpMap.put(_Fields.NUM2,
					new org.apache.thrift.meta_data.FieldMetaData("num2",
							org.apache.thrift.TFieldRequirementType.DEFAULT,
							new org.apache.thrift.meta_data.FieldValueMetaData(
									org.apache.thrift.protocol.TType.I32)));
			tmpMap.put(_Fields.NUM3,
					new org.apache.thrift.meta_data.FieldMetaData("num3",
							org.apache.thrift.TFieldRequirementType.DEFAULT,
							new org.apache.thrift.meta_data.FieldValueMetaData(
									org.apache.thrift.protocol.TType.STRING)));
			metaDataMap = Collections.unmodifiableMap(tmpMap);
			org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(
					testCase1_args.class, metaDataMap);
		}

		public testCase1_args() {
		}

		public testCase1_args(int num1, int num2, String num3) {
			this();
			this.num1 = num1;
			setNum1IsSet(true);
			this.num2 = num2;
			setNum2IsSet(true);
			this.num3 = num3;
		}

		public testCase1_args(testCase1_args other) {
			__isset_bitfield = other.__isset_bitfield;
			this.num1 = other.num1;
			this.num2 = other.num2;
			if (other.isSetNum3()) {
				this.num3 = other.num3;
			}
		}

		public testCase1_args deepCopy() {
			return new testCase1_args(this);
		}

		@Override
		public void clear() {
			setNum1IsSet(false);
			this.num1 = 0;
			setNum2IsSet(false);
			this.num2 = 0;
			this.num3 = null;
		}

		public int getNum1() {
			return this.num1;
		}

		public testCase1_args setNum1(int num1) {
			this.num1 = num1;
			setNum1IsSet(true);
			return this;
		}

		public void unsetNum1() {
			__isset_bitfield = EncodingUtils.clearBit(__isset_bitfield,
					__NUM1_ISSET_ID);
		}

		public boolean isSetNum1() {
			return EncodingUtils.testBit(__isset_bitfield, __NUM1_ISSET_ID);
		}

		public void setNum1IsSet(boolean value) {
			__isset_bitfield = EncodingUtils.setBit(__isset_bitfield,
					__NUM1_ISSET_ID, value);
		}

		public int getNum2() {
			return this.num2;
		}

		public testCase1_args setNum2(int num2) {
			this.num2 = num2;
			setNum2IsSet(true);
			return this;
		}

		public void unsetNum2() {
			__isset_bitfield = EncodingUtils.clearBit(__isset_bitfield,
					__NUM2_ISSET_ID);
		}

		public boolean isSetNum2() {
			return EncodingUtils.testBit(__isset_bitfield, __NUM2_ISSET_ID);
		}

		public void setNum2IsSet(boolean value) {
			__isset_bitfield = EncodingUtils.setBit(__isset_bitfield,
					__NUM2_ISSET_ID, value);
		}

		public String getNum3() {
			return this.num3;
		}

		public testCase1_args setNum3(String num3) {
			this.num3 = num3;
			return this;
		}

		public void unsetNum3() {
			this.num3 = null;
		}

		public boolean isSetNum3() {
			return this.num3 != null;
		}

		public void setNum3IsSet(boolean value) {
			if (!value) {
				this.num3 = null;
			}
		}

		public void setFieldValue(_Fields field, Object value) {
			switch (field) {
			case NUM1:
				if (value == null) {
					unsetNum1();
				} else {
					setNum1((Integer) value);
				}
				break;

			case NUM2:
				if (value == null) {
					unsetNum2();
				} else {
					setNum2((Integer) value);
				}
				break;

			case NUM3:
				if (value == null) {
					unsetNum3();
				} else {
					setNum3((String) value);
				}
				break;

			}
		}

		public Object getFieldValue(_Fields field) {
			switch (field) {
			case NUM1:
				return Integer.valueOf(getNum1());

			case NUM2:
				return Integer.valueOf(getNum2());

			case NUM3:
				return getNum3();

			}
			throw new IllegalStateException();
		}

		public boolean isSet(_Fields field) {
			if (field == null) {
				throw new IllegalArgumentException();
			}

			switch (field) {
			case NUM1:
				return isSetNum1();
			case NUM2:
				return isSetNum2();
			case NUM3:
				return isSetNum3();
			}
			throw new IllegalStateException();
		}

		@Override
		public boolean equals(Object that) {
			if (that == null)
				return false;
			if (that instanceof testCase1_args)
				return this.equals((testCase1_args) that);
			return false;
		}

		public boolean equals(testCase1_args that) {
			if (that == null)
				return false;

			boolean this_present_num1 = true;
			boolean that_present_num1 = true;
			if (this_present_num1 || that_present_num1) {
				if (!(this_present_num1 && that_present_num1))
					return false;
				if (this.num1 != that.num1)
					return false;
			}

			boolean this_present_num2 = true;
			boolean that_present_num2 = true;
			if (this_present_num2 || that_present_num2) {
				if (!(this_present_num2 && that_present_num2))
					return false;
				if (this.num2 != that.num2)
					return false;
			}

			boolean this_present_num3 = true && this.isSetNum3();
			boolean that_present_num3 = true && that.isSetNum3();
			if (this_present_num3 || that_present_num3) {
				if (!(this_present_num3 && that_present_num3))
					return false;
				if (!this.num3.equals(that.num3))
					return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public int compareTo(testCase1_args other) {
			if (!getClass().equals(other.getClass())) {
				return getClass().getName().compareTo(
						other.getClass().getName());
			}

			int lastComparison = 0;

			lastComparison = Boolean.valueOf(isSetNum1()).compareTo(
					other.isSetNum1());
			if (lastComparison != 0) {
				return lastComparison;
			}
			if (isSetNum1()) {
				lastComparison = org.apache.thrift.TBaseHelper.compareTo(
						this.num1, other.num1);
				if (lastComparison != 0) {
					return lastComparison;
				}
			}
			lastComparison = Boolean.valueOf(isSetNum2()).compareTo(
					other.isSetNum2());
			if (lastComparison != 0) {
				return lastComparison;
			}
			if (isSetNum2()) {
				lastComparison = org.apache.thrift.TBaseHelper.compareTo(
						this.num2, other.num2);
				if (lastComparison != 0) {
					return lastComparison;
				}
			}
			lastComparison = Boolean.valueOf(isSetNum3()).compareTo(
					other.isSetNum3());
			if (lastComparison != 0) {
				return lastComparison;
			}
			if (isSetNum3()) {
				lastComparison = org.apache.thrift.TBaseHelper.compareTo(
						this.num3, other.num3);
				if (lastComparison != 0) {
					return lastComparison;
				}
			}
			return 0;
		}

		public _Fields fieldForId(int fieldId) {
			return _Fields.findByThriftId(fieldId);
		}

		public void read(org.apache.thrift.protocol.TProtocol iprot)
				throws org.apache.thrift.TException {
			schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
		}

		public void write(org.apache.thrift.protocol.TProtocol oprot)
				throws org.apache.thrift.TException {
			schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("testCase1_args(");
			boolean first = true;

			sb.append("num1:");
			sb.append(this.num1);
			first = false;
			if (!first)
				sb.append(", ");
			sb.append("num2:");
			sb.append(this.num2);
			first = false;
			if (!first)
				sb.append(", ");
			sb.append("num3:");
			if (this.num3 == null) {
				sb.append("null");
			} else {
				sb.append(this.num3);
			}
			first = false;
			sb.append(")");
			return sb.toString();
		}

		public void validate() throws org.apache.thrift.TException {
			// check for required fields
			// check for sub-struct validity
		}

		private void writeObject(java.io.ObjectOutputStream out)
				throws java.io.IOException {
			try {
				write(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(out)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private void readObject(java.io.ObjectInputStream in)
				throws java.io.IOException, ClassNotFoundException {
			try {
				// it doesn't seem like you should have to do this, but java
				// serialization is wacky, and doesn't call the default
				// constructor.
				__isset_bitfield = 0;
				read(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(in)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private static class testCase1_argsStandardSchemeFactory implements
				SchemeFactory {
			public testCase1_argsStandardScheme getScheme() {
				return new testCase1_argsStandardScheme();
			}
		}

		private static class testCase1_argsStandardScheme extends
				StandardScheme<testCase1_args> {

			public void read(org.apache.thrift.protocol.TProtocol iprot,
					testCase1_args struct) throws org.apache.thrift.TException {
				org.apache.thrift.protocol.TField schemeField;
				iprot.readStructBegin();
				while (true) {
					schemeField = iprot.readFieldBegin();
					if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
						break;
					}
					switch (schemeField.id) {
					case 1: // NUM1
						if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
							struct.num1 = iprot.readI32();
							struct.setNum1IsSet(true);
						} else {
							org.apache.thrift.protocol.TProtocolUtil.skip(
									iprot, schemeField.type);
						}
						break;
					case 2: // NUM2
						if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
							struct.num2 = iprot.readI32();
							struct.setNum2IsSet(true);
						} else {
							org.apache.thrift.protocol.TProtocolUtil.skip(
									iprot, schemeField.type);
						}
						break;
					case 3: // NUM3
						if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
							struct.num3 = iprot.readString();
							struct.setNum3IsSet(true);
						} else {
							org.apache.thrift.protocol.TProtocolUtil.skip(
									iprot, schemeField.type);
						}
						break;
					default:
						org.apache.thrift.protocol.TProtocolUtil.skip(iprot,
								schemeField.type);
					}
					iprot.readFieldEnd();
				}
				iprot.readStructEnd();

				// check for required fields of primitive type, which can't be
				// checked in the validate method
				struct.validate();
			}

			public void write(org.apache.thrift.protocol.TProtocol oprot,
					testCase1_args struct) throws org.apache.thrift.TException {
				struct.validate();

				oprot.writeStructBegin(STRUCT_DESC);
				oprot.writeFieldBegin(NUM1_FIELD_DESC);
				oprot.writeI32(struct.num1);
				oprot.writeFieldEnd();
				oprot.writeFieldBegin(NUM2_FIELD_DESC);
				oprot.writeI32(struct.num2);
				oprot.writeFieldEnd();
				if (struct.num3 != null) {
					oprot.writeFieldBegin(NUM3_FIELD_DESC);
					oprot.writeString(struct.num3);
					oprot.writeFieldEnd();
				}
				oprot.writeFieldStop();
				oprot.writeStructEnd();
			}

		}

		private static class testCase1_argsTupleSchemeFactory implements
				SchemeFactory {
			public testCase1_argsTupleScheme getScheme() {
				return new testCase1_argsTupleScheme();
			}
		}

		private static class testCase1_argsTupleScheme extends
				TupleScheme<testCase1_args> {

			@Override
			public void write(org.apache.thrift.protocol.TProtocol prot,
					testCase1_args struct) throws org.apache.thrift.TException {
				TTupleProtocol oprot = (TTupleProtocol) prot;
				BitSet optionals = new BitSet();
				if (struct.isSetNum1()) {
					optionals.set(0);
				}
				if (struct.isSetNum2()) {
					optionals.set(1);
				}
				if (struct.isSetNum3()) {
					optionals.set(2);
				}
				oprot.writeBitSet(optionals, 3);
				if (struct.isSetNum1()) {
					oprot.writeI32(struct.num1);
				}
				if (struct.isSetNum2()) {
					oprot.writeI32(struct.num2);
				}
				if (struct.isSetNum3()) {
					oprot.writeString(struct.num3);
				}
			}

			@Override
			public void read(org.apache.thrift.protocol.TProtocol prot,
					testCase1_args struct) throws org.apache.thrift.TException {
				TTupleProtocol iprot = (TTupleProtocol) prot;
				BitSet incoming = iprot.readBitSet(3);
				if (incoming.get(0)) {
					struct.num1 = iprot.readI32();
					struct.setNum1IsSet(true);
				}
				if (incoming.get(1)) {
					struct.num2 = iprot.readI32();
					struct.setNum2IsSet(true);
				}
				if (incoming.get(2)) {
					struct.num3 = iprot.readString();
					struct.setNum3IsSet(true);
				}
			}
		}

	}

	public static class testCase1_result
			implements
			org.apache.thrift.TBase<testCase1_result, testCase1_result._Fields>,
			java.io.Serializable, Cloneable, Comparable<testCase1_result> {
		private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
				"testCase1_result");

		private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField(
				"success", org.apache.thrift.protocol.TType.I32, (short) 0);

		private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
		static {
			schemes.put(StandardScheme.class,
					new testCase1_resultStandardSchemeFactory());
			schemes.put(TupleScheme.class,
					new testCase1_resultTupleSchemeFactory());
		}

		public int success; // required

		public enum _Fields implements org.apache.thrift.TFieldIdEnum {
			SUCCESS((short) 0, "success");

			private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

			static {
				for (_Fields field : EnumSet.allOf(_Fields.class)) {
					byName.put(field.getFieldName(), field);
				}
			}

			public static _Fields findByThriftId(int fieldId) {
				switch (fieldId) {
				case 0: // SUCCESS
					return SUCCESS;
				default:
					return null;
				}
			}

			public static _Fields findByThriftIdOrThrow(int fieldId) {
				_Fields fields = findByThriftId(fieldId);
				if (fields == null)
					throw new IllegalArgumentException("Field " + fieldId
							+ " doesn't exist!");
				return fields;
			}

			public static _Fields findByName(String name) {
				return byName.get(name);
			}

			private final short _thriftId;
			private final String _fieldName;

			_Fields(short thriftId, String fieldName) {
				_thriftId = thriftId;
				_fieldName = fieldName;
			}

			public short getThriftFieldId() {
				return _thriftId;
			}

			public String getFieldName() {
				return _fieldName;
			}
		}

		// isset id assignments
		private static final int __SUCCESS_ISSET_ID = 0;
		private byte __isset_bitfield = 0;
		public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
		static {
			Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
					_Fields.class);
			tmpMap.put(_Fields.SUCCESS,
					new org.apache.thrift.meta_data.FieldMetaData("success",
							org.apache.thrift.TFieldRequirementType.DEFAULT,
							new org.apache.thrift.meta_data.FieldValueMetaData(
									org.apache.thrift.protocol.TType.I32)));
			metaDataMap = Collections.unmodifiableMap(tmpMap);
			org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(
					testCase1_result.class, metaDataMap);
		}

		public testCase1_result() {
		}

		public testCase1_result(int success) {
			this();
			this.success = success;
			setSuccessIsSet(true);
		}

		/**
		 * Performs a deep copy on <i>other</i>.
		 */
		public testCase1_result(testCase1_result other) {
			__isset_bitfield = other.__isset_bitfield;
			this.success = other.success;
		}

		public testCase1_result deepCopy() {
			return new testCase1_result(this);
		}

		@Override
		public void clear() {
			setSuccessIsSet(false);
			this.success = 0;
		}

		public int getSuccess() {
			return this.success;
		}

		public testCase1_result setSuccess(int success) {
			this.success = success;
			setSuccessIsSet(true);
			return this;
		}

		public void unsetSuccess() {
			__isset_bitfield = EncodingUtils.clearBit(__isset_bitfield,
					__SUCCESS_ISSET_ID);
		}

		public boolean isSetSuccess() {
			return EncodingUtils.testBit(__isset_bitfield, __SUCCESS_ISSET_ID);
		}

		public void setSuccessIsSet(boolean value) {
			__isset_bitfield = EncodingUtils.setBit(__isset_bitfield,
					__SUCCESS_ISSET_ID, value);
		}

		public void setFieldValue(_Fields field, Object value) {
			switch (field) {
			case SUCCESS:
				if (value == null) {
					unsetSuccess();
				} else {
					setSuccess((Integer) value);
				}
				break;

			}
		}

		public Object getFieldValue(_Fields field) {
			switch (field) {
			case SUCCESS:
				return Integer.valueOf(getSuccess());

			}
			throw new IllegalStateException();
		}

		public boolean isSet(_Fields field) {
			if (field == null) {
				throw new IllegalArgumentException();
			}

			switch (field) {
			case SUCCESS:
				return isSetSuccess();
			}
			throw new IllegalStateException();
		}

		@Override
		public boolean equals(Object that) {
			if (that == null)
				return false;
			if (that instanceof testCase1_result)
				return this.equals((testCase1_result) that);
			return false;
		}

		public boolean equals(testCase1_result that) {
			if (that == null)
				return false;

			boolean this_present_success = true;
			boolean that_present_success = true;
			if (this_present_success || that_present_success) {
				if (!(this_present_success && that_present_success))
					return false;
				if (this.success != that.success)
					return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public int compareTo(testCase1_result other) {
			if (!getClass().equals(other.getClass())) {
				return getClass().getName().compareTo(
						other.getClass().getName());
			}

			int lastComparison = 0;

			lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(
					other.isSetSuccess());
			if (lastComparison != 0) {
				return lastComparison;
			}
			if (isSetSuccess()) {
				lastComparison = org.apache.thrift.TBaseHelper.compareTo(
						this.success, other.success);
				if (lastComparison != 0) {
					return lastComparison;
				}
			}
			return 0;
		}

		public _Fields fieldForId(int fieldId) {
			return _Fields.findByThriftId(fieldId);
		}

		public void read(org.apache.thrift.protocol.TProtocol iprot)
				throws org.apache.thrift.TException {
			schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
		}

		public void write(org.apache.thrift.protocol.TProtocol oprot)
				throws org.apache.thrift.TException {
			schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("testCase1_result(");
			boolean first = true;

			sb.append("success:");
			sb.append(this.success);
			first = false;
			sb.append(")");
			return sb.toString();
		}

		public void validate() throws org.apache.thrift.TException {
			// check for required fields
			// check for sub-struct validity
		}

		private void writeObject(java.io.ObjectOutputStream out)
				throws java.io.IOException {
			try {
				write(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(out)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private void readObject(java.io.ObjectInputStream in)
				throws java.io.IOException, ClassNotFoundException {
			try {
				// it doesn't seem like you should have to do this, but java
				// serialization is wacky, and doesn't call the default
				// constructor.
				__isset_bitfield = 0;
				read(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(in)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private static class testCase1_resultStandardSchemeFactory implements
				SchemeFactory {
			public testCase1_resultStandardScheme getScheme() {
				return new testCase1_resultStandardScheme();
			}
		}

		private static class testCase1_resultStandardScheme extends
				StandardScheme<testCase1_result> {

			public void read(org.apache.thrift.protocol.TProtocol iprot,
					testCase1_result struct)
					throws org.apache.thrift.TException {
				org.apache.thrift.protocol.TField schemeField;
				iprot.readStructBegin();
				while (true) {
					schemeField = iprot.readFieldBegin();
					if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
						break;
					}
					switch (schemeField.id) {
					case 0: // SUCCESS
						if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
							struct.success = iprot.readI32();
							struct.setSuccessIsSet(true);
						} else {
							org.apache.thrift.protocol.TProtocolUtil.skip(
									iprot, schemeField.type);
						}
						break;
					default:
						org.apache.thrift.protocol.TProtocolUtil.skip(iprot,
								schemeField.type);
					}
					iprot.readFieldEnd();
				}
				iprot.readStructEnd();

				// check for required fields of primitive type, which can't be
				// checked in the validate method
				struct.validate();
			}

			public void write(org.apache.thrift.protocol.TProtocol oprot,
					testCase1_result struct)
					throws org.apache.thrift.TException {
				struct.validate();

				oprot.writeStructBegin(STRUCT_DESC);
				if (struct.isSetSuccess()) {
					oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
					oprot.writeI32(struct.success);
					oprot.writeFieldEnd();
				}
				oprot.writeFieldStop();
				oprot.writeStructEnd();
			}

		}

		private static class testCase1_resultTupleSchemeFactory implements
				SchemeFactory {
			public testCase1_resultTupleScheme getScheme() {
				return new testCase1_resultTupleScheme();
			}
		}

		private static class testCase1_resultTupleScheme extends
				TupleScheme<testCase1_result> {

			@Override
			public void write(org.apache.thrift.protocol.TProtocol prot,
					testCase1_result struct)
					throws org.apache.thrift.TException {
				TTupleProtocol oprot = (TTupleProtocol) prot;
				BitSet optionals = new BitSet();
				if (struct.isSetSuccess()) {
					optionals.set(0);
				}
				oprot.writeBitSet(optionals, 1);
				if (struct.isSetSuccess()) {
					oprot.writeI32(struct.success);
				}
			}

			@Override
			public void read(org.apache.thrift.protocol.TProtocol prot,
					testCase1_result struct)
					throws org.apache.thrift.TException {
				TTupleProtocol iprot = (TTupleProtocol) prot;
				BitSet incoming = iprot.readBitSet(1);
				if (incoming.get(0)) {
					struct.success = iprot.readI32();
					struct.setSuccessIsSet(true);
				}
			}
		}

	}

	public static class testCase2_args implements
			org.apache.thrift.TBase<testCase2_args, testCase2_args._Fields>,
			java.io.Serializable, Cloneable, Comparable<testCase2_args> {
		private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
				"testCase2_args");

		private static final org.apache.thrift.protocol.TField NUM1_FIELD_DESC = new org.apache.thrift.protocol.TField(
				"num1", org.apache.thrift.protocol.TType.MAP, (short) 1);

		private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
		static {
			schemes.put(StandardScheme.class,
					new testCase2_argsStandardSchemeFactory());
			schemes.put(TupleScheme.class,
					new testCase2_argsTupleSchemeFactory());
		}

		public Map<String, String> num1; // required

		public enum _Fields implements org.apache.thrift.TFieldIdEnum {
			NUM1((short) 1, "num1");

			private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

			static {
				for (_Fields field : EnumSet.allOf(_Fields.class)) {
					byName.put(field.getFieldName(), field);
				}
			}

			public static _Fields findByThriftId(int fieldId) {
				switch (fieldId) {
				case 1: // NUM1
					return NUM1;
				default:
					return null;
				}
			}

			public static _Fields findByThriftIdOrThrow(int fieldId) {
				_Fields fields = findByThriftId(fieldId);
				if (fields == null)
					throw new IllegalArgumentException("Field " + fieldId
							+ " doesn't exist!");
				return fields;
			}
			public static _Fields findByName(String name) {
				return byName.get(name);
			}

			private final short _thriftId;
			private final String _fieldName;

			_Fields(short thriftId, String fieldName) {
				_thriftId = thriftId;
				_fieldName = fieldName;
			}

			public short getThriftFieldId() {
				return _thriftId;
			}

			public String getFieldName() {
				return _fieldName;
			}
		}

		// isset id assignments
		public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
		static {
			Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
					_Fields.class);
			tmpMap.put(
					_Fields.NUM1,
					new org.apache.thrift.meta_data.FieldMetaData(
							"num1",
							org.apache.thrift.TFieldRequirementType.DEFAULT,
							new org.apache.thrift.meta_data.MapMetaData(
									org.apache.thrift.protocol.TType.MAP,
									new org.apache.thrift.meta_data.FieldValueMetaData(
											org.apache.thrift.protocol.TType.STRING),
									new org.apache.thrift.meta_data.FieldValueMetaData(
											org.apache.thrift.protocol.TType.STRING))));
			metaDataMap = Collections.unmodifiableMap(tmpMap);
			org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(
					testCase2_args.class, metaDataMap);
		}

		public testCase2_args() {
		}

		public testCase2_args(Map<String, String> num1) {
			this();
			this.num1 = num1;
		}
		public testCase2_args(testCase2_args other) {
			if (other.isSetNum1()) {
				Map<String, String> __this__num1 = new HashMap<String, String>(
						other.num1);
				this.num1 = __this__num1;
			}
		}

		public testCase2_args deepCopy() {
			return new testCase2_args(this);
		}

		@Override
		public void clear() {
			this.num1 = null;
		}

		public int getNum1Size() {
			return (this.num1 == null) ? 0 : this.num1.size();
		}

		public void putToNum1(String key, String val) {
			if (this.num1 == null) {
				this.num1 = new HashMap<String, String>();
			}
			this.num1.put(key, val);
		}

		public Map<String, String> getNum1() {
			return this.num1;
		}

		public testCase2_args setNum1(Map<String, String> num1) {
			this.num1 = num1;
			return this;
		}

		public void unsetNum1() {
			this.num1 = null;
		}

		/**
		 * Returns true if field num1 is set (has been assigned a value) and
		 * false otherwise
		 */
		public boolean isSetNum1() {
			return this.num1 != null;
		}

		public void setNum1IsSet(boolean value) {
			if (!value) {
				this.num1 = null;
			}
		}

		public void setFieldValue(_Fields field, Object value) {
			switch (field) {
			case NUM1:
				if (value == null) {
					unsetNum1();
				} else {
					setNum1((Map<String, String>) value);
				}
				break;

			}
		}

		public Object getFieldValue(_Fields field) {
			switch (field) {
			case NUM1:
				return getNum1();

			}
			throw new IllegalStateException();
		}

		public boolean isSet(_Fields field) {
			if (field == null) {
				throw new IllegalArgumentException();
			}

			switch (field) {
			case NUM1:
				return isSetNum1();
			}
			throw new IllegalStateException();
		}

		@Override
		public boolean equals(Object that) {
			if (that == null)
				return false;
			if (that instanceof testCase2_args)
				return this.equals((testCase2_args) that);
			return false;
		}

		public boolean equals(testCase2_args that) {
			if (that == null)
				return false;

			boolean this_present_num1 = true && this.isSetNum1();
			boolean that_present_num1 = true && that.isSetNum1();
			if (this_present_num1 || that_present_num1) {
				if (!(this_present_num1 && that_present_num1))
					return false;
				if (!this.num1.equals(that.num1))
					return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public int compareTo(testCase2_args other) {
			if (!getClass().equals(other.getClass())) {
				return getClass().getName().compareTo(
						other.getClass().getName());
			}

			int lastComparison = 0;

			lastComparison = Boolean.valueOf(isSetNum1()).compareTo(
					other.isSetNum1());
			if (lastComparison != 0) {
				return lastComparison;
			}
			if (isSetNum1()) {
				lastComparison = org.apache.thrift.TBaseHelper.compareTo(
						this.num1, other.num1);
				if (lastComparison != 0) {
					return lastComparison;
				}
			}
			return 0;
		}

		public _Fields fieldForId(int fieldId) {
			return _Fields.findByThriftId(fieldId);
		}

		public void read(org.apache.thrift.protocol.TProtocol iprot)
				throws org.apache.thrift.TException {
			schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
		}

		public void write(org.apache.thrift.protocol.TProtocol oprot)
				throws org.apache.thrift.TException {
			schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("testCase2_args(");
			boolean first = true;

			sb.append("num1:");
			if (this.num1 == null) {
				sb.append("null");
			} else {
				sb.append(this.num1);
			}
			first = false;
			sb.append(")");
			return sb.toString();
		}

		public void validate() throws org.apache.thrift.TException {
			// check for required fields
			// check for sub-struct validity
		}

		private void writeObject(java.io.ObjectOutputStream out)
				throws java.io.IOException {
			try {
				write(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(out)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private void readObject(java.io.ObjectInputStream in)
				throws java.io.IOException, ClassNotFoundException {
			try {
				read(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(in)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private static class testCase2_argsStandardSchemeFactory implements
				SchemeFactory {
			public testCase2_argsStandardScheme getScheme() {
				return new testCase2_argsStandardScheme();
			}
		}

		private static class testCase2_argsStandardScheme extends
				StandardScheme<testCase2_args> {

			public void read(org.apache.thrift.protocol.TProtocol iprot,
					testCase2_args struct) throws org.apache.thrift.TException {
				org.apache.thrift.protocol.TField schemeField;
				iprot.readStructBegin();
				while (true) {
					schemeField = iprot.readFieldBegin();
					if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
						break;
					}
					switch (schemeField.id) {
					case 1: // NUM1
						if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
							{
								org.apache.thrift.protocol.TMap _map10 = iprot
										.readMapBegin();
								struct.num1 = new HashMap<String, String>(
										2 * _map10.size);
								for (int _i11 = 0; _i11 < _map10.size; ++_i11) {
									String _key12;
									String _val13;
									_key12 = iprot.readString();
									_val13 = iprot.readString();
									struct.num1.put(_key12, _val13);
								}
								iprot.readMapEnd();
							}
							struct.setNum1IsSet(true);
						} else {
							org.apache.thrift.protocol.TProtocolUtil.skip(
									iprot, schemeField.type);
						}
						break;
					default:
						org.apache.thrift.protocol.TProtocolUtil.skip(iprot,
								schemeField.type);
					}
					iprot.readFieldEnd();
				}
				iprot.readStructEnd();

				// check for required fields of primitive type, which can't be
				// checked in the validate method
				struct.validate();
			}

			public void write(org.apache.thrift.protocol.TProtocol oprot,
					testCase2_args struct) throws org.apache.thrift.TException {
				struct.validate();

				oprot.writeStructBegin(STRUCT_DESC);
				if (struct.num1 != null) {
					oprot.writeFieldBegin(NUM1_FIELD_DESC);
					{
						oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(
								org.apache.thrift.protocol.TType.STRING,
								org.apache.thrift.protocol.TType.STRING,
								struct.num1.size()));
						for (Map.Entry<String, String> _iter14 : struct.num1
								.entrySet()) {
							oprot.writeString(_iter14.getKey());
							oprot.writeString(_iter14.getValue());
						}
						oprot.writeMapEnd();
					}
					oprot.writeFieldEnd();
				}
				oprot.writeFieldStop();
				oprot.writeStructEnd();
			}

		}

		private static class testCase2_argsTupleSchemeFactory implements
				SchemeFactory {
			public testCase2_argsTupleScheme getScheme() {
				return new testCase2_argsTupleScheme();
			}
		}

		private static class testCase2_argsTupleScheme extends
				TupleScheme<testCase2_args> {

			@Override
			public void write(org.apache.thrift.protocol.TProtocol prot,
					testCase2_args struct) throws org.apache.thrift.TException {
				TTupleProtocol oprot = (TTupleProtocol) prot;
				BitSet optionals = new BitSet();
				if (struct.isSetNum1()) {
					optionals.set(0);
				}
				oprot.writeBitSet(optionals, 1);
				if (struct.isSetNum1()) {
					{
						oprot.writeI32(struct.num1.size());
						for (Map.Entry<String, String> _iter15 : struct.num1
								.entrySet()) {
							oprot.writeString(_iter15.getKey());
							oprot.writeString(_iter15.getValue());
						}
					}
				}
			}

			@Override
			public void read(org.apache.thrift.protocol.TProtocol prot,
					testCase2_args struct) throws org.apache.thrift.TException {
				TTupleProtocol iprot = (TTupleProtocol) prot;
				BitSet incoming = iprot.readBitSet(1);
				if (incoming.get(0)) {
					{
						org.apache.thrift.protocol.TMap _map16 = new org.apache.thrift.protocol.TMap(
								org.apache.thrift.protocol.TType.STRING,
								org.apache.thrift.protocol.TType.STRING,
								iprot.readI32());
						struct.num1 = new HashMap<String, String>(
								2 * _map16.size);
						for (int _i17 = 0; _i17 < _map16.size; ++_i17) {
							String _key18;
							String _val19;
							_key18 = iprot.readString();
							_val19 = iprot.readString();
							struct.num1.put(_key18, _val19);
						}
					}
					struct.setNum1IsSet(true);
				}
			}
		}

	}

	public static class testCase2_result
			implements
			org.apache.thrift.TBase<testCase2_result, testCase2_result._Fields>,
			java.io.Serializable, Cloneable, Comparable<testCase2_result> {
		private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
				"testCase2_result");

		private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField(
				"success", org.apache.thrift.protocol.TType.LIST, (short) 0);

		private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
		static {
			schemes.put(StandardScheme.class,
					new testCase2_resultStandardSchemeFactory());
			schemes.put(TupleScheme.class,
					new testCase2_resultTupleSchemeFactory());
		}

		public List<String> success; // required

		public enum _Fields implements org.apache.thrift.TFieldIdEnum {
			SUCCESS((short) 0, "success");

			private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

			static {
				for (_Fields field : EnumSet.allOf(_Fields.class)) {
					byName.put(field.getFieldName(), field);
				}
			}

			public static _Fields findByThriftId(int fieldId) {
				switch (fieldId) {
				case 0: // SUCCESS
					return SUCCESS;
				default:
					return null;
				}
			}
			public static _Fields findByThriftIdOrThrow(int fieldId) {
				_Fields fields = findByThriftId(fieldId);
				if (fields == null)
					throw new IllegalArgumentException("Field " + fieldId
							+ " doesn't exist!");
				return fields;
			}

			public static _Fields findByName(String name) {
				return byName.get(name);
			}

			private final short _thriftId;
			private final String _fieldName;

			_Fields(short thriftId, String fieldName) {
				_thriftId = thriftId;
				_fieldName = fieldName;
			}

			public short getThriftFieldId() {
				return _thriftId;
			}

			public String getFieldName() {
				return _fieldName;
			}
		}

		// isset id assignments
		public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
		static {
			Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
					_Fields.class);
			tmpMap.put(
					_Fields.SUCCESS,
					new org.apache.thrift.meta_data.FieldMetaData(
							"success",
							org.apache.thrift.TFieldRequirementType.DEFAULT,
							new org.apache.thrift.meta_data.ListMetaData(
									org.apache.thrift.protocol.TType.LIST,
									new org.apache.thrift.meta_data.FieldValueMetaData(
											org.apache.thrift.protocol.TType.STRING))));
			metaDataMap = Collections.unmodifiableMap(tmpMap);
			org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(
					testCase2_result.class, metaDataMap);
		}

		public testCase2_result() {
		}

		public testCase2_result(List<String> success) {
			this();
			this.success = success;
		}
		public testCase2_result(testCase2_result other) {
			if (other.isSetSuccess()) {
				List<String> __this__success = new ArrayList<String>(
						other.success);
				this.success = __this__success;
			}
		}

		public testCase2_result deepCopy() {
			return new testCase2_result(this);
		}

		@Override
		public void clear() {
			this.success = null;
		}

		public int getSuccessSize() {
			return (this.success == null) ? 0 : this.success.size();
		}

		public java.util.Iterator<String> getSuccessIterator() {
			return (this.success == null) ? null : this.success.iterator();
		}

		public void addToSuccess(String elem) {
			if (this.success == null) {
				this.success = new ArrayList<String>();
			}
			this.success.add(elem);
		}

		public List<String> getSuccess() {
			return this.success;
		}

		public testCase2_result setSuccess(List<String> success) {
			this.success = success;
			return this;
		}

		public void unsetSuccess() {
			this.success = null;
		}

		public boolean isSetSuccess() {
			return this.success != null;
		}

		public void setSuccessIsSet(boolean value) {
			if (!value) {
				this.success = null;
			}
		}

		public void setFieldValue(_Fields field, Object value) {
			switch (field) {
			case SUCCESS:
				if (value == null) {
					unsetSuccess();
				} else {
					setSuccess((List<String>) value);
				}
				break;

			}
		}

		public Object getFieldValue(_Fields field) {
			switch (field) {
			case SUCCESS:
				return getSuccess();

			}
			throw new IllegalStateException();
		}

		public boolean isSet(_Fields field) {
			if (field == null) {
				throw new IllegalArgumentException();
			}

			switch (field) {
			case SUCCESS:
				return isSetSuccess();
			}
			throw new IllegalStateException();
		}

		@Override
		public boolean equals(Object that) {
			if (that == null)
				return false;
			if (that instanceof testCase2_result)
				return this.equals((testCase2_result) that);
			return false;
		}

		public boolean equals(testCase2_result that) {
			if (that == null)
				return false;

			boolean this_present_success = true && this.isSetSuccess();
			boolean that_present_success = true && that.isSetSuccess();
			if (this_present_success || that_present_success) {
				if (!(this_present_success && that_present_success))
					return false;
				if (!this.success.equals(that.success))
					return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public int compareTo(testCase2_result other) {
			if (!getClass().equals(other.getClass())) {
				return getClass().getName().compareTo(
						other.getClass().getName());
			}

			int lastComparison = 0;

			lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(
					other.isSetSuccess());
			if (lastComparison != 0) {
				return lastComparison;
			}
			if (isSetSuccess()) {
				lastComparison = org.apache.thrift.TBaseHelper.compareTo(
						this.success, other.success);
				if (lastComparison != 0) {
					return lastComparison;
				}
			}
			return 0;
		}

		public _Fields fieldForId(int fieldId) {
			return _Fields.findByThriftId(fieldId);
		}

		public void read(org.apache.thrift.protocol.TProtocol iprot)
				throws org.apache.thrift.TException {
			schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
		}

		public void write(org.apache.thrift.protocol.TProtocol oprot)
				throws org.apache.thrift.TException {
			schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("testCase2_result(");
			boolean first = true;

			sb.append("success:");
			if (this.success == null) {
				sb.append("null");
			} else {
				sb.append(this.success);
			}
			first = false;
			sb.append(")");
			return sb.toString();
		}

		public void validate() throws org.apache.thrift.TException {
			// check for required fields
			// check for sub-struct validity
		}

		private void writeObject(java.io.ObjectOutputStream out)
				throws java.io.IOException {
			try {
				write(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(out)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private void readObject(java.io.ObjectInputStream in)
				throws java.io.IOException, ClassNotFoundException {
			try {
				read(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(in)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private static class testCase2_resultStandardSchemeFactory implements
				SchemeFactory {
			public testCase2_resultStandardScheme getScheme() {
				return new testCase2_resultStandardScheme();
			}
		}

		private static class testCase2_resultStandardScheme extends
				StandardScheme<testCase2_result> {

			public void read(org.apache.thrift.protocol.TProtocol iprot,
					testCase2_result struct)
					throws org.apache.thrift.TException {
				org.apache.thrift.protocol.TField schemeField;
				iprot.readStructBegin();
				while (true) {
					schemeField = iprot.readFieldBegin();
					if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
						break;
					}
					switch (schemeField.id) {
					case 0: // SUCCESS
						if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
							{
								org.apache.thrift.protocol.TList _list20 = iprot
										.readListBegin();
								struct.success = new ArrayList<String>(
										_list20.size);
								for (int _i21 = 0; _i21 < _list20.size; ++_i21) {
									String _elem22;
									_elem22 = iprot.readString();
									struct.success.add(_elem22);
								}
								iprot.readListEnd();
							}
							struct.setSuccessIsSet(true);
						} else {
							org.apache.thrift.protocol.TProtocolUtil.skip(
									iprot, schemeField.type);
						}
						break;
					default:
						org.apache.thrift.protocol.TProtocolUtil.skip(iprot,
								schemeField.type);
					}
					iprot.readFieldEnd();
				}
				iprot.readStructEnd();

				// check for required fields of primitive type, which can't be
				// checked in the validate method
				struct.validate();
			}

			public void write(org.apache.thrift.protocol.TProtocol oprot,
					testCase2_result struct)
					throws org.apache.thrift.TException {
				struct.validate();

				oprot.writeStructBegin(STRUCT_DESC);
				if (struct.success != null) {
					oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
					{
						oprot.writeListBegin(new org.apache.thrift.protocol.TList(
								org.apache.thrift.protocol.TType.STRING,
								struct.success.size()));
						for (String _iter23 : struct.success) {
							oprot.writeString(_iter23);
						}
						oprot.writeListEnd();
					}
					oprot.writeFieldEnd();
				}
				oprot.writeFieldStop();
				oprot.writeStructEnd();
			}

		}

		private static class testCase2_resultTupleSchemeFactory implements
				SchemeFactory {
			public testCase2_resultTupleScheme getScheme() {
				return new testCase2_resultTupleScheme();
			}
		}

		private static class testCase2_resultTupleScheme extends
				TupleScheme<testCase2_result> {

			@Override
			public void write(org.apache.thrift.protocol.TProtocol prot,
					testCase2_result struct)
					throws org.apache.thrift.TException {
				TTupleProtocol oprot = (TTupleProtocol) prot;
				BitSet optionals = new BitSet();
				if (struct.isSetSuccess()) {
					optionals.set(0);
				}
				oprot.writeBitSet(optionals, 1);
				if (struct.isSetSuccess()) {
					{
						oprot.writeI32(struct.success.size());
						for (String _iter24 : struct.success) {
							oprot.writeString(_iter24);
						}
					}
				}
			}

			@Override
			public void read(org.apache.thrift.protocol.TProtocol prot,
					testCase2_result struct)
					throws org.apache.thrift.TException {
				TTupleProtocol iprot = (TTupleProtocol) prot;
				BitSet incoming = iprot.readBitSet(1);
				if (incoming.get(0)) {
					{
						org.apache.thrift.protocol.TList _list25 = new org.apache.thrift.protocol.TList(
								org.apache.thrift.protocol.TType.STRING,
								iprot.readI32());
						struct.success = new ArrayList<String>(_list25.size);
						for (int _i26 = 0; _i26 < _list25.size; ++_i26) {
							String _elem27;
							_elem27 = iprot.readString();
							struct.success.add(_elem27);
						}
					}
					struct.setSuccessIsSet(true);
				}
			}
		}

	}

	public static class testCase3_args implements
			org.apache.thrift.TBase<testCase3_args, testCase3_args._Fields>,
			java.io.Serializable, Cloneable, Comparable<testCase3_args> {
		private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
				"testCase3_args");

		private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
		static {
			schemes.put(StandardScheme.class,
					new testCase3_argsStandardSchemeFactory());
			schemes.put(TupleScheme.class,
					new testCase3_argsTupleSchemeFactory());
		}

		public enum _Fields implements org.apache.thrift.TFieldIdEnum {
			;

			private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

			static {
				for (_Fields field : EnumSet.allOf(_Fields.class)) {
					byName.put(field.getFieldName(), field);
				}
			}

			public static _Fields findByThriftId(int fieldId) {
				switch (fieldId) {
				default:
					return null;
				}
			}
			public static _Fields findByThriftIdOrThrow(int fieldId) {
				_Fields fields = findByThriftId(fieldId);
				if (fields == null)
					throw new IllegalArgumentException("Field " + fieldId
							+ " doesn't exist!");
				return fields;
			}

			public static _Fields findByName(String name) {
				return byName.get(name);
			}

			private final short _thriftId;
			private final String _fieldName;

			_Fields(short thriftId, String fieldName) {
				_thriftId = thriftId;
				_fieldName = fieldName;
			}

			public short getThriftFieldId() {
				return _thriftId;
			}

			public String getFieldName() {
				return _fieldName;
			}
		}

		public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
		static {
			Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
					_Fields.class);
			metaDataMap = Collections.unmodifiableMap(tmpMap);
			org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(
					testCase3_args.class, metaDataMap);
		}

		public testCase3_args() {
		}

		public testCase3_args(testCase3_args other) {
		}

		public testCase3_args deepCopy() {
			return new testCase3_args(this);
		}

		@Override
		public void clear() {
		}

		public void setFieldValue(_Fields field, Object value) {
			switch (field) {
			}
		}

		public Object getFieldValue(_Fields field) {
			switch (field) {
			}
			throw new IllegalStateException();
		}

		public boolean isSet(_Fields field) {
			if (field == null) {
				throw new IllegalArgumentException();
			}

			switch (field) {
			}
			throw new IllegalStateException();
		}

		@Override
		public boolean equals(Object that) {
			if (that == null)
				return false;
			if (that instanceof testCase3_args)
				return this.equals((testCase3_args) that);
			return false;
		}

		public boolean equals(testCase3_args that) {
			if (that == null)
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public int compareTo(testCase3_args other) {
			if (!getClass().equals(other.getClass())) {
				return getClass().getName().compareTo(
						other.getClass().getName());
			}

			int lastComparison = 0;

			return 0;
		}

		public _Fields fieldForId(int fieldId) {
			return _Fields.findByThriftId(fieldId);
		}

		public void read(org.apache.thrift.protocol.TProtocol iprot)
				throws org.apache.thrift.TException {
			schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
		}

		public void write(org.apache.thrift.protocol.TProtocol oprot)
				throws org.apache.thrift.TException {
			schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("testCase3_args(");
			boolean first = true;

			sb.append(")");
			return sb.toString();
		}

		public void validate() throws org.apache.thrift.TException {
			// check for required fields
			// check for sub-struct validity
		}

		private void writeObject(java.io.ObjectOutputStream out)
				throws java.io.IOException {
			try {
				write(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(out)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private void readObject(java.io.ObjectInputStream in)
				throws java.io.IOException, ClassNotFoundException {
			try {
				read(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(in)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private static class testCase3_argsStandardSchemeFactory implements
				SchemeFactory {
			public testCase3_argsStandardScheme getScheme() {
				return new testCase3_argsStandardScheme();
			}
		}

		private static class testCase3_argsStandardScheme extends
				StandardScheme<testCase3_args> {

			public void read(org.apache.thrift.protocol.TProtocol iprot,
					testCase3_args struct) throws org.apache.thrift.TException {
				org.apache.thrift.protocol.TField schemeField;
				iprot.readStructBegin();
				while (true) {
					schemeField = iprot.readFieldBegin();
					if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
						break;
					}
					switch (schemeField.id) {
					default:
						org.apache.thrift.protocol.TProtocolUtil.skip(iprot,
								schemeField.type);
					}
					iprot.readFieldEnd();
				}
				iprot.readStructEnd();

				// check for required fields of primitive type, which can't be
				// checked in the validate method
				struct.validate();
			}

			public void write(org.apache.thrift.protocol.TProtocol oprot,
					testCase3_args struct) throws org.apache.thrift.TException {
				struct.validate();

				oprot.writeStructBegin(STRUCT_DESC);
				oprot.writeFieldStop();
				oprot.writeStructEnd();
			}

		}

		private static class testCase3_argsTupleSchemeFactory implements
				SchemeFactory {
			public testCase3_argsTupleScheme getScheme() {
				return new testCase3_argsTupleScheme();
			}
		}

		private static class testCase3_argsTupleScheme extends
				TupleScheme<testCase3_args> {

			@Override
			public void write(org.apache.thrift.protocol.TProtocol prot,
					testCase3_args struct) throws org.apache.thrift.TException {
				TTupleProtocol oprot = (TTupleProtocol) prot;
			}

			@Override
			public void read(org.apache.thrift.protocol.TProtocol prot,
					testCase3_args struct) throws org.apache.thrift.TException {
				TTupleProtocol iprot = (TTupleProtocol) prot;
			}
		}

	}

	public static class testCase3_result
			implements
			org.apache.thrift.TBase<testCase3_result, testCase3_result._Fields>,
			java.io.Serializable, Cloneable, Comparable<testCase3_result> {
		private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
				"testCase3_result");

		private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
		static {
			schemes.put(StandardScheme.class,
					new testCase3_resultStandardSchemeFactory());
			schemes.put(TupleScheme.class,
					new testCase3_resultTupleSchemeFactory());
		}

		public enum _Fields implements org.apache.thrift.TFieldIdEnum {
			;

			private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

			static {
				for (_Fields field : EnumSet.allOf(_Fields.class)) {
					byName.put(field.getFieldName(), field);
				}
			}

			public static _Fields findByThriftId(int fieldId) {
				switch (fieldId) {
				default:
					return null;
				}
			}
			public static _Fields findByThriftIdOrThrow(int fieldId) {
				_Fields fields = findByThriftId(fieldId);
				if (fields == null)
					throw new IllegalArgumentException("Field " + fieldId
							+ " doesn't exist!");
				return fields;
			}

			public static _Fields findByName(String name) {
				return byName.get(name);
			}

			private final short _thriftId;
			private final String _fieldName;

			_Fields(short thriftId, String fieldName) {
				_thriftId = thriftId;
				_fieldName = fieldName;
			}

			public short getThriftFieldId() {
				return _thriftId;
			}

			public String getFieldName() {
				return _fieldName;
			}
		}

		public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
		static {
			Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
					_Fields.class);
			metaDataMap = Collections.unmodifiableMap(tmpMap);
			org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(
					testCase3_result.class, metaDataMap);
		}

		public testCase3_result() {
		}
		public testCase3_result(testCase3_result other) {
		}

		public testCase3_result deepCopy() {
			return new testCase3_result(this);
		}

		@Override
		public void clear() {
		}

		public void setFieldValue(_Fields field, Object value) {
			switch (field) {
			}
		}

		public Object getFieldValue(_Fields field) {
			switch (field) {
			}
			throw new IllegalStateException();
		}

		public boolean isSet(_Fields field) {
			if (field == null) {
				throw new IllegalArgumentException();
			}

			switch (field) {
			}
			throw new IllegalStateException();
		}

		@Override
		public boolean equals(Object that) {
			if (that == null)
				return false;
			if (that instanceof testCase3_result)
				return this.equals((testCase3_result) that);
			return false;
		}

		public boolean equals(testCase3_result that) {
			if (that == null)
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public int compareTo(testCase3_result other) {
			if (!getClass().equals(other.getClass())) {
				return getClass().getName().compareTo(
						other.getClass().getName());
			}

			int lastComparison = 0;

			return 0;
		}

		public _Fields fieldForId(int fieldId) {
			return _Fields.findByThriftId(fieldId);
		}

		public void read(org.apache.thrift.protocol.TProtocol iprot)
				throws org.apache.thrift.TException {
			schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
		}

		public void write(org.apache.thrift.protocol.TProtocol oprot)
				throws org.apache.thrift.TException {
			schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("testCase3_result(");
			boolean first = true;

			sb.append(")");
			return sb.toString();
		}

		public void validate() throws org.apache.thrift.TException {
			// check for required fields
			// check for sub-struct validity
		}

		private void writeObject(java.io.ObjectOutputStream out)
				throws java.io.IOException {
			try {
				write(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(out)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private void readObject(java.io.ObjectInputStream in)
				throws java.io.IOException, ClassNotFoundException {
			try {
				read(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(in)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private static class testCase3_resultStandardSchemeFactory implements
				SchemeFactory {
			public testCase3_resultStandardScheme getScheme() {
				return new testCase3_resultStandardScheme();
			}
		}

		private static class testCase3_resultStandardScheme extends
				StandardScheme<testCase3_result> {

			public void read(org.apache.thrift.protocol.TProtocol iprot,
					testCase3_result struct)
					throws org.apache.thrift.TException {
				org.apache.thrift.protocol.TField schemeField;
				iprot.readStructBegin();
				while (true) {
					schemeField = iprot.readFieldBegin();
					if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
						break;
					}
					switch (schemeField.id) {
					default:
						org.apache.thrift.protocol.TProtocolUtil.skip(iprot,
								schemeField.type);
					}
					iprot.readFieldEnd();
				}
				iprot.readStructEnd();

				// check for required fields of primitive type, which can't be
				// checked in the validate method
				struct.validate();
			}

			public void write(org.apache.thrift.protocol.TProtocol oprot,
					testCase3_result struct)
					throws org.apache.thrift.TException {
				struct.validate();

				oprot.writeStructBegin(STRUCT_DESC);
				oprot.writeFieldStop();
				oprot.writeStructEnd();
			}

		}

		private static class testCase3_resultTupleSchemeFactory implements
				SchemeFactory {
			public testCase3_resultTupleScheme getScheme() {
				return new testCase3_resultTupleScheme();
			}
		}

		private static class testCase3_resultTupleScheme extends
				TupleScheme<testCase3_result> {

			@Override
			public void write(org.apache.thrift.protocol.TProtocol prot,
					testCase3_result struct)
					throws org.apache.thrift.TException {
				TTupleProtocol oprot = (TTupleProtocol) prot;
			}

			@Override
			public void read(org.apache.thrift.protocol.TProtocol prot,
					testCase3_result struct)
					throws org.apache.thrift.TException {
				TTupleProtocol iprot = (TTupleProtocol) prot;
			}
		}

	}

	public static class testCase4_args implements
			org.apache.thrift.TBase<testCase4_args, testCase4_args._Fields>,
			java.io.Serializable, Cloneable, Comparable<testCase4_args> {
		private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
				"testCase4_args");

		private static final org.apache.thrift.protocol.TField BLOG_FIELD_DESC = new org.apache.thrift.protocol.TField(
				"blog", org.apache.thrift.protocol.TType.LIST, (short) 1);

		private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
		static {
			schemes.put(StandardScheme.class,
					new testCase4_argsStandardSchemeFactory());
			schemes.put(TupleScheme.class,
					new testCase4_argsTupleSchemeFactory());
		}

		public List<Blog> blog; // required

		public enum _Fields implements org.apache.thrift.TFieldIdEnum {
			BLOG((short) 1, "blog");

			private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

			static {
				for (_Fields field : EnumSet.allOf(_Fields.class)) {
					byName.put(field.getFieldName(), field);
				}
			}

			public static _Fields findByThriftId(int fieldId) {
				switch (fieldId) {
				case 1: // BLOG
					return BLOG;
				default:
					return null;
				}
			}
			public static _Fields findByThriftIdOrThrow(int fieldId) {
				_Fields fields = findByThriftId(fieldId);
				if (fields == null)
					throw new IllegalArgumentException("Field " + fieldId
							+ " doesn't exist!");
				return fields;
			}
			public static _Fields findByName(String name) {
				return byName.get(name);
			}

			private final short _thriftId;
			private final String _fieldName;

			_Fields(short thriftId, String fieldName) {
				_thriftId = thriftId;
				_fieldName = fieldName;
			}

			public short getThriftFieldId() {
				return _thriftId;
			}

			public String getFieldName() {
				return _fieldName;
			}
		}

		// isset id assignments
		public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
		static {
			Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
					_Fields.class);
			tmpMap.put(
					_Fields.BLOG,
					new org.apache.thrift.meta_data.FieldMetaData(
							"blog",
							org.apache.thrift.TFieldRequirementType.DEFAULT,
							new org.apache.thrift.meta_data.ListMetaData(
									org.apache.thrift.protocol.TType.LIST,
									new org.apache.thrift.meta_data.StructMetaData(
											org.apache.thrift.protocol.TType.STRUCT,
											Blog.class))));
			metaDataMap = Collections.unmodifiableMap(tmpMap);
			org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(
					testCase4_args.class, metaDataMap);
		}

		public testCase4_args() {
		}

		public testCase4_args(List<Blog> blog) {
			this();
			this.blog = blog;
		}
		public testCase4_args(testCase4_args other) {
			if (other.isSetBlog()) {
				List<Blog> __this__blog = new ArrayList<Blog>(other.blog.size());
				for (Blog other_element : other.blog) {
					__this__blog.add(new Blog(other_element));
				}
				this.blog = __this__blog;
			}
		}

		public testCase4_args deepCopy() {
			return new testCase4_args(this);
		}

		@Override
		public void clear() {
			this.blog = null;
		}

		public int getBlogSize() {
			return (this.blog == null) ? 0 : this.blog.size();
		}

		public java.util.Iterator<Blog> getBlogIterator() {
			return (this.blog == null) ? null : this.blog.iterator();
		}

		public void addToBlog(Blog elem) {
			if (this.blog == null) {
				this.blog = new ArrayList<Blog>();
			}
			this.blog.add(elem);
		}

		public List<Blog> getBlog() {
			return this.blog;
		}

		public testCase4_args setBlog(List<Blog> blog) {
			this.blog = blog;
			return this;
		}

		public void unsetBlog() {
			this.blog = null;
		}

		public boolean isSetBlog() {
			return this.blog != null;
		}

		public void setBlogIsSet(boolean value) {
			if (!value) {
				this.blog = null;
			}
		}

		public void setFieldValue(_Fields field, Object value) {
			switch (field) {
			case BLOG:
				if (value == null) {
					unsetBlog();
				} else {
					setBlog((List<Blog>) value);
				}
				break;

			}
		}

		public Object getFieldValue(_Fields field) {
			switch (field) {
			case BLOG:
				return getBlog();

			}
			throw new IllegalStateException();
		}
		public boolean isSet(_Fields field) {
			if (field == null) {
				throw new IllegalArgumentException();
			}

			switch (field) {
			case BLOG:
				return isSetBlog();
			}
			throw new IllegalStateException();
		}

		@Override
		public boolean equals(Object that) {
			if (that == null)
				return false;
			if (that instanceof testCase4_args)
				return this.equals((testCase4_args) that);
			return false;
		}

		public boolean equals(testCase4_args that) {
			if (that == null)
				return false;

			boolean this_present_blog = true && this.isSetBlog();
			boolean that_present_blog = true && that.isSetBlog();
			if (this_present_blog || that_present_blog) {
				if (!(this_present_blog && that_present_blog))
					return false;
				if (!this.blog.equals(that.blog))
					return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public int compareTo(testCase4_args other) {
			if (!getClass().equals(other.getClass())) {
				return getClass().getName().compareTo(
						other.getClass().getName());
			}

			int lastComparison = 0;

			lastComparison = Boolean.valueOf(isSetBlog()).compareTo(
					other.isSetBlog());
			if (lastComparison != 0) {
				return lastComparison;
			}
			if (isSetBlog()) {
				lastComparison = org.apache.thrift.TBaseHelper.compareTo(
						this.blog, other.blog);
				if (lastComparison != 0) {
					return lastComparison;
				}
			}
			return 0;
		}

		public _Fields fieldForId(int fieldId) {
			return _Fields.findByThriftId(fieldId);
		}

		public void read(org.apache.thrift.protocol.TProtocol iprot)
				throws org.apache.thrift.TException {
			schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
		}

		public void write(org.apache.thrift.protocol.TProtocol oprot)
				throws org.apache.thrift.TException {
			schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("testCase4_args(");
			boolean first = true;

			sb.append("blog:");
			if (this.blog == null) {
				sb.append("null");
			} else {
				sb.append(this.blog);
			}
			first = false;
			sb.append(")");
			return sb.toString();
		}

		public void validate() throws org.apache.thrift.TException {
			// check for required fields
			// check for sub-struct validity
		}

		private void writeObject(java.io.ObjectOutputStream out)
				throws java.io.IOException {
			try {
				write(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(out)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private void readObject(java.io.ObjectInputStream in)
				throws java.io.IOException, ClassNotFoundException {
			try {
				read(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(in)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private static class testCase4_argsStandardSchemeFactory implements
				SchemeFactory {
			public testCase4_argsStandardScheme getScheme() {
				return new testCase4_argsStandardScheme();
			}
		}

		private static class testCase4_argsStandardScheme extends
				StandardScheme<testCase4_args> {

			public void read(org.apache.thrift.protocol.TProtocol iprot,
					testCase4_args struct) throws org.apache.thrift.TException {
				org.apache.thrift.protocol.TField schemeField;
				iprot.readStructBegin();
				while (true) {
					schemeField = iprot.readFieldBegin();
					if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
						break;
					}
					switch (schemeField.id) {
					case 1: // BLOG
						if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
							{
								org.apache.thrift.protocol.TList _list28 = iprot
										.readListBegin();
								struct.blog = new ArrayList<Blog>(_list28.size);
								for (int _i29 = 0; _i29 < _list28.size; ++_i29) {
									Blog _elem30;
									_elem30 = new Blog();
									_elem30.read(iprot);
									struct.blog.add(_elem30);
								}
								iprot.readListEnd();
							}
							struct.setBlogIsSet(true);
						} else {
							org.apache.thrift.protocol.TProtocolUtil.skip(
									iprot, schemeField.type);
						}
						break;
					default:
						org.apache.thrift.protocol.TProtocolUtil.skip(iprot,
								schemeField.type);
					}
					iprot.readFieldEnd();
				}
				iprot.readStructEnd();

				// check for required fields of primitive type, which can't be
				// checked in the validate method
				struct.validate();
			}

			public void write(org.apache.thrift.protocol.TProtocol oprot,
					testCase4_args struct) throws org.apache.thrift.TException {
				struct.validate();

				oprot.writeStructBegin(STRUCT_DESC);
				if (struct.blog != null) {
					oprot.writeFieldBegin(BLOG_FIELD_DESC);
					{
						oprot.writeListBegin(new org.apache.thrift.protocol.TList(
								org.apache.thrift.protocol.TType.STRUCT,
								struct.blog.size()));
						for (Blog _iter31 : struct.blog) {
							_iter31.write(oprot);
						}
						oprot.writeListEnd();
					}
					oprot.writeFieldEnd();
				}
				oprot.writeFieldStop();
				oprot.writeStructEnd();
			}

		}

		private static class testCase4_argsTupleSchemeFactory implements
				SchemeFactory {
			public testCase4_argsTupleScheme getScheme() {
				return new testCase4_argsTupleScheme();
			}
		}

		private static class testCase4_argsTupleScheme extends
				TupleScheme<testCase4_args> {

			@Override
			public void write(org.apache.thrift.protocol.TProtocol prot,
					testCase4_args struct) throws org.apache.thrift.TException {
				TTupleProtocol oprot = (TTupleProtocol) prot;
				BitSet optionals = new BitSet();
				if (struct.isSetBlog()) {
					optionals.set(0);
				}
				oprot.writeBitSet(optionals, 1);
				if (struct.isSetBlog()) {
					{
						oprot.writeI32(struct.blog.size());
						for (Blog _iter32 : struct.blog) {
							_iter32.write(oprot);
						}
					}
				}
			}

			@Override
			public void read(org.apache.thrift.protocol.TProtocol prot,
					testCase4_args struct) throws org.apache.thrift.TException {
				TTupleProtocol iprot = (TTupleProtocol) prot;
				BitSet incoming = iprot.readBitSet(1);
				if (incoming.get(0)) {
					{
						org.apache.thrift.protocol.TList _list33 = new org.apache.thrift.protocol.TList(
								org.apache.thrift.protocol.TType.STRUCT,
								iprot.readI32());
						struct.blog = new ArrayList<Blog>(_list33.size);
						for (int _i34 = 0; _i34 < _list33.size; ++_i34) {
							Blog _elem35;
							_elem35 = new Blog();
							_elem35.read(iprot);
							struct.blog.add(_elem35);
						}
					}
					struct.setBlogIsSet(true);
				}
			}
		}

	}

	public static class testCase4_result
			implements
			org.apache.thrift.TBase<testCase4_result, testCase4_result._Fields>,
			java.io.Serializable, Cloneable, Comparable<testCase4_result> {
		private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
				"testCase4_result");

		private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
		static {
			schemes.put(StandardScheme.class,
					new testCase4_resultStandardSchemeFactory());
			schemes.put(TupleScheme.class,
					new testCase4_resultTupleSchemeFactory());
		}

		/**
		 * The set of fields this struct contains, along with convenience
		 * methods for finding and manipulating them.
		 */
		public enum _Fields implements org.apache.thrift.TFieldIdEnum {
			;

			private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

			static {
				for (_Fields field : EnumSet.allOf(_Fields.class)) {
					byName.put(field.getFieldName(), field);
				}
			}

			public static _Fields findByThriftId(int fieldId) {
				switch (fieldId) {
				default:
					return null;
				}
			}

			public static _Fields findByThriftIdOrThrow(int fieldId) {
				_Fields fields = findByThriftId(fieldId);
				if (fields == null)
					throw new IllegalArgumentException("Field " + fieldId
							+ " doesn't exist!");
				return fields;
			}

			public static _Fields findByName(String name) {
				return byName.get(name);
			}

			private final short _thriftId;
			private final String _fieldName;

			_Fields(short thriftId, String fieldName) {
				_thriftId = thriftId;
				_fieldName = fieldName;
			}

			public short getThriftFieldId() {
				return _thriftId;
			}

			public String getFieldName() {
				return _fieldName;
			}
		}

		public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
		static {
			Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
					_Fields.class);
			metaDataMap = Collections.unmodifiableMap(tmpMap);
			org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(
					testCase4_result.class, metaDataMap);
		}

		public testCase4_result() {
		}

		public testCase4_result(testCase4_result other) {
		}

		public testCase4_result deepCopy() {
			return new testCase4_result(this);
		}

		@Override
		public void clear() {
		}

		public void setFieldValue(_Fields field, Object value) {
			switch (field) {
			}
		}

		public Object getFieldValue(_Fields field) {
			switch (field) {
			}
			throw new IllegalStateException();
		}

		public boolean isSet(_Fields field) {
			if (field == null) {
				throw new IllegalArgumentException();
			}

			switch (field) {
			}
			throw new IllegalStateException();
		}

		@Override
		public boolean equals(Object that) {
			if (that == null)
				return false;
			if (that instanceof testCase4_result)
				return this.equals((testCase4_result) that);
			return false;
		}

		public boolean equals(testCase4_result that) {
			if (that == null)
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public int compareTo(testCase4_result other) {
			if (!getClass().equals(other.getClass())) {
				return getClass().getName().compareTo(
						other.getClass().getName());
			}

			int lastComparison = 0;

			return 0;
		}

		public _Fields fieldForId(int fieldId) {
			return _Fields.findByThriftId(fieldId);
		}

		public void read(org.apache.thrift.protocol.TProtocol iprot)
				throws org.apache.thrift.TException {
			schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
		}

		public void write(org.apache.thrift.protocol.TProtocol oprot)
				throws org.apache.thrift.TException {
			schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("testCase4_result(");
			boolean first = true;

			sb.append(")");
			return sb.toString();
		}

		public void validate() throws org.apache.thrift.TException {
			// check for required fields
			// check for sub-struct validity
		}

		private void writeObject(java.io.ObjectOutputStream out)
				throws java.io.IOException {
			try {
				write(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(out)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private void readObject(java.io.ObjectInputStream in)
				throws java.io.IOException, ClassNotFoundException {
			try {
				read(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(in)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private static class testCase4_resultStandardSchemeFactory implements
				SchemeFactory {
			public testCase4_resultStandardScheme getScheme() {
				return new testCase4_resultStandardScheme();
			}
		}

		private static class testCase4_resultStandardScheme extends
				StandardScheme<testCase4_result> {

			public void read(org.apache.thrift.protocol.TProtocol iprot,
					testCase4_result struct)
					throws org.apache.thrift.TException {
				org.apache.thrift.protocol.TField schemeField;
				iprot.readStructBegin();
				while (true) {
					schemeField = iprot.readFieldBegin();
					if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
						break;
					}
					switch (schemeField.id) {
					default:
						org.apache.thrift.protocol.TProtocolUtil.skip(iprot,
								schemeField.type);
					}
					iprot.readFieldEnd();
				}
				iprot.readStructEnd();

				// check for required fields of primitive type, which can't be
				// checked in the validate method
				struct.validate();
			}

			public void write(org.apache.thrift.protocol.TProtocol oprot,
					testCase4_result struct)
					throws org.apache.thrift.TException {
				struct.validate();

				oprot.writeStructBegin(STRUCT_DESC);
				oprot.writeFieldStop();
				oprot.writeStructEnd();
			}

		}

		private static class testCase4_resultTupleSchemeFactory implements
				SchemeFactory {
			public testCase4_resultTupleScheme getScheme() {
				return new testCase4_resultTupleScheme();
			}
		}

		private static class testCase4_resultTupleScheme extends
				TupleScheme<testCase4_result> {

			@Override
			public void write(org.apache.thrift.protocol.TProtocol prot,
					testCase4_result struct)
					throws org.apache.thrift.TException {
				TTupleProtocol oprot = (TTupleProtocol) prot;
			}

			@Override
			public void read(org.apache.thrift.protocol.TProtocol prot,
					testCase4_result struct)
					throws org.apache.thrift.TException {
				TTupleProtocol iprot = (TTupleProtocol) prot;
			}
		}

	}

}
