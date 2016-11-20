package com.sxdsf.whew;

import com.sxdsf.echo.Cast;
import com.sxdsf.whew.info.Request;
import com.sxdsf.whew.info.Response;

import java.io.IOException;

/**
 * com.sxdsf.whew.Call
 *
 * @author 孙博闻
 * @date 2016/7/13 11:37
 * @desc 描述一次调用的接口
 */
public interface Call extends Cast, Task {

    /**
     * 异步调用
     *
     * @param callback 回调
     */
    Call call(Callback callback);

    /**
     * 同步调用
     *
     * @return
     * @throws IOException
     */
    Response call() throws IOException;

    /**
     * 取消调用
     */
    void cancel();

    /**
     * 返回本次调用的原始request
     *
     * @return
     */
    Request request();
}
