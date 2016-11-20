package com.sxdsf.whew;

import com.sxdsf.echo.Acceptor;
import com.sxdsf.echo.Caster;
import com.sxdsf.echo.Receiver;
import com.sxdsf.echo.Switcher;
import com.sxdsf.whew.info.Method;
import com.sxdsf.whew.info.Response;
import com.sxdsf.whew.info.WhewResponse;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * com.sxdsf.whew.WhewCall
 *
 * @author 孙博闻
 * @date 2016/7/13 14:16
 * @desc 文件描述
 */
public class WhewCall<T> extends Caster<Response> implements Call {

    /**
     * 原始okhttp的call
     */
    private okhttp3.Call mRawCall;
    /**
     * 原始okhttp的工厂
     */
    private okhttp3.Call.Factory mRawFactory;

    /**
     * 本次调用的请求
     */
    private com.sxdsf.whew.info.Request mRequest;
    /**
     * 当前调用要访问的url
     */
    private String mUrl;
    /**
     * 当前调用的方法类型
     */
    private Method mMethod;
    /**
     * 当前解析返回数据的解析者
     */
    private Parser<ResponseBody, T> mParser;
    /**
     * 接收者的线程
     */
    private ThreadMode mReceiveThread;
    /**
     * 当前调用的回调
     */
    private Callback mCallback;

    private volatile Status mStatus = Status.READY;

    private WhewCall(OnCast<Response> onCast) {
        super(onCast);
    }

    @Override
    protected Caster<Response> create(OnCast<Response> onCast) {
        return new WhewCall<>(onCast);
    }

    @Override
    protected Acceptor<Response> wrap(Receiver<Response> receiver, boolean isOverride, boolean isMerge) {
        return new CallbackWrapper((Callback) receiver, isOverride, isMerge);
    }

    @Override
    protected Acceptor<Response> createOnReceiveReceiver(Receiver<Response> responseReceiver, Switcher.Worker worker) {
        OnCallback onCallback = new OnCallback((Callback) responseReceiver, true, false, worker);
        onCallback.init();
        return onCallback;
    }

    @Override
    public boolean isUnReceived() {
        return isCanceled();
    }

    @Override
    public void unReceive() {
        cancel();
    }

    @Override
    public Call call(Callback callback) {
        return (Call) super.cast(callback);
    }

    @Override
    public Response call() throws IOException {
        return execute();
    }

    @Override
    public void cancel() {
        mStatus = Status.CANCELED;
        synchronized (this) {
            if (!mRawCall.isCanceled()) {
                mRawCall.cancel();
            }
            if (mCallback != null) {
                mCallback.onCancel(this);
            }
        }
    }

    @Override
    public com.sxdsf.whew.info.Request request() {
        return mRequest;
    }

    @Override
    public boolean isReady() {
        return mStatus == Status.READY;
    }

    @Override
    public boolean isExecuted() {
        return mStatus == Status.EXECUTED;
    }

    @Override
    public boolean isCanceled() {
        return mStatus == Status.CANCELED;
    }

    @Override
    public boolean isError() {
        return mStatus == Status.ERROR;
    }

    @Override
    public boolean isDone() {
        return mStatus == Status.DONE;
    }

    /**
     * 创建一个call
     *
     * @param <T>
     * @return
     */
    public static <T> WhewCall<T> create() {
        return new WhewCall<>(null);
    }

    /**
     * 设置请求的url
     *
     * @param url url
     * @return
     */
    WhewCall<T> url(String url) {
        mUrl = url;
        return this;
    }

    /**
     * 设置请求的方法类型
     *
     * @param method 方法类型
     * @return
     */
    WhewCall<T> method(Method method) {
        mMethod = method;
        return this;
    }

    /**
     * 设置call创建的工厂
     *
     * @param factory 工厂
     * @return
     */
    WhewCall<T> factory(okhttp3.Call.Factory factory) {
        mRawFactory = factory;
        return this;
    }

    /**
     * 统一处理的方法
     *
     * @param converter 做统一处理的类
     * @return
     */
    public WhewCall<T> unify(com.sxdsf.whew.Converter<T> converter) {
        return (WhewCall<T>) super.convert(converter);
    }

    /**
     * 设置数据的解析者
     *
     * @param parser 解析者
     * @return
     */
    public WhewCall<T> parser(Parser<ResponseBody, T> parser) {
        mParser = parser;
        return this;
    }

    /**
     * 回调时的线程
     *
     * @param threadMode 线程模式
     * @return
     */
    public WhewCall<T> callbackOn(ThreadMode threadMode) {
        mReceiveThread = threadMode;
        return this;
    }

    /**
     * 创建一个okHttp的call
     *
     * @return
     * @throws IOException
     */
    private okhttp3.Call createRawCall() throws IOException {
        okhttp3.Request request = new Request.Builder().url(mUrl).get().build();
        mRequest.mUrl = mUrl;
        mRequest.mMethod = mMethod;
        mRequest.mRealRequest = request;
        okhttp3.Call call = mRawFactory.newCall(request);
        if (call == null) {
            throw new NullPointerException("Call.Factory returned null.");
        }
        return call;
    }

    /**
     * 同步执行
     *
     * @return
     * @throws IOException
     */
    private Response execute() throws IOException {
        okhttp3.Call call;
        synchronized (this) {
            if (isExecuted()) {
                throw new IllegalStateException("Already executed.");
            }
            mStatus = Status.EXECUTED;
            call = mRawCall;
            if (call == null) {
                call = mRawCall = createRawCall();
            }
        }

        if (isCanceled()) {
            call.cancel();
        }

        return parseResponse(call.execute());
    }

    /**
     * 异步执行
     *
     * @param callback 回调
     */
    private void enQueue(final Callback callback) {
        if (callback == null) {
            throw new NullPointerException("callback == null");
        }

        okhttp3.Call call;
        Throwable failure = null;

        synchronized (this) {
            if (isExecuted()) {
                throw new IllegalStateException("Already executed.");
            }
            mStatus = Status.EXECUTED;

            callback.onStart(this);

            call = mRawCall;
            if (call == null) {
                try {
                    call = mRawCall = createRawCall();
                } catch (Throwable t) {
                    failure = t;
                }
            }
        }

        if (failure != null) {
            callback.onError(this, failure);
            return;
        }

        if (isCanceled()) {
            call.cancel();
        }

        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response rawResponse)
                    throws IOException {
                Response response;
                try {
                    response = parseResponse(rawResponse);
                } catch (Throwable e) {
                    callFailure(e);
                    return;
                }
                callSuccess(response);
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                try {
                    callback.onError(WhewCall.this, e);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            private void callFailure(Throwable e) {
                try {
                    callback.onError(WhewCall.this, e);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            private void callSuccess(Response response) {
                try {
                    callback.onSuccess(WhewCall.this, response);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });
    }

    /**
     * 解析response
     *
     * @param rawResponse 原始response
     * @return
     * @throws IOException
     */
    private Response parseResponse(okhttp3.Response rawResponse) throws IOException {
        ResponseBody rawBody = rawResponse.body();
        if (mParser == null) {
            throw new NullPointerException("parser == null");
        }
        return new WhewResponse<>(mParser.parse(rawBody));
    }

    private static class OnCall<T> implements OnCast<Response> {

        private final WhewCall<T> mWhewCall;

        private OnCall(WhewCall<T> whewCall) {
            mWhewCall = whewCall;
        }

        @Override
        public void call(Receiver<Response> responseReceiver) {
            mWhewCall.enQueue((Callback) responseReceiver);
        }
    }
}
