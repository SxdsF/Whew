package com.sxdsf.whew;

import com.sxdsf.echo.Acceptor;
import com.sxdsf.echo.Switcher;
import com.sxdsf.echo.functions.Action0;
import com.sxdsf.whew.info.Response;

/**
 * com.sxdsf.whew.OnCallback
 *
 * @author 孙博闻
 * @date 2016/10/28 16:30
 * @desc 文件描述
 */

public class OnCallback extends CallbackWrapper {

    private final Switcher.Worker mWorker;

    public OnCallback(Callback wrapped, boolean isOverride, boolean isMerge, Switcher.Worker worker) {
        super(wrapped, isOverride, isMerge);
        mWorker = worker;
    }

    public void init() {
        if (getWrapped() != null && getWrapped() instanceof Acceptor) {
            ((Acceptor) getWrapped()).add(mWorker);
            ((Acceptor) getWrapped()).add(this);
        }
    }

    @Override
    public void onStart(final Call call) {
        if (isUnReceived()) {
            return;
        }
        mWorker.switches(new Action0() {
            @Override
            public void call() {
                if (getWrapped() != null) {
                    if (getWrapped() instanceof Acceptor && ((Acceptor) getWrapped()).isUnReceived()) {
                        return;
                    }
                    getWrapped().onStart(call);
                }
            }
        });
    }

    @Override
    public void onError(final Call call, final Throwable t) {
        if (isUnReceived()) {
            return;
        }
        mWorker.switches(new Action0() {
            @Override
            public void call() {
                if (getWrapped() != null) {
                    if (getWrapped() instanceof Acceptor && ((Acceptor) getWrapped()).isUnReceived()) {
                        return;
                    }
                    getWrapped().onError(call, t);
                }
            }
        });
    }

    @Override
    public void onSuccess(final Call call, final Response response) {
        if (isUnReceived()) {
            return;
        }
        mWorker.switches(new Action0() {
            @Override
            public void call() {
                if (getWrapped() != null) {
                    if (getWrapped() instanceof Acceptor && ((Acceptor) getWrapped()).isUnReceived()) {
                        return;
                    }
                    getWrapped().onSuccess(call, response);
                }
            }
        });
    }

    @Override
    public void onCancel(final Call call) {
        if (isUnReceived()) {
            return;
        }
        mWorker.switches(new Action0() {
            @Override
            public void call() {
                if (getWrapped() != null) {
                    if (getWrapped() instanceof Acceptor && ((Acceptor) getWrapped()).isUnReceived()) {
                        return;
                    }
                    getWrapped().onCancel(call);
                }
            }
        });
    }
}
