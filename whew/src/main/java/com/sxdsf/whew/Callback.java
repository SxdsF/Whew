package com.sxdsf.whew;

import com.sxdsf.echo.Receiver;
import com.sxdsf.whew.info.Response;

/**
 * com.sxdsf.whew.Callback
 *
 * @author 孙博闻
 * @date 2016/7/13 11:49
 * @desc 文件描述
 */
public interface Callback extends Receiver<Response> {

    /**
     * 在任务开始时回调
     *
     * @param call 调用
     */
    void onStart(Call call);

    /**
     * 在任务错误时回调
     *
     * @param call 调用
     * @param t    异常
     */
    void onError(Call call, Throwable t);

    /**
     * 在任务成功时回调
     *
     * @param call     调用
     * @param response 成功时回调的内容
     */
    void onSuccess(Call call, Response response);

    /**
     * 在任务取消时回调
     *
     * @param call 调用
     */
    void onCancel(Call call);
}
