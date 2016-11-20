package com.sxdsf.whew;

/**
 * com.sxdsf.whew.Task
 *
 * @author 孙博闻
 * @date 2016/7/13 11:42
 * @desc 描述一个任务的接口，一次性的
 */
public interface Task {

    /**
     * 是否已经准备
     *
     * @return
     */
    boolean isReady();

    /**
     * 是否已经执行
     *
     * @return
     */
    boolean isExecuted();

    /**
     * 是否被取消，表示没有完整执行
     *
     * @return
     */
    boolean isCanceled();

    /**
     * 是否出错了
     *
     * @return
     */
    boolean isError();

    /**
     * 是否执行完毕，表示完整执行
     *
     * @return
     */
    boolean isDone();

}
