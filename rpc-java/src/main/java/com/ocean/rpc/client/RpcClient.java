package com.ocean.rpc.client;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.ocean.rpc.common.RpcCallback;
import com.ocean.rpc.common.RpcCallback1;
import com.ocean.rpc.common.RpcContext;
import com.ocean.rpc.common.RpcErrorEvent;
import com.ocean.rpc.common.RpcException;
import com.ocean.rpc.common.RpcFilter;
import com.ocean.rpc.common.RpcInvocationHandler;
import com.ocean.rpc.common.RpcInvoker;
import com.ocean.rpc.common.RpcResultMode;
import com.ocean.rpc.io.ByteBufferStream;
import com.ocean.rpc.io.RpcMode;
import com.ocean.rpc.io.RpcTags;
import com.ocean.rpc.io.serialize.RpcWriter;
import com.ocean.rpc.io.unserialize.RpcReader;
import com.ocean.rpc.util.ClassUtil;
import com.ocean.rpc.util.StrUtil;

public abstract class RpcClient implements RpcInvoker, RpcTags {
	private final ExecutorService threadPool = Executors.newCachedThreadPool();

	private final static Object[] nullArgs = new Object[0];
	private final ArrayList<RpcFilter> filters = new ArrayList<RpcFilter>();
	private RpcMode mode;
	protected String uri;
	public RpcErrorEvent onError = null;

	protected RpcClient() {
		this(null, RpcMode.MemberMode);
	}

	protected RpcClient(String uri) {
		this(uri, RpcMode.MemberMode);
	}

	protected RpcClient(RpcMode mode) {
		this(null, mode);
	}

	protected RpcClient(String uri, RpcMode mode) {
		this.mode = mode;
		this.uri = uri;
	}

	public void close() {
		if (!threadPool.isShutdown()) {
			threadPool.shutdown();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	private final static HashMap<String, Class<? extends RpcClient>> clientFactories = new HashMap<String, Class<? extends RpcClient>>();

	public static void registerClientFactory(String scheme,
			Class<? extends RpcClient> clientClass) {
		synchronized (clientFactories) {
			clientFactories.put(scheme, clientClass);
		}
	}

	static {
		registerClientFactory("tcp", RpcTcpClient.class);
		registerClientFactory("tcp4", RpcTcpClient.class);
		registerClientFactory("tcp6", RpcTcpClient.class);
		registerClientFactory("http", RpcHttpClient.class);
		registerClientFactory("https", RpcHttpClient.class);
	}

	public static RpcClient create(String uri) throws IOException,
			URISyntaxException {
		return create(uri, RpcMode.MemberMode);
	}

	public static RpcClient create(String uri, RpcMode mode)
			throws IOException, URISyntaxException {
		String scheme = (new URI(uri)).getScheme().toLowerCase();
		Class<? extends RpcClient> clientClass = clientFactories.get(scheme);
		if (clientClass != null) {
			try {
				RpcClient client = clientClass.newInstance();
				client.mode = mode;
				client.uri = uri;
				return client;
			} catch (Exception ex) {
				throw new RpcException("This client doesn't support " + scheme
						+ " scheme.");
			}
		}
		throw new RpcException("This client doesn't support " + scheme
				+ " scheme.");
	}

	public RpcFilter getFilter() {
		if (filters.isEmpty()) {
			return null;
		}
		return filters.get(0);
	}

	public void setFilter(RpcFilter filter) {
		if (!filters.isEmpty()) {
			filters.clear();
		}
		if (filter != null) {
			filters.add(filter);
		}
	}

	public void addFilter(RpcFilter filter) {
		filters.add(filter);
	}

	public boolean removeFilter(RpcFilter filter) {
		return filters.remove(filter);
	}

	public void useService(String uri) {
		this.uri = uri;
	}

	public final <T> T useService(Class<T> type) {
		return useService(type, null);
	}

	public final <T> T useService(String uri, Class<T> type) {
		return useService(uri, type, null);
	}

	@SuppressWarnings("unchecked")
	public final <T> T useService(Class<T> type, String ns) {
		RpcInvocationHandler handler = new RpcInvocationHandler(this, ns);
		if (type.isInterface()) {
			return (T) Proxy.newProxyInstance(type.getClassLoader(),
					new Class<?>[] { type }, handler);
		} else {
			return (T) Proxy.newProxyInstance(type.getClassLoader(),
					type.getInterfaces(), handler);
		}
	}

	public final <T> T useService(String uri, Class<T> type, String ns) {
		useService(uri);
		return useService(type, ns);
	}

	@Override
	public final void invoke(String functionName, RpcCallback1<?> callback) {
		invoke(functionName, nullArgs, callback, null, (Type) null,
				RpcResultMode.Normal, false);
	}

	@Override
	public final void invoke(String functionName, RpcCallback1<?> callback,
			RpcErrorEvent errorEvent) {
		invoke(functionName, nullArgs, callback, errorEvent, (Type) null,
				RpcResultMode.Normal, false);
	}

	@Override
	public final void invoke(String functionName, RpcCallback1<?> callback,
			RpcResultMode resultMode) {
		invoke(functionName, nullArgs, callback, null, (Type) null, resultMode,
				false);
	}

	@Override
	public final void invoke(String functionName, RpcCallback1<?> callback,
			RpcErrorEvent errorEvent, RpcResultMode resultMode) {
		invoke(functionName, nullArgs, callback, errorEvent, (Type) null,
				resultMode, false);
	}

	@Override
	public final void invoke(String functionName, RpcCallback1<?> callback,
			boolean simple) {
		invoke(functionName, nullArgs, callback, null, (Type) null,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final void invoke(String functionName, RpcCallback1<?> callback,
			RpcErrorEvent errorEvent, boolean simple) {
		invoke(functionName, nullArgs, callback, errorEvent, (Type) null,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final void invoke(String functionName, RpcCallback1<?> callback,
			RpcResultMode resultMode, boolean simple) {
		invoke(functionName, nullArgs, callback, null, (Type) null, resultMode,
				simple);
	}

	@Override
	public final void invoke(String functionName, RpcCallback1<?> callback,
			RpcErrorEvent errorEvent, RpcResultMode resultMode, boolean simple) {
		invoke(functionName, nullArgs, callback, errorEvent, (Type) null,
				resultMode, simple);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback) {
		invoke(functionName, arguments, callback, null, (Type) null,
				RpcResultMode.Normal, false);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, RpcErrorEvent errorEvent) {
		invoke(functionName, arguments, callback, errorEvent, (Type) null,
				RpcResultMode.Normal, false);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, RpcResultMode resultMode) {
		invoke(functionName, arguments, callback, null, (Type) null,
				resultMode, false);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, RpcErrorEvent errorEvent,
			RpcResultMode resultMode) {
		invoke(functionName, arguments, callback, errorEvent, (Type) null,
				resultMode, false);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, boolean simple) {
		invoke(functionName, arguments, callback, null, (Type) null,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, RpcErrorEvent errorEvent, boolean simple) {
		invoke(functionName, arguments, callback, errorEvent, (Type) null,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, RpcResultMode resultMode, boolean simple) {
		invoke(functionName, arguments, callback, null, (Type) null,
				resultMode, simple);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, RpcErrorEvent errorEvent,
			RpcResultMode resultMode, boolean simple) {
		invoke(functionName, arguments, callback, errorEvent, (Type) null,
				resultMode, simple);
	}

	@Override
	public final <T> void invoke(String functionName, RpcCallback1<T> callback,
			Class<T> returnType) {
		invoke(functionName, nullArgs, callback, null, returnType,
				RpcResultMode.Normal, false);
	}

	@Override
	public final <T> void invoke(String functionName, RpcCallback1<T> callback,
			RpcErrorEvent errorEvent, Class<T> returnType) {
		invoke(functionName, nullArgs, callback, errorEvent, returnType,
				RpcResultMode.Normal, false);
	}

	@Override
	public final <T> void invoke(String functionName, RpcCallback1<T> callback,
			Class<T> returnType, RpcResultMode resultMode) {
		invoke(functionName, nullArgs, callback, null, returnType, resultMode,
				false);
	}

	@Override
	public final <T> void invoke(String functionName, RpcCallback1<T> callback,
			RpcErrorEvent errorEvent, Class<T> returnType,
			RpcResultMode resultMode) {
		invoke(functionName, nullArgs, callback, errorEvent, returnType,
				resultMode, false);
	}

	@Override
	public final <T> void invoke(String functionName, RpcCallback1<T> callback,
			Class<T> returnType, boolean simple) {
		invoke(functionName, nullArgs, callback, null, returnType,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final <T> void invoke(String functionName, RpcCallback1<T> callback,
			RpcErrorEvent errorEvent, Class<T> returnType, boolean simple) {
		invoke(functionName, nullArgs, callback, errorEvent, returnType,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final <T> void invoke(String functionName, RpcCallback1<T> callback,
			Class<T> returnType, RpcResultMode resultMode, boolean simple) {
		invoke(functionName, nullArgs, callback, null, returnType, resultMode,
				simple);
	}

	@Override
	public final <T> void invoke(String functionName, RpcCallback1<T> callback,
			RpcErrorEvent errorEvent, Class<T> returnType,
			RpcResultMode resultMode, boolean simple) {
		invoke(functionName, nullArgs, callback, errorEvent, returnType,
				resultMode, simple);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, Class<T> returnType) {
		invoke(functionName, arguments, callback, null, returnType,
				RpcResultMode.Normal, false);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType) {
		invoke(functionName, arguments, callback, errorEvent, returnType,
				RpcResultMode.Normal, false);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, Class<T> returnType,
			RpcResultMode resultMode) {
		invoke(functionName, arguments, callback, null, returnType, resultMode,
				false);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, RpcResultMode resultMode) {
		invoke(functionName, arguments, callback, errorEvent, returnType,
				resultMode, false);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, Class<T> returnType, boolean simple) {
		invoke(functionName, arguments, callback, null, returnType,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, boolean simple) {
		invoke(functionName, arguments, callback, errorEvent, returnType,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, Class<T> returnType,
			RpcResultMode resultMode, boolean simple) {
		invoke(functionName, arguments, callback, null, returnType, resultMode,
				simple);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, RpcResultMode resultMode, boolean simple) {
		invoke(functionName, arguments, callback, errorEvent,
				(Type) returnType, resultMode, simple);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void invoke(final String functionName, final Object[] arguments,
			@SuppressWarnings("rawtypes") final RpcCallback1 callback,
			final RpcErrorEvent errorEvent, final Type returnType,
			final RpcResultMode resultMode, final boolean simple) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Object result = invoke(functionName, arguments, returnType,
							false, resultMode, simple);
					callback.handler(result);
				} catch (Exception ex) {
					if (errorEvent != null) {
						errorEvent.handler(functionName, ex);
					} else if (onError != null) {
						onError.handler(functionName, ex);
					}
				}
			}
		});
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback) {
		invoke(functionName, arguments, callback, null, (Type) null, false,
				RpcResultMode.Normal, false);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent) {
		invoke(functionName, arguments, callback, errorEvent, (Type) null,
				false, RpcResultMode.Normal, false);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, boolean byRef) {
		invoke(functionName, arguments, callback, null, (Type) null, byRef,
				RpcResultMode.Normal, false);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent, boolean byRef) {
		invoke(functionName, arguments, callback, errorEvent, (Type) null,
				byRef, RpcResultMode.Normal, false);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, boolean byRef, boolean simple) {
		invoke(functionName, arguments, callback, null, (Type) null, byRef,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent, boolean byRef,
			boolean simple) {
		invoke(functionName, arguments, callback, errorEvent, (Type) null,
				byRef, RpcResultMode.Normal, simple);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcResultMode resultMode) {
		invoke(functionName, arguments, callback, null, (Type) null, false,
				resultMode, false);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent,
			RpcResultMode resultMode) {
		invoke(functionName, arguments, callback, errorEvent, (Type) null,
				false, resultMode, false);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, boolean byRef, RpcResultMode resultMode) {
		invoke(functionName, arguments, callback, null, (Type) null, byRef,
				resultMode, false);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent, boolean byRef,
			RpcResultMode resultMode) {
		invoke(functionName, arguments, callback, errorEvent, (Type) null,
				byRef, resultMode, false);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcResultMode resultMode, boolean simple) {
		invoke(functionName, arguments, callback, null, (Type) null, false,
				resultMode, simple);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent,
			RpcResultMode resultMode, boolean simple) {
		invoke(functionName, arguments, callback, errorEvent, (Type) null,
				false, resultMode, simple);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, boolean byRef, RpcResultMode resultMode,
			boolean simple) {
		invoke(functionName, arguments, callback, null, (Type) null, byRef,
				resultMode, simple);
	}

	@Override
	public final void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent, boolean byRef,
			RpcResultMode resultMode, boolean simple) {
		invoke(functionName, arguments, callback, errorEvent, (Type) null,
				byRef, resultMode, simple);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType) {
		invoke(functionName, arguments, callback, null, returnType, false,
				RpcResultMode.Normal, false);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType) {
		invoke(functionName, arguments, callback, errorEvent, returnType,
				false, RpcResultMode.Normal, false);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType, boolean byRef) {
		invoke(functionName, arguments, callback, null, returnType, byRef,
				RpcResultMode.Normal, false);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, boolean byRef) {
		invoke(functionName, arguments, callback, errorEvent, returnType,
				byRef, RpcResultMode.Normal, false);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType, boolean byRef,
			boolean simple) {
		invoke(functionName, arguments, callback, null, returnType, byRef,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, boolean byRef, boolean simple) {
		invoke(functionName, arguments, callback, errorEvent, returnType,
				byRef, RpcResultMode.Normal, simple);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType,
			RpcResultMode resultMode) {
		invoke(functionName, arguments, callback, null, returnType, false,
				resultMode, false);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, RpcResultMode resultMode) {
		invoke(functionName, arguments, callback, errorEvent, returnType,
				false, resultMode, false);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType, boolean byRef,
			RpcResultMode resultMode) {
		invoke(functionName, arguments, callback, null, returnType, byRef,
				resultMode, false);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, boolean byRef, RpcResultMode resultMode) {
		invoke(functionName, arguments, callback, errorEvent, returnType,
				byRef, resultMode, false);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType,
			RpcResultMode resultMode, boolean simple) {
		invoke(functionName, arguments, callback, null, returnType, false,
				resultMode, simple);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, RpcResultMode resultMode, boolean simple) {
		invoke(functionName, arguments, callback, errorEvent, returnType,
				false, resultMode, simple);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType, boolean byRef,
			RpcResultMode resultMode, boolean simple) {
		invoke(functionName, arguments, callback, null, returnType, byRef,
				resultMode, simple);
	}

	@Override
	public final <T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, boolean byRef, RpcResultMode resultMode,
			boolean simple) {
		invoke(functionName, arguments, callback, errorEvent,
				(Type) returnType, byRef, resultMode, simple);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void invoke(final String functionName, final Object[] arguments,
			@SuppressWarnings("rawtypes") final RpcCallback callback,
			final RpcErrorEvent errorEvent, final Type returnType,
			final boolean byRef, final RpcResultMode resultMode,
			final boolean simple) {
		new Thread() {
			@Override
			public void run() {
				try {
					Object result = invoke(functionName, arguments, returnType,
							byRef, resultMode, simple);
					callback.handler(result, arguments);
				} catch (Exception ex) {
					if (errorEvent != null) {
						errorEvent.handler(functionName, ex);
					} else if (onError != null) {
						onError.handler(functionName, ex);
					}
				}
			}
		}.start();
	}

	@Override
	public final Object invoke(String functionName) throws IOException {
		return invoke(functionName, nullArgs, (Type) null, false,
				RpcResultMode.Normal, false);
	}

	@Override
	public final Object invoke(String functionName, Object[] arguments)
			throws IOException {
		return invoke(functionName, arguments, (Type) null, false,
				RpcResultMode.Normal, false);
	}

	@Override
	public final Object invoke(String functionName, Object[] arguments,
			boolean byRef) throws IOException {
		return invoke(functionName, arguments, (Type) null, byRef,
				RpcResultMode.Normal, false);
	}

	@Override
	public final Object invoke(String functionName, boolean simple)
			throws IOException {
		return invoke(functionName, nullArgs, (Type) null, false,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final Object invoke(String functionName, Object[] arguments,
			boolean byRef, boolean simple) throws IOException {
		return invoke(functionName, arguments, (Type) null, byRef,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final Object invoke(String functionName, RpcResultMode resultMode)
			throws IOException {
		return invoke(functionName, nullArgs, (Type) null, false, resultMode,
				false);
	}

	@Override
	public final Object invoke(String functionName, Object[] arguments,
			RpcResultMode resultMode) throws IOException {
		return invoke(functionName, arguments, (Type) null, false, resultMode,
				false);
	}

	@Override
	public final Object invoke(String functionName, Object[] arguments,
			boolean byRef, RpcResultMode resultMode) throws IOException {
		return invoke(functionName, arguments, (Type) null, byRef, resultMode,
				false);
	}

	@Override
	public final Object invoke(String functionName, RpcResultMode resultMode,
			boolean simple) throws IOException {
		return invoke(functionName, nullArgs, (Type) null, false, resultMode,
				simple);
	}

	@Override
	public final Object invoke(String functionName, Object[] arguments,
			RpcResultMode resultMode, boolean simple) throws IOException {
		return invoke(functionName, arguments, (Type) null, false, resultMode,
				simple);
	}

	@Override
	public final Object invoke(String functionName, Object[] arguments,
			boolean byRef, RpcResultMode resultMode, boolean simple)
			throws IOException {
		return invoke(functionName, arguments, (Type) null, byRef, resultMode,
				simple);
	}

	@Override
	public final <T> T invoke(String functionName, Class<T> returnType)
			throws IOException {
		return invoke(functionName, nullArgs, returnType, false,
				RpcResultMode.Normal, false);
	}

	@Override
	public final <T> T invoke(String functionName, Object[] arguments,
			Class<T> returnType) throws IOException {
		return invoke(functionName, arguments, returnType, false,
				RpcResultMode.Normal, false);
	}

	@Override
	public final <T> T invoke(String functionName, Object[] arguments,
			Class<T> returnType, boolean byRef) throws IOException {
		return invoke(functionName, arguments, returnType, byRef,
				RpcResultMode.Normal, false);
	}

	@Override
	public final <T> T invoke(String functionName, Class<T> returnType,
			boolean simple) throws IOException {
		return invoke(functionName, nullArgs, returnType, false,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final <T> T invoke(String functionName, Object[] arguments,
			Class<T> returnType, boolean byRef, boolean simple)
			throws IOException {
		return invoke(functionName, arguments, returnType, byRef,
				RpcResultMode.Normal, simple);
	}

	@Override
	public final <T> T invoke(String functionName, Class<T> returnType,
			RpcResultMode resultMode) throws IOException {
		return invoke(functionName, nullArgs, returnType, false,
				resultMode, false);
	}

	@Override
	public final <T> T invoke(String functionName, Object[] arguments,
			Class<T> returnType, RpcResultMode resultMode) throws IOException {
		return invoke(functionName, arguments, returnType, false,
				resultMode, false);
	}

	@Override
	public final <T> T invoke(String functionName, Object[] arguments,
			Class<T> returnType, boolean byRef, RpcResultMode resultMode)
			throws IOException {
		return invoke(functionName, arguments, returnType, byRef,
				resultMode, false);
	}

	@Override
	public final <T> T invoke(String functionName, Class<T> returnType,
			RpcResultMode resultMode, boolean simple) throws IOException {
		return invoke(functionName, nullArgs, returnType, false,
				resultMode, simple);
	}

	@Override
	public final <T> T invoke(String functionName, Object[] arguments,
			Class<T> returnType, RpcResultMode resultMode, boolean simple)
			throws IOException {
		return invoke(functionName, arguments, returnType, false,
				resultMode, simple);
	}

	@Override
	@SuppressWarnings("unchecked")
	public final <T> T invoke(String functionName, Object[] arguments,
			Class<T> returnType, boolean byRef, RpcResultMode resultMode,
			boolean simple) throws IOException {
		return (T) invoke(functionName, arguments, (Type) returnType, byRef,
				resultMode, simple);
	}

	@Override
	public Object invoke(final String functionName, final Object[] arguments,
			Type returnType, final boolean byRef,
			final RpcResultMode resultMode, final boolean simple)
			throws IOException {
		if (Future.class.equals(ClassUtil.toClass(returnType))) {
			if (returnType instanceof ParameterizedType) {
				returnType = ((ParameterizedType) returnType)
						.getActualTypeArguments()[0];
				if (void.class.equals(returnType)
						|| Void.class.equals(returnType)) {
					returnType = null;
				}
			} else {
				returnType = null;
			}
			final Type _returnType = returnType;
			return threadPool.submit(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					return invoke(functionName, arguments, _returnType, byRef,
							resultMode, simple);
				}
			});
		}
		ByteBufferStream ostream = null;
		ByteBufferStream istream = null;
		try {
			RpcContext context = new ClientContext(this);
			ostream = doOutput(functionName, arguments, byRef, simple, context);
			istream = sendAndReceive(ostream);
			Object result = doInput(istream, arguments, returnType, resultMode,
					context);
			if (result instanceof RpcException) {
				throw (RpcException) result;
			}
			return result;
		} finally {
			if (ostream != null)
				ostream.close();
			if (istream != null)
				istream.close();
		}
	}

	private ByteBufferStream doOutput(String functionName, Object[] arguments,
			boolean byRef, boolean simple, RpcContext context)
			throws IOException {
		ByteBufferStream stream = new ByteBufferStream();
		RpcWriter hproseWriter = new RpcWriter(stream.getOutputStream(), mode,
				simple);
		stream.write(TagCall);
		hproseWriter.writeString(functionName);
		if ((arguments != null) && (arguments.length > 0 || byRef)) {
			hproseWriter.reset();
			hproseWriter.writeArray(arguments);
			if (byRef) {
				hproseWriter.writeBoolean(true);
			}
		}
		stream.write(TagEnd);
		stream.flip();
		for (int i = 0, n = filters.size(); i < n; ++i) {
			stream.buffer = filters.get(i).outputFilter(stream.buffer, context);
			stream.flip();
		}
		return stream;
	}

	private Object ByteBufferStreamToType(ByteBufferStream stream,
			Type returnType) throws RpcException {
		stream.flip();
		if (returnType == null || returnType == Object.class
				|| returnType == ByteBuffer.class || returnType == Buffer.class) {
			return stream.buffer;
		} else if (returnType == ByteBufferStream.class) {
			return stream;
		} else if (returnType == byte[].class) {
			byte[] bytes = stream.toArray();
			stream.close();
			return bytes;
		}
		throw new RpcException("Can't Convert ByteBuffer to Type: "
				+ returnType.toString());
	}

	private Object doInput(ByteBufferStream stream, Object[] arguments,
			Type returnType, RpcResultMode resultMode, RpcContext context)
			throws IOException {
		stream.flip();
		for (int i = filters.size() - 1; i >= 0; --i) {
			stream.buffer = filters.get(i).inputFilter(stream.buffer, context);
			stream.flip();
		}
		int tag = stream.buffer.get(stream.buffer.limit() - 1);
		if (tag != TagEnd) {
			throw new RpcException("Wrong Response: \r\n"
					+ StrUtil.toString(stream));
		}
		if (resultMode == RpcResultMode.Raw) {
			stream.buffer.limit(stream.buffer.limit() - 1);
		}
		if (resultMode == RpcResultMode.RawWithEndTag
				|| resultMode == RpcResultMode.Raw) {
			return ByteBufferStreamToType(stream, returnType);
		}
		Object result = null;
		RpcReader hproseReader = new RpcReader(stream.getInputStream(), mode);
		while ((tag = stream.read()) != TagEnd) {
			switch (tag) {
			case TagResult:
				if (resultMode == RpcResultMode.Normal) {
					hproseReader.reset();
					result = hproseReader.unserialize(returnType);
				} else if (resultMode == RpcResultMode.Serialized) {
					result = ByteBufferStreamToType(hproseReader.readRaw(),
							returnType);
				}
				break;
			case TagArgument:
				hproseReader.reset();
				Object[] args = hproseReader.readObjectArray();
				int length = arguments.length;
				if (length > args.length) {
					length = args.length;
				}
				System.arraycopy(args, 0, arguments, 0, length);
				break;
			case TagError:
				hproseReader.reset();
				result = new RpcException(hproseReader.readString());
				break;
			default:
				stream.rewind();
				throw new RpcException("Wrong Response: \r\n"
						+ StrUtil.toString(stream));
			}
		}
		return result;
	}

	protected abstract ByteBufferStream sendAndReceive(ByteBufferStream buffer)
			throws IOException;
}
