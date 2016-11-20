package com.sxdsf.whew;

import com.sxdsf.echo.AcceptorWrapper;
import com.sxdsf.whew.info.Response;

/**
 * com.sxdsf.whew.CallbackWrapper
 *
 * @author 孙博闻
 * @date 2016/10/28 16:06
 * @desc 文件描述
 */

public class CallbackWrapper extends AcceptorWrapper<Response, Callback> implements Callback {

    public CallbackWrapper(Callback wrapped, boolean isOverride, boolean isMerge) {
        super(wrapped, isOverride, isMerge);
    }

    @Override
    public void onStart(Call call) {
        if (getWrapped() != null) {
            getWrapped().onStart(call);
        }
    }

    @Override
    public void onError(Call call, Throwable t) {
        if (getWrapped() != null) {
            getWrapped().onError(call, t);
        }
    }

    @Override
    public void onSuccess(Call call, Response response) {
        if (getWrapped() != null) {
            getWrapped().onSuccess(call, response);
        }
    }

    @Override
    public void onCancel(Call call) {
        if (getWrapped() != null) {
            getWrapped().onCancel(call);
        }
    }
}
